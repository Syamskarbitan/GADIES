package com.gadies.suzuki.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.data.repository.PidRepository
import com.gadies.suzuki.data.simulator.PidDataSimulator
import com.gadies.suzuki.service.AiService
import com.gadies.suzuki.service.ObdService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pidRepository: PidRepository,
    private val aiService: AiService
) : ViewModel() {

    // PID Data Simulator for testing (DISABLED)
    // private val pidDataSimulator = PidDataSimulator(viewModelScope)

    // PID Data
    val pidDataMap: StateFlow<Map<String, PidData>> = pidRepository.pidDataMap

    // Dashboard PIDs
    val dashboardPids: StateFlow<List<PidData>> = pidRepository.dashboardPids

    // Categorized PIDs
    val categorizedPids: StateFlow<Map<PidCategory, List<PidData>>> = pidRepository.categorizedPids

    // Alerts
    private val _alerts = MutableStateFlow<List<PidAlert>>(emptyList()) // DIUBAH: Tipe data menjadi PidAlert
    val alerts: StateFlow<List<PidAlert>> = _alerts.asStateFlow()

    // Connection State
    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    // Device Management
    private val _availableDevices = MutableStateFlow<List<ObdDevice>>(emptyList())
    val availableDevices: StateFlow<List<ObdDevice>> = _availableDevices.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _requestBluetoothEnable = MutableStateFlow(false)
    val requestBluetoothEnable: StateFlow<Boolean> = _requestBluetoothEnable.asStateFlow()

    // AI Analysis State
    private val _aiAnalysisState = MutableStateFlow<AiAnalysisState>(AiAnalysisState.Idle)
    val aiAnalysisState: StateFlow<AiAnalysisState> = _aiAnalysisState.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    // Settings
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    // UI State
    private val _selectedCategory = MutableStateFlow<PidCategory?>(null)
    val selectedCategory: StateFlow<PidCategory?> = _selectedCategory.asStateFlow()

    private val _expandedCategories = MutableStateFlow<Set<PidCategory>>(emptySet())
    val expandedCategories: StateFlow<Set<PidCategory>> = _expandedCategories.asStateFlow()

    init {
        // Initialize with empty states
        _connectionState.value = ConnectionState()
        _aiAnalysisState.value = AiAnalysisState.Idle
        _settings.value = AppSettings()
        
        // PRODUCTION: No mock data - only real OBD2 data after connection
        // Data will only be populated when actual OBD2 device is connected
    }

    fun updateConnectionState(state: ConnectionState) {
        _connectionState.value = state
        if (state.needsBluetoothEnable) {
            _requestBluetoothEnable.value = true
        }
    }

    fun toggleCategoryExpansion(category: PidCategory) {
        val current = _expandedCategories.value.toMutableSet()
        if (current.contains(category)) {
            current.remove(category)
        } else {
            current.add(category)
        }
        _expandedCategories.value = current
    }

    fun selectCategory(category: PidCategory?) {
        _selectedCategory.value = category
    }

    fun startAiAnalysis(userInputs: Map<String, String>) {
        viewModelScope.launch {
            _aiAnalysisState.value = AiAnalysisState.Loading

            val currentPids = pidRepository.pidDataMap.value.values.toList()
            val request = AiAnalysisRequest(
                userInputs = userInputs,
                pidData = currentPids,
                language = _settings.value.language
            )

            val result = aiService.analyzeVehicleHealth(
                request = request,
                apiKey = _settings.value.openRouterApiKey,
                model = _settings.value.aiModel
            )

            _aiAnalysisState.value = when {
                result.isSuccess -> AiAnalysisState.Success(result.getOrThrow())
                result.isFailure -> AiAnalysisState.Error(
                    result.exceptionOrNull()?.message ?: "Unknown error"
                )
                else -> AiAnalysisState.Error("Unknown error")
            }
        }
    }

    fun sendChatMessage(message: String) {
        viewModelScope.launch {
            // Add user message
            val userMessage = ChatMessage( // DIUBAH: Nama variabel disesuaikan
                content = message, // DIUBAH: Menggunakan parameter 'message'
                isFromUser = true,
                timestamp = System.currentTimeMillis()
            )
            _chatMessages.value = _chatMessages.value + userMessage

            _isChatLoading.value = true

            val result = aiService.sendChatMessage(
                message = message,
                chatHistory = _chatMessages.value,
                language = _settings.value.language,
                apiKey = _settings.value.openRouterApiKey,
                model = _settings.value.aiModel
            )

            when {
                result.isSuccess -> {
                    val aiMessage = ChatMessage(
                        content = result.getOrThrow(),
                        isFromUser = false,
                        timestamp = System.currentTimeMillis()
                    )
                    _chatMessages.value = _chatMessages.value + aiMessage
                }
                result.isFailure -> {
                    val errorMessage = ChatMessage(
                        content = "Sorry, I encountered an error: ${result.exceptionOrNull()?.message}",
                        isFromUser = false,
                        timestamp = System.currentTimeMillis()
                    )
                    _chatMessages.value = _chatMessages.value + errorMessage
                }
            }
            _isChatLoading.value = false
        }
    }

    fun clearChatHistory() {
        _chatMessages.value = emptyList()
        _isChatLoading.value = false
    }

    fun updateSettings(newSettings: AppSettings) {
        _settings.value = newSettings
    }

    // OBD Service reference for real Bluetooth operations
    private var obdService: ObdService? = null
    
    fun setObdService(service: ObdService) {
        Log.d("MainViewModel", "setObdService() called")
        obdService = service
        
        // Observe real scanning state
        viewModelScope.launch {
            service.isScanning.collect { isScanning ->
                Log.d("MainViewModel", "isScanning collected: $isScanning")
                _isScanning.value = isScanning
            }
        }
        
        // Observe discovered devices
        viewModelScope.launch {
            service.discoveredDevices.collect { devices ->
                Log.d("MainViewModel", "discoveredDevices collected: ${devices.size} devices")
                _availableDevices.value = devices
            }
        }
        
        // Observe connection state
        viewModelScope.launch {
            service.connectionState.collect { state ->
                Log.d("MainViewModel", "connectionState collected: $state")
                _connectionState.value = state
            }
        }
    }
    
    // Connection Management - Real Bluetooth scanning
    fun scanForDevices() {
        if (obdService == null) {
            _connectionState.value = ConnectionState(
                status = ConnectionStatus.ERROR,
                errorMessage = "OBD Service not available. Please restart the app."
            )
        } else {
            obdService?.startBluetoothScan()
        }
    }

    fun stopScan() {
        if (obdService == null) {
            _connectionState.value = ConnectionState(
                status = ConnectionStatus.ERROR,
                errorMessage = "OBD Service not available. Please restart the app."
            )
        } else {
            obdService?.stopBluetoothScan()
        }
    }

    fun connectToDevice(device: ObdDevice) {
        if (obdService == null) {
            _connectionState.value = ConnectionState(
                status = ConnectionStatus.ERROR,
                errorMessage = "OBD Service not available. Please restart the app."
            )
            return
        }
        // Set state to connecting immediately for responsive UI
        _connectionState.value = _connectionState.value.copy(
            status = ConnectionStatus.CONNECTING,
            device = device,
            errorMessage = null // Clear previous errors
        )
        // Delegate the actual connection logic to the service in a coroutine
        viewModelScope.launch {
            obdService?.connectToDevice(device)
        }
    }

    fun connectToWiFi(ip: String, port: Int) {
        if (obdService == null) {
            _connectionState.value = ConnectionState(
                status = ConnectionStatus.ERROR,
                errorMessage = "OBD Service not available. Please restart the app."
            )
            return
        }
        val wifiDevice = ObdDevice(name = "WiFi OBD Reader", address = "$ip:$port", type = ConnectionType.WIFI)
        // Set state to connecting immediately for responsive UI
        _connectionState.value = _connectionState.value.copy(
            status = ConnectionStatus.CONNECTING,
            device = wifiDevice,
            errorMessage = null // Clear previous errors
        )
        // Delegate the actual connection logic to the service in a coroutine
        viewModelScope.launch {
            obdService?.connectToDevice(wifiDevice)
        }
    }

    fun disconnectObd() {
        if (obdService == null) {
            _connectionState.value = ConnectionState(
                status = ConnectionStatus.ERROR,
                errorMessage = "OBD Service not available. Please restart the app."
            )
        } else {
            obdService?.disconnect()
        }
    }

    fun connectDirectly() {
        if (obdService == null) {
            _connectionState.value = ConnectionState(
                status = ConnectionStatus.ERROR,
                errorMessage = "OBD Service not available. Please restart the app."
            )
        } else {
            obdService?.connectDirectly("00:1D:A5:68:D0:34")
        }
    }

    // Settings Management (DIUBAH: Menggunakan _settings yang benar)
    fun updateLanguage(language: String) {
        _settings.value = _settings.value.copy(language = language)
    }

    fun updateTheme(theme: String) {
        _settings.value = _settings.value.copy(theme = theme)
    }

    fun updateNotifications(enabled: Boolean) {
        _settings.value = _settings.value.copy(notificationsEnabled = enabled)
    }

    fun updateVibration(enabled: Boolean) {
        _settings.value = _settings.value.copy(vibrationEnabled = enabled)
    }

    fun updateAiEnabled(enabled: Boolean) {
        _settings.value = _settings.value.copy(aiEnabled = enabled)
    }

    fun updateAiModel(model: String) {
        _settings.value = _settings.value.copy(aiModel = model)
    }

    fun updateOpenRouterApiKey(apiKey: String) {
        _settings.value = _settings.value.copy(openRouterApiKey = apiKey)
    }

    fun updateAutoConnect(enabled: Boolean) {
        _settings.value = _settings.value.copy(autoConnect = enabled)
    }

    fun resetThresholds() {
        pidRepository.resetThresholds()
    }

    // Dashboard-specific PID getters
    fun getCoolantTemperature(): StateFlow<PidData?> {
        return pidDataMap.map { pidMap ->
            // Try different coolant temperature PIDs in order of preference
            pidMap["01_05"] ?: // Standard OBD-II coolant temp
            pidMap["21_04"] ?: // Suzuki coolant temp
            pidMap["22_00"]   // D13A coolant temp
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
    }
    
    fun getBatteryVoltage(): StateFlow<PidData?> {
        return pidDataMap.map { pidMap ->
            pidMap["01_42"] ?: // Standard OBD-II control module voltage
            pidMap["22_70"]    // Suzuki battery voltage
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
    }

    fun togglePidAlerts(pid: String, enabled: Boolean) {
        pidRepository.togglePidAlerts(pid, enabled)
    }

    // PID Data Simulation Methods (DISABLED)
    /*
    fun startPidDataSimulation() {
        // pidDataSimulator.startSimulation()
    }

    fun stopPidDataSimulation() {
        // pidDataSimulator.stopSimulation()
    }
    */

    // DIHAPUS: Fungsi duplikat dan tidak perlu
    /*
    private fun updateCategorizedPids() { ... }
    private fun updateDashboardPids() { ... }
    */

    fun clearAlerts() {
        pidRepository.clearAlerts()
    }

    fun resetAiAnalysis() {
        _aiAnalysisState.value = AiAnalysisState.Idle
    }

    fun onBluetoothEnableResult() {
        _requestBluetoothEnable.value = false
        // Optionally, reset the flag in the service's state as well
        val current = _connectionState.value
        if (current.needsBluetoothEnable) {
            _connectionState.value = current.copy(needsBluetoothEnable = false)
        }
    }
}

// State classes
sealed class AiAnalysisState {
    object Idle : AiAnalysisState()
    object Loading : AiAnalysisState()
    data class Success(val response: AiAnalysisResponse) : AiAnalysisState()
    data class Error(val message: String) : AiAnalysisState()
}

data class AppSettings(
    val language: String = "id",
    val theme: String = "system",
    val notificationsEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val aiEnabled: Boolean = true,
    val aiModel: String = "deepseek/deepseek-r1",
    val openRouterApiKey: String = "",
    val autoConnect: Boolean = true,
    val pollingInterval: Long = 1000L
)