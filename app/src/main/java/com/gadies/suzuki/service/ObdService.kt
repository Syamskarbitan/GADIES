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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
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
        Log.d(TAG, "ObdService created")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        disconnect()
        serviceScope.cancel()
        Log.d(TAG, "ObdService destroyed")
    }
    
    fun getAvailableDevices(): List<ObdDevice> {
        val devices = mutableListOf<ObdDevice>()
        
        try {
            bluetoothAdapter?.bondedDevices?.forEach { device ->
                if (device.name?.contains("ELM327", true) == true ||
                    device.name?.contains("OBD", true) == true) {
                    devices.add(
                        ObdDevice(
                            name = device.name ?: "Unknown",
                            address = device.address,
                            type = ConnectionType.BLUETOOTH,
                            isAvailable = true
                        )
                    )
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Permission denied for Bluetooth access", e)
        }
        
        return devices
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
            
            // Initialize ELM327
            if (initializeElm327()) {
                _connectionState.value = _connectionState.value.copy(
                    status = ConnectionStatus.CONNECTED,
                    device = device,
                    lastConnected = System.currentTimeMillis()
                )
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
            // Reset ELM327
            sendCommand("ATZ")
            delay(2000)
            
            // Turn off echo
            sendCommand("ATE0")
            
            // Set protocol to automatic
            sendCommand("ATSP0")
            
            // Test communication
            val response = sendCommand("0100")
            isInitialized = response.isSuccess
            
            Log.d(TAG, "ELM327 initialized: $isInitialized")
            isInitialized
        } catch (e: Exception) {
            Log.e(TAG, "ELM327 initialization failed", e)
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
