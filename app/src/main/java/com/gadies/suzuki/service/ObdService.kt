package com.gadies.suzuki.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.Manifest
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.gadies.suzuki.MainActivity
import com.gadies.suzuki.R
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.data.repository.PidRepository
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ObdService : Service() {

    companion object {
        private const val TAG = "ObdService"
        private const val ELM327_UUID = "00001101-0000-1000-8000-00805F9B34FB"
        private const val POLLING_INTERVAL = 1000L // 1 second
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "OBD_SERVICE_CHANNEL"
    }

    @Inject
    lateinit var pidRepository: PidRepository

    private val binder = ObdBinder()
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        Log.d(TAG, "Initializing BluetoothAdapter")
        val bluetoothManager = applicationContext.getSystemService(BLUETOOTH_SERVICE) as? android.bluetooth.BluetoothManager
        if (bluetoothManager == null) {
            Log.e(TAG, "BluetoothManager not found.")
        }
        bluetoothManager?.adapter
    }
    private var bluetoothSocket: BluetoothSocket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    private val _connectionState = MutableStateFlow(ConnectionState(status = ConnectionStatus.DISCONNECTED))
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private var pollingJob: Job? = null
    private var isInitialized = false

    inner class ObdBinder : Binder() {
        fun getService(): ObdService = this@ObdService
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "ObdService onCreate")
        
        // Create notification channel for foreground service
        createNotificationChannel()
        
        // Start as foreground service
        startForeground(NOTIFICATION_ID, createNotification())

        // Register broadcast receiver for Bluetooth discovery
        val filter = android.content.IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        registerReceiver(bluetoothReceiver, filter)

        Log.d(TAG, "ObdService created as foreground service")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ObdService onDestroy")

        try {
            unregisterReceiver(bluetoothReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "Error unregistering receiver", e)
        }

        stopBluetoothScan()
        disconnect()
        serviceScope.cancel()
        Log.d(TAG, "ObdService destroyed")
    }

    private val _discoveredDevices = MutableStateFlow<List<ObdDevice>>(emptyList())
    val discoveredDevices: StateFlow<List<ObdDevice>> = _discoveredDevices.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val bluetoothReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: android.content.Context?, intent: android.content.Intent?) {
            Log.d(TAG, "BroadcastReceiver onReceive: ${intent?.action}")
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Use modern getParcelableExtra with proper Android version handling
                    val device: BluetoothDevice? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    }
                    
                    Log.d(TAG, "ACTION_FOUND: Device found: ${device?.name} - ${device?.address}")
                    device?.let { handleDeviceFound(it) }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d(TAG, "ACTION_DISCOVERY_FINISHED: Scan finished.")
                    _isScanning.value = false
                }
            }
        }
    }

    fun startBluetoothScan() {
        Log.d(TAG, "startBluetoothScan() called")
        
        // Comprehensive permission check for all Android versions
        if (!hasRequiredBluetoothPermissions()) {
            Log.e(TAG, "startBluetoothScan: Missing required Bluetooth permissions")
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.ERROR,
                errorMessage = "Izin Bluetooth diperlukan untuk memindai perangkat."
            )
            return
        }
        
        // Check if Bluetooth is enabled
        if (bluetoothAdapter?.isEnabled != true) {
            Log.e(TAG, "startBluetoothScan: Bluetooth is not enabled")
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.ERROR,
                errorMessage = "Bluetooth tidak aktif. Mohon aktifkan Bluetooth.",
                needsBluetoothEnable = true
            )
            return
        }
        
        // Check location services (required for BT scanning)
        if (!isLocationEnabled()) {
            Log.e(TAG, "startBluetoothScan: Location services must be enabled for Bluetooth scanning")
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.ERROR,
                errorMessage = "Layanan Lokasi (GPS) harus diaktifkan untuk memindai perangkat."
            )
            return
        }
        
        if (_isScanning.value) {
            Log.d(TAG, "startBluetoothScan: Scan already in progress")
            return
        }
        
        // Load paired devices first
        Log.d(TAG, "startBluetoothScan: Loading paired devices before starting discovery")
        loadPairedDevices()
        
        // Start discovery
        _isScanning.value = true
        Log.d(TAG, "startBluetoothScan: Starting Bluetooth discovery. Adapter state: ${bluetoothAdapter?.state}")
        
        val started = bluetoothAdapter?.startDiscovery()
        Log.d(TAG, "startBluetoothScan: startDiscovery() returned: $started")
        
        if (started != true) {
            _isScanning.value = false
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.ERROR,
                errorMessage = "Failed to start scan. Check permissions and adapter state."
            )
            Log.e(TAG, "startBluetoothScan: Failed to start Bluetooth discovery")
        } else {
            Log.d(TAG, "startBluetoothScan: Bluetooth discovery started successfully")
        }
    }

    fun stopBluetoothScan() {
        Log.d(TAG, "stopBluetoothScan() called")
        
        // Check permissions based on Android version
        val hasPermission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
        }
        
        if (hasPermission) {
            if (bluetoothAdapter?.isDiscovering == true) {
                Log.d(TAG, "stopBluetoothScan: Stopping Bluetooth discovery...")
                bluetoothAdapter?.cancelDiscovery()
            }
        } else {
            Log.w(TAG, "stopBluetoothScan: No permission to stop Bluetooth scan")
        }
        _isScanning.value = false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
    }

    fun getAvailableDevices(): List<ObdDevice> {
        return _discoveredDevices.value
    }

    private fun hasRequiredBluetoothPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12+
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            // Android 11 and below
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    private fun handleDeviceFound(device: BluetoothDevice) {
        Log.d(TAG, "handleDeviceFound: Found device: ${device.name} (${device.address})")
        try {
            val deviceName = device.name ?: "Unknown Device"
            val deviceAddress = device.address
            
            Log.d(TAG, "handleDeviceFound: Processing discovered device: $deviceName ($deviceAddress)")
            
            val obdDevice = ObdDevice(
                name = deviceName,
                address = deviceAddress,
                type = ConnectionType.BLUETOOTH,
                isAvailable = true
            )
            
            val currentDevices = _discoveredDevices.value.toMutableList()
            if (!currentDevices.any { it.address == deviceAddress }) {
                currentDevices.add(obdDevice)
                _discoveredDevices.value = currentDevices
                Log.d(TAG, "handleDeviceFound: Device discovered and added: $deviceName ($deviceAddress)")
            } else {
                Log.d(TAG, "handleDeviceFound: Device already in list: $deviceName ($deviceAddress)")
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "handleDeviceFound: Security exception accessing device info: ${e.message}")
        }
    }
    
    private fun loadPairedDevices() {
        Log.d(TAG, "loadPairedDevices() called")
        
        if (bluetoothAdapter == null) {
            Log.e(TAG, "loadPairedDevices: Bluetooth adapter is null.")
            return
        }

        // Check permissions for getting bonded devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "loadPairedDevices: BLUETOOTH_CONNECT permission required for paired devices")
                return
            }
        }

        try {
            val bondedDevices = bluetoothAdapter?.bondedDevices ?: emptySet()
            Log.d(TAG, "loadPairedDevices: Found ${bondedDevices.size} paired devices")
            
            val pairedObdDevices = bondedDevices.mapNotNull { device ->
                try {
                    val deviceName = device.name ?: "Unknown Device"
                    val deviceAddress = device.address
                    
                    Log.d(TAG, "loadPairedDevices: Paired device: $deviceName - $deviceAddress")
                    
                    // Filter for likely OBD devices or show all for debugging
                    if (isLikelyObdDevice(deviceName)) {
                        ObdDevice(
                            name = "$deviceName (Paired)",
                            address = deviceAddress,
                            type = ConnectionType.BLUETOOTH,
                            rssi = null
                        )
                    } else {
                        // For debugging, show all devices
                        ObdDevice(
                            name = "$deviceName (Paired)",
                            address = deviceAddress,
                            type = ConnectionType.BLUETOOTH,
                            rssi = null
                        )
                    }
                } catch (e: SecurityException) {
                    Log.e(TAG, "loadPairedDevices: Security exception accessing paired device: ${e.message}")
                    null
                }
            }
            
            val currentDevices = _discoveredDevices.value.toMutableList()
            pairedObdDevices.forEach { pairedDevice ->
                if (currentDevices.none { it.address == pairedDevice.address }) {
                    currentDevices.add(pairedDevice)
                }
            }
            _discoveredDevices.value = currentDevices
            Log.d(TAG, "loadPairedDevices: Loaded ${pairedObdDevices.size} paired devices, total devices: ${currentDevices.size}")
            
        } catch (e: Exception) {
            Log.e(TAG, "loadPairedDevices: Error loading paired devices: ${e.message}")
        }
    }
    
    private fun isLikelyObdDevice(deviceName: String): Boolean {
        val obdKeywords = listOf("obd", "elm", "327", "scan", "diag", "car", "auto", "gadies")
        return obdKeywords.any { keyword ->
            deviceName.lowercase().contains(keyword)
        }
    }

    fun connectDirectly(address: String) {
        Log.d(TAG, "Attempting to connect directly to $address")

        if (bluetoothAdapter == null || !bluetoothAdapter!!.isEnabled) {
            Log.e(TAG, "Bluetooth not available or not enabled for direct connect.")
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.ERROR,
                errorMessage = "Bluetooth tidak aktif.",
                needsBluetoothEnable = true
            )
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                _connectionState.value = _connectionState.value.copy(status = ConnectionStatus.ERROR, errorMessage = "Izin BLUETOOTH_CONNECT diperlukan.")
                Log.e(TAG, "BLUETOOTH_CONNECT permission not granted for direct connect.")
                return
            }
        }

        val deviceToConnect = ObdDevice(
            name = "GADIES OBD (Direct)",
            address = address,
            type = ConnectionType.BLUETOOTH,
            rssi = null
        )

        // Launch coroutine to call suspend function
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            connectToDevice(deviceToConnect)
        }
    }

    suspend fun connectToDevice(device: ObdDevice): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                _connectionState.value = _connectionState.value.copy(
                    status = ConnectionStatus.CONNECTING,
                    device = device
                )

                when (device.type) {
                    ConnectionType.BLUETOOTH -> connectBluetooth(device)
                    ConnectionType.WIFI -> connectWifi(device)
                    ConnectionType.NONE -> false
                }
            } catch (e: Exception) {
                Log.e(TAG, "Connection failed", e)
                _connectionState.value = _connectionState.value.copy(
                    status = ConnectionStatus.ERROR,
                    errorMessage = e.message
                )
                false
            }
        }
    }

    private suspend fun connectBluetooth(device: ObdDevice): Boolean {
        return try {
            val bluetoothDevice = bluetoothAdapter?.getRemoteDevice(device.address)
            bluetoothSocket = bluetoothDevice?.createRfcommSocketToServiceRecord(
                UUID.fromString(ELM327_UUID)
            )

            bluetoothSocket?.connect()
            inputStream = bluetoothSocket?.inputStream
            outputStream = bluetoothSocket?.outputStream

            if (initializeElm327()) {
                startPolling()
                true
            } else {
                disconnect()
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Bluetooth connection failed", e)
            disconnect()
            false
        }
    }

    private suspend fun connectWifi(_device: ObdDevice): Boolean {
        return false // Placeholder for WiFi implementation
    }

    private suspend fun initializeElm327(): Boolean {
        return try {
            Log.d(TAG, "Starting ELM327 initialization...")

            val resetResponse = sendCommand("ATZ")
            if (!resetResponse.isSuccess) {
                Log.e(TAG, "ELM327 reset failed")
                return false
            }
            delay(2000)

            val echoResponse = sendCommand("ATE0")
            if (!echoResponse.isSuccess) {
                Log.e(TAG, "ELM327 echo off failed")
                return false
            }

            sendCommand("ATL0")
            sendCommand("ATS0")
            sendCommand("ATSP0")

            val testResponse = sendCommand("0100")
            if (!testResponse.isSuccess || testResponse.rawResponse.contains("NO DATA") ||
                testResponse.rawResponse.contains("UNABLE TO CONNECT")) {
                Log.e(TAG, "Vehicle OBD communication test failed: ${testResponse.rawResponse}")
                isInitialized = false
                return false
            }

            isInitialized = true
            Log.d(TAG, "ELM327 successfully initialized and connected to vehicle")

            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.CONNECTED,
                lastConnected = System.currentTimeMillis()
            )

            true
        } catch (e: Exception) {
            Log.e(TAG, "ELM327 initialization failed", e)
            isInitialized = false
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.ERROR,
                errorMessage = "Failed to initialize OBD connection: ${e.message}"
            )
            false
        }
    }

    private suspend fun sendCommand(command: String): ObdResponse {
        return withContext(Dispatchers.IO) {
            try {
                outputStream?.write("$command\r".toByteArray())
                outputStream?.flush()

                delay(100) // Wait for response

                val buffer = ByteArray(1024)
                val bytesRead = inputStream?.read(buffer) ?: 0
                val response = String(buffer, 0, bytesRead).trim()

                Log.d(TAG, "Command: $command, Response: $response")

                ObdResponse(
                    command = command,
                    rawResponse = response,
                    isSuccess = !response.contains("ERROR") && !response.contains("NO DATA")
                )
            } catch (e: Exception) {
                Log.e(TAG, "Command failed: $command", e)
                ObdResponse(
                    command = command,
                    rawResponse = "",
                    isSuccess = false,
                    errorMessage = e.message
                )
            }
        }
    }

    private fun startPolling() {
        pollingJob?.cancel()
        pollingJob = serviceScope.launch {
            while (isActive && _connectionState.value.status == ConnectionStatus.CONNECTED) {
                try {
                    pidRepository.dashboardPids.value.forEach { pidData ->
                        if (pidData.isEnabled) {
                            pollPid(pidData)
                        }
                    }

                    pidRepository.pidDataMap.value.values.forEach { pidData ->
                        if (pidData.isEnabled && !isDashboardPid(pidData)) {
                            pollPid(pidData)
                        }
                    }

                    delay(POLLING_INTERVAL)
                } catch (e: Exception) {
                    Log.e(TAG, "Polling error", e)
                    if (e is IOException) {
                        _connectionState.value = _connectionState.value.copy(
                            status = ConnectionStatus.ERROR,
                            errorMessage = "Connection lost"
                        )
                        break
                    }
                }
            }
        }
    }

    private suspend fun pollPid(pidData: PidData) {
        val command = when (pidData.mode) {
            "01" -> "01${pidData.pid.removePrefix("0x")}"
            "21" -> "21${pidData.pid.removePrefix("0x")}"
            "22" -> "22${pidData.pid.removePrefix("0x")}"
            else -> return
        }

        val response = sendCommand(command)
        if (response.isSuccess) {
            val value = parseObdResponse(response.rawResponse, pidData)
            pidRepository.updatePidValue(pidData.pid, value, response.rawResponse)
        }
    }

    private fun parseObdResponse(rawResponse: String, pidData: PidData): Double {
        try {
            val cleanResponse = rawResponse.replace(" ", "").uppercase()

            val dataStart = when (pidData.mode) {
                "01" -> 4 // Skip "41XX"
                "21" -> 4 // Skip "61XX"
                "22" -> 4 // Skip "62XX"
                else -> 0
            }

            if (cleanResponse.length <= dataStart) return 0.0

            val dataHex = cleanResponse.substring(dataStart)

            return when {
                pidData.formula.contains("A - 40") -> {
                    val a = dataHex.substring(0, 2).toInt(16)
                    (a - 40).toDouble()
                }
                pidData.formula.contains("A * 100/255") -> {
                    val a = dataHex.substring(0, 2).toInt(16)
                    (a * 100.0 / 255.0)
                }
                pidData.formula.contains("(A * 256 + B) / 4") -> {
                    val a = dataHex.substring(0, 2).toInt(16)
                    val b = dataHex.substring(2, 4).toInt(16)
                    ((a * 256 + b) / 4.0)
                }
                pidData.formula.contains("A * 256 + B") -> {
                    val a = dataHex.substring(0, 2).toInt(16)
                    val b = if (dataHex.length >= 4) dataHex.substring(2, 4).toInt(16) else 0
                    (a * 256 + b).toDouble()
                }
                pidData.formula.contains("A") -> {
                    dataHex.substring(0, 2).toInt(16).toDouble()
                }
                else -> dataHex.toIntOrNull(16)?.toDouble() ?: 0.0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse response: $rawResponse for PID: ${pidData.pid}", e)
            return 0.0
        }
    }

    private fun isDashboardPid(pidData: PidData): Boolean {
        return pidData.name.contains("coolant", true) ||
               pidData.name.contains("battery", true)
    }

    fun disconnect() {
        pollingJob?.cancel()

        try {
            bluetoothSocket?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error closing Bluetooth socket", e)
        }

        bluetoothSocket = null
        inputStream = null
        outputStream = null
        isInitialized = false

        _connectionState.value = ConnectionState(status = ConnectionStatus.DISCONNECTED)
        Log.d(TAG, "Disconnected from OBD device")
    }

    fun isConnected(): Boolean {
        return _connectionState.value.status == ConnectionStatus.CONNECTED
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "GADIES OBD Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Layanan monitoring OBD2 untuk Suzuki Ertiga Diesel"
                setShowBadge(false)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("GADIES OBD Monitor")
            .setContentText("Memantau data OBD2 Suzuki Ertiga Diesel")
            .setSmallIcon(R.drawable.ic_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()
    }
}
