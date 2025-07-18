package com.gadies.suzuki.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.data.repository.PidRepository
import com.gadies.suzuki.data.simulator.PidDataSimulator
import com.gadies.suzuki.service.AiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pidRepository: PidRepository,
    private val aiService: AiService
) : ViewModel() {
    
    // PID Data Simulator for testing
    private val pidDataSimulator = PidDataSimulator(viewModelScope)
    
    // PID Data
    val dashboardPids = pidRepository.dashboardPids.asStateFlow()
    val categorizedPids = pidRepository.categorizedPids.asStateFlow()
    val alerts = pidRepository.alerts.asStateFlow()
    
    // Connection State
    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState = _connectionState.asStateFlow()
    
    // Device Management
    private val _availableDevices = MutableStateFlow<List<ObdDevice>>(emptyList())
    val availableDevices = _availableDevices.asStateFlow()
    
    private val _isScanning = MutableStateFlow(false)
    val isScanning = _isScanning.asStateFlow()
    
    // AI Analysis State
    private val _aiAnalysisState = MutableStateFlow<AiAnalysisState>(AiAnalysisState.Idle)
    val aiAnalysisState = _aiAnalysisState.asStateFlow()
    
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages = _chatMessages.asStateFlow()
    
    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading = _isChatLoading.asStateFlow()
    
    // Settings
    private val _settings = MutableStateFlow(AppSettings())
    val settings = _settings.asStateFlow()
    
    // UI State
    private val _selectedCategory = MutableStateFlow<PidCategory?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()
    
    private val _expandedCategories = MutableStateFlow<Set<PidCategory>>(emptySet())
    val expandedCategories = _expandedCategories.asStateFlow()
    
    init {
        // Initialize with empty states
        _connectionState.value = ConnectionState()
        _aiAnalysisState.value = AiAnalysisState.Idle
        _appSettings.value = AppSettings()
        
        // Start simulator for testing (remove in production)
        startPidDataSimulation()
        
        // Observe simulated data
        viewModelScope.launch {
            pidDataSimulator.simulatedData.collect { simulatedData ->
                pidRepository.updatePidDataMap(simulatedData)
            }
        }
    }
    
    fun updateConnectionState(state: ConnectionState) {
        _connectionState.value = state
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
            val userMessage = ChatMessage(
                content = message,
                sender = MessageSender.USER,
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
                        sender = MessageSender.AI,
                        timestamp = System.currentTimeMillis()
                    )
                    _chatMessages.value = _chatMessages.value + aiMessage
                }
                result.isFailure -> {
                    val errorMessage = ChatMessage(
                        content = "Sorry, I encountered an error: ${result.exceptionOrNull()?.message}",
                        sender = MessageSender.AI,
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
    
    // Connection Management
    fun scanForDevices() {
        _isScanning.value = true
        // TODO: Implement actual device scanning
        viewModelScope.launch {
            // Simulate scanning delay
            kotlinx.coroutines.delay(3000)
            _availableDevices.value = listOf(
                ObdDevice(
                    name = "ELM327 Bluetooth",
                    address = "00:1D:A5:68:98:8B",
                    type = ConnectionType.BLUETOOTH,
                    rssi = -45
                ),
                ObdDevice(
                    name = "OBD2 Scanner",
                    address = "00:1A:7D:DA:71:13",
                    type = ConnectionType.BLUETOOTH,
                    rssi = -67
                )
            )
            _isScanning.value = false
        }
    }
    
    fun stopScan() {
        _isScanning.value = false
    }
    
    fun connectToDevice(device: ObdDevice) {
        _connectionState.value = _connectionState.value.copy(
            status = ConnectionStatus.CONNECTING,
            device = device
        )
        // TODO: Implement actual device connection
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.CONNECTED
            )
        }
    }
    
    fun connectToWiFi(ip: String, port: Int) {
        val wifiDevice = ObdDevice(
            name = "ELM327 WiFi",
            address = "$ip:$port",
            type = ConnectionType.WIFI
        )
        _connectionState.value = _connectionState.value.copy(
            status = ConnectionStatus.CONNECTING,
            device = wifiDevice
        )
        // TODO: Implement actual WiFi connection
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            _connectionState.value = _connectionState.value.copy(
                status = ConnectionStatus.CONNECTED
            )
        }
    }
    
    fun disconnectObd() {
        _connectionState.value = ConnectionState(
            status = ConnectionStatus.DISCONNECTED
        )
    }
    
    // Settings Management
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
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }
    
    fun getBatteryVoltage(): StateFlow<PidData?> {
        return pidDataMap.map { pidMap ->
            // Try different battery voltage PIDs in order of preference
            pidMap["21_08"] ?: // Suzuki battery voltage
            pidMap["22_0A"] ?: // D13A battery voltage
            pidMap["22_0B"]   // D13A alternator voltage (fallback)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }
    
    fun togglePidAlerts(pid: String, enabled: Boolean) {
        pidRepository.togglePidAlerts(pid, enabled)
    }
    
    // PID Data Simulation Methods
    fun startPidDataSimulation() {
        pidDataSimulator.startSimulation()
    }
    
    fun stopPidDataSimulation() {
        pidDataSimulator.stopSimulation()
    }
    
    private fun updateCategorizedPids() {
        // This will be handled by the repository
    }
    
    private fun updateDashboardPids() {
        // This will be handled by the repository
    }
    
    fun clearAlerts() {
        pidRepository.clearAlerts()
    }
    
    fun resetAiAnalysis() {
        _aiAnalysisState.value = AiAnalysisState.Idle
    }
    
    // Get coolant temperature for dashboard
    fun getCoolantTemperature(): StateFlow<PidData?> {
        return pidRepository.pidDataMap.map { pidMap ->
            pidMap.values.find { it.name.contains("coolant", true) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }
    
    // Get battery voltage for dashboard
    fun getBatteryVoltage(): StateFlow<PidData?> {
        return pidRepository.pidDataMap.map { pidMap ->
            pidMap.values.find { it.name.contains("battery", true) && it.name.contains("voltage", true) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
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
    val language: String = "id", // "id" or "en"
    val theme: String = "system", // "system", "light", or "dark"
    val notificationsEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val aiEnabled: Boolean = true,
    val aiModel: String = "deepseek/deepseek-r1",
    val openRouterApiKey: String = "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",
    val autoConnect: Boolean = true,
    val pollingInterval: Long = 1000L // milliseconds
)
