package com.gadies.suzuki.service

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.data.repository.PidRepository
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

class ObdService : Service() {
    
    companion object {
        private const val TAG = "ObdService"
        private const val ELM327_UUID = "00001101-0000-1000-8000-00805F9B34FB"
        private const val POLLING_INTERVAL = 1000L // 1 second
    }
    
    @Inject
    lateinit var pidRepository: PidRepository
    
    private val binder = ObdBinder()
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    private var bluetoothAdapter: BluetoothAdapter? = null
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
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        
        // Register broadcast receiver for Bluetooth discovery
        val filter = android.content.IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        registerReceiver(bluetoothReceiver, filter)
        
        Log.d(TAG, "ObdService created")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Unregister broadcast receiver
        try {
            unregisterReceiver(bluetoothReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "Error unregistering receiver", e)
        }
        
        // Stop any ongoing scan
        stopBluetoothScan()
        
        disconnect()
        serviceScope.cancel()
        Log.d(TAG, "ObdService destroyed")
    }
    
    // Discovered devices during scanning
    private val _discoveredDevices = MutableStateFlow<List<ObdDevice>>(emptyList())
    val discoveredDevices: StateFlow<List<ObdDevice>> = _discoveredDevices.asStateFlow()
    
    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()
    
    private val bluetoothReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: android.content.Context?, intent: android.content.Intent?) {
    when (intent?.action) {
        BluetoothDevice.ACTION_FOUND -> {
            val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            device?.let {
                val obdDevice = ObdDevice(
                    name = it.name ?: "Unknown Device",
                    address = it.address,
                    type = ConnectionType.BLUETOOTH,
                    isAvailable = true
                )

                val currentDevices = _discoveredDevices.value.toMutableList()
                if (!currentDevices.any { existing -> existing.address == obdDevice.address }) {
                    currentDevices.add(obdDevice)
                    _discoveredDevices.value = currentDevices
                    Log.d(TAG, "Discovered device: ${obdDevice.name} - ${obdDevice.address}")
                }
            }
        }
        BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
            _isScanning.value = false
            Log.d(TAG, "Bluetooth discovery finished")
        }
    }
}
    }
    
    fun startBluetoothScan(): Boolean {
    if (_isScanning.value) {
        Log.d(TAG, "Scan already in progress")
        return false
    }

    // Permission check
    val hasPermission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) == android.content.pm.PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }
    if (!hasPermission) {
        Log.e(TAG, "Bluetooth scan/connect permission not granted. Cannot start scan.")
        _isScanning.value = false
        return false
    }

    try {
        bluetoothAdapter?.let { adapter ->
            if (!adapter.isEnabled) {
                Log.e(TAG, "Bluetooth is not enabled")
                return false
            }

            // Clear previous results
            _discoveredDevices.value = emptyList()

            // Cancel any ongoing discovery
            if (adapter.isDiscovering) {
                adapter.cancelDiscovery()
            }

            // Start discovery
            val started = adapter.startDiscovery()
            if (started) {
                _isScanning.value = true
                Log.d(TAG, "Bluetooth discovery started")
            } else {
                Log.e(TAG, "Failed to start Bluetooth discovery")
            }
            return started
        }
    } catch (e: SecurityException) {
        _isScanning.value = false
        Log.e(TAG, "Permission denied for Bluetooth scanning: ${e.message}", e)
    }
    return false
}
    
    fun stopBluetoothScan() {
        try {
            bluetoothAdapter?.cancelDiscovery()
            _isScanning.value = false
            Log.d(TAG, "Bluetooth discovery stopped")
        } catch (e: SecurityException) {
            Log.e(TAG, "Permission denied for stopping Bluetooth scan", e)
        }
    }
    
    fun getAvailableDevices(): List<ObdDevice> {
        // Return discovered devices from actual scanning, not bonded devices
        return _discoveredDevices.value
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
            
            // Initialize ELM327 - status will be updated inside initializeElm327()
            if (initializeElm327()) {
                // Only start polling after successful OBD initialization
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
    
    private suspend fun connectWifi(device: ObdDevice): Boolean {
        // WiFi ELM327 connection implementation
        // This would typically connect to a WiFi network and communicate via TCP
        return false // Placeholder for WiFi implementation
    }
    
    private suspend fun initializeElm327(): Boolean {
        return try {
            Log.d(TAG, "Starting ELM327 initialization...")
            
            // Reset ELM327
            val resetResponse = sendCommand("ATZ")
            if (!resetResponse.isSuccess) {
                Log.e(TAG, "ELM327 reset failed")
                return false
            }
            delay(2000)
            
            // Turn off echo
            val echoResponse = sendCommand("ATE0")
            if (!echoResponse.isSuccess) {
                Log.e(TAG, "ELM327 echo off failed")
                return false
            }
            
            // Turn off line feeds
            sendCommand("ATL0")
            
            // Turn off spaces
            sendCommand("ATS0")
            
            // Set protocol to automatic
            sendCommand("ATSP0")
            
            // Test OBD communication with vehicle
            val testResponse = sendCommand("0100")
            if (!testResponse.isSuccess || testResponse.rawResponse.contains("NO DATA") || 
                testResponse.rawResponse.contains("UNABLE TO CONNECT")) {
                Log.e(TAG, "Vehicle OBD communication test failed: ${testResponse.rawResponse}")
                isInitialized = false
                return false
            }
            
            // Verify we can get vehicle identification
            val vinResponse = sendCommand("0902")
            
            isInitialized = true
            Log.d(TAG, "ELM327 successfully initialized and connected to vehicle")
            
            // Update connection state to connected only after successful OBD communication
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
                    // Poll dashboard PIDs first (higher priority)
                    pidRepository.dashboardPids.value.forEach { pidData ->
                        if (pidData.isEnabled) {
                            pollPid(pidData)
                        }
                    }
                    
                    // Poll other PIDs with lower frequency
                    pidRepository.pidDataMap.value.values.forEach { pidData ->
                        if (pidData.isEnabled && !isDashboardPid(pidData)) {
                            pollPid(pidData)
                        }
                    }
                    
                    delay(POLLING_INTERVAL)
                } catch (e: Exception) {
                    Log.e(TAG, "Polling error", e)
                    if (e is IOException) {
                        // Connection lost
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
            // Remove spaces and convert to uppercase
            val cleanResponse = rawResponse.replace(" ", "").uppercase()
            
            // Extract data bytes (skip header)
            val dataStart = when (pidData.mode) {
                "01" -> 4 // Skip "41XX"
                "21" -> 4 // Skip "61XX" 
                "22" -> 4 // Skip "62XX"
                else -> 0
            }
            
            if (cleanResponse.length <= dataStart) return 0.0
            
            val dataHex = cleanResponse.substring(dataStart)
            
            // Parse based on formula
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
}
