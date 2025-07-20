package com.gadies.suzuki.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.gadies.suzuki.data.model.*;
import com.gadies.suzuki.data.repository.PidRepository;
import com.gadies.suzuki.data.simulator.PidDataSimulator;
import com.gadies.suzuki.service.AiService;
import com.gadies.suzuki.service.ObdService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\'\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010@\u001a\u00020AJ\u0006\u0010B\u001a\u00020AJ\u000e\u0010C\u001a\u00020A2\u0006\u0010D\u001a\u00020\u000eJ\u0016\u0010E\u001a\u00020A2\u0006\u0010F\u001a\u0002062\u0006\u0010G\u001a\u00020HJ\u0006\u0010I\u001a\u00020AJ\u000e\u0010J\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\'0\u001eJ\u000e\u0010K\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\'0\u001eJ\u0006\u0010L\u001a\u00020AJ\u0006\u0010M\u001a\u00020AJ\u0006\u0010N\u001a\u00020AJ\u0006\u0010O\u001a\u00020AJ\u0010\u0010P\u001a\u00020A2\b\u0010Q\u001a\u0004\u0018\u00010\u0015J\u000e\u0010R\u001a\u00020A2\u0006\u0010S\u001a\u000206J\u000e\u0010T\u001a\u00020A2\u0006\u0010U\u001a\u000204J\u001a\u0010V\u001a\u00020A2\u0012\u0010W\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u0002060&J\u0006\u0010X\u001a\u00020AJ\u0006\u0010Y\u001a\u00020AJ\u0006\u0010Z\u001a\u00020AJ\u000e\u0010[\u001a\u00020A2\u0006\u0010Q\u001a\u00020\u0015J\u0016\u0010\\\u001a\u00020A2\u0006\u0010]\u001a\u0002062\u0006\u0010^\u001a\u00020\u0017J\u000e\u0010_\u001a\u00020A2\u0006\u0010^\u001a\u00020\u0017J\u000e\u0010`\u001a\u00020A2\u0006\u0010a\u001a\u000206J\u000e\u0010b\u001a\u00020A2\u0006\u0010^\u001a\u00020\u0017J\u000e\u0010c\u001a\u00020A2\u0006\u0010d\u001a\u00020\u0012J\u000e\u0010e\u001a\u00020A2\u0006\u0010f\u001a\u000206J\u000e\u0010g\u001a\u00020A2\u0006\u0010^\u001a\u00020\u0017J\u000e\u0010h\u001a\u00020A2\u0006\u0010i\u001a\u000206J\u000e\u0010j\u001a\u00020A2\u0006\u0010k\u001a\u00020\u001cJ\u000e\u0010l\u001a\u00020A2\u0006\u0010m\u001a\u000206J\u000e\u0010n\u001a\u00020A2\u0006\u0010^\u001a\u00020\u0017R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00170\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u001d\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010 R)\u0010%\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u000b0&0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010 R\u001d\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010 R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00120\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010 R\u001d\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u000b0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u001d\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010 R\u0017\u00101\u001a\b\u0012\u0004\u0012\u00020\u00170\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010 R\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\u00170\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010 R\u0010\u00103\u001a\u0004\u0018\u000104X\u0082\u000e\u00a2\u0006\u0002\n\u0000R#\u00105\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\'0&0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010 R\u000e\u00108\u001a\u000209X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00170\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010 R\u0019\u0010<\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010 R\u0017\u0010>\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010 \u00a8\u0006o"}, d2 = {"Lcom/gadies/suzuki/ui/viewmodel/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "pidRepository", "Lcom/gadies/suzuki/data/repository/PidRepository;", "aiService", "Lcom/gadies/suzuki/service/AiService;", "(Lcom/gadies/suzuki/data/repository/PidRepository;Lcom/gadies/suzuki/service/AiService;)V", "_aiAnalysisState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gadies/suzuki/ui/viewmodel/AiAnalysisState;", "_alerts", "", "Lcom/gadies/suzuki/data/model/PidAlert;", "_availableDevices", "Lcom/gadies/suzuki/data/model/ObdDevice;", "_chatMessages", "Lcom/gadies/suzuki/data/model/ChatMessage;", "_connectionState", "Lcom/gadies/suzuki/data/model/ConnectionState;", "_expandedCategories", "", "Lcom/gadies/suzuki/data/model/PidCategory;", "_isChatLoading", "", "_isScanning", "_requestBluetoothEnable", "_selectedCategory", "_settings", "Lcom/gadies/suzuki/ui/viewmodel/AppSettings;", "aiAnalysisState", "Lkotlinx/coroutines/flow/StateFlow;", "getAiAnalysisState", "()Lkotlinx/coroutines/flow/StateFlow;", "alerts", "getAlerts", "availableDevices", "getAvailableDevices", "categorizedPids", "", "Lcom/gadies/suzuki/data/model/PidData;", "getCategorizedPids", "chatMessages", "getChatMessages", "connectionState", "getConnectionState", "dashboardPids", "getDashboardPids", "expandedCategories", "getExpandedCategories", "isChatLoading", "isScanning", "obdService", "Lcom/gadies/suzuki/service/ObdService;", "pidDataMap", "", "getPidDataMap", "pidDataSimulator", "Lcom/gadies/suzuki/data/simulator/PidDataSimulator;", "requestBluetoothEnable", "getRequestBluetoothEnable", "selectedCategory", "getSelectedCategory", "settings", "getSettings", "clearAlerts", "", "clearChatHistory", "connectToDevice", "device", "connectToWiFi", "ip", "port", "", "disconnectObd", "getBatteryVoltage", "getCoolantTemperature", "onBluetoothEnableResult", "resetAiAnalysis", "resetThresholds", "scanForDevices", "selectCategory", "category", "sendChatMessage", "message", "setObdService", "service", "startAiAnalysis", "userInputs", "startPidDataSimulation", "stopPidDataSimulation", "stopScan", "toggleCategoryExpansion", "togglePidAlerts", "pid", "enabled", "updateAiEnabled", "updateAiModel", "model", "updateAutoConnect", "updateConnectionState", "state", "updateLanguage", "language", "updateNotifications", "updateOpenRouterApiKey", "apiKey", "updateSettings", "newSettings", "updateTheme", "theme", "updateVibration", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.data.repository.PidRepository pidRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.service.AiService aiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.data.simulator.PidDataSimulator pidDataSimulator = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.gadies.suzuki.data.model.PidData>> pidDataMap = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.PidData>> dashboardPids = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<com.gadies.suzuki.data.model.PidCategory, java.util.List<com.gadies.suzuki.data.model.PidData>>> categorizedPids = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.gadies.suzuki.data.model.PidAlert>> _alerts = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.PidAlert>> alerts = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gadies.suzuki.data.model.ConnectionState> _connectionState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.ConnectionState> connectionState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.gadies.suzuki.data.model.ObdDevice>> _availableDevices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.ObdDevice>> availableDevices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isScanning = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _requestBluetoothEnable = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> requestBluetoothEnable = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gadies.suzuki.ui.viewmodel.AiAnalysisState> _aiAnalysisState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.ui.viewmodel.AiAnalysisState> aiAnalysisState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.gadies.suzuki.data.model.ChatMessage>> _chatMessages = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.ChatMessage>> chatMessages = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isChatLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isChatLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gadies.suzuki.ui.viewmodel.AppSettings> _settings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.ui.viewmodel.AppSettings> settings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gadies.suzuki.data.model.PidCategory> _selectedCategory = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.PidCategory> selectedCategory = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<com.gadies.suzuki.data.model.PidCategory>> _expandedCategories = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<com.gadies.suzuki.data.model.PidCategory>> expandedCategories = null;
    @org.jetbrains.annotations.Nullable
    private com.gadies.suzuki.service.ObdService obdService;
    
    @javax.inject.Inject
    public MainViewModel(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.repository.PidRepository pidRepository, @org.jetbrains.annotations.NotNull
    com.gadies.suzuki.service.AiService aiService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.gadies.suzuki.data.model.PidData>> getPidDataMap() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.PidData>> getDashboardPids() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<com.gadies.suzuki.data.model.PidCategory, java.util.List<com.gadies.suzuki.data.model.PidData>>> getCategorizedPids() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.PidAlert>> getAlerts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.ConnectionState> getConnectionState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.ObdDevice>> getAvailableDevices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getRequestBluetoothEnable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.ui.viewmodel.AiAnalysisState> getAiAnalysisState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.ChatMessage>> getChatMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isChatLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.ui.viewmodel.AppSettings> getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.PidCategory> getSelectedCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<com.gadies.suzuki.data.model.PidCategory>> getExpandedCategories() {
        return null;
    }
    
    public final void updateConnectionState(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ConnectionState state) {
    }
    
    public final void toggleCategoryExpansion(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.PidCategory category) {
    }
    
    public final void selectCategory(@org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.PidCategory category) {
    }
    
    public final void startAiAnalysis(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> userInputs) {
    }
    
    public final void sendChatMessage(@org.jetbrains.annotations.NotNull
    java.lang.String message) {
    }
    
    public final void clearChatHistory() {
    }
    
    public final void updateSettings(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.ui.viewmodel.AppSettings newSettings) {
    }
    
    public final void setObdService(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.service.ObdService service) {
    }
    
    public final void scanForDevices() {
    }
    
    public final void stopScan() {
    }
    
    public final void connectToDevice(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ObdDevice device) {
    }
    
    public final void connectToWiFi(@org.jetbrains.annotations.NotNull
    java.lang.String ip, int port) {
    }
    
    public final void disconnectObd() {
    }
    
    public final void updateLanguage(@org.jetbrains.annotations.NotNull
    java.lang.String language) {
    }
    
    public final void updateTheme(@org.jetbrains.annotations.NotNull
    java.lang.String theme) {
    }
    
    public final void updateNotifications(boolean enabled) {
    }
    
    public final void updateVibration(boolean enabled) {
    }
    
    public final void updateAiEnabled(boolean enabled) {
    }
    
    public final void updateAiModel(@org.jetbrains.annotations.NotNull
    java.lang.String model) {
    }
    
    public final void updateOpenRouterApiKey(@org.jetbrains.annotations.NotNull
    java.lang.String apiKey) {
    }
    
    public final void updateAutoConnect(boolean enabled) {
    }
    
    public final void resetThresholds() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.PidData> getCoolantTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.PidData> getBatteryVoltage() {
        return null;
    }
    
    public final void togglePidAlerts(@org.jetbrains.annotations.NotNull
    java.lang.String pid, boolean enabled) {
    }
    
    public final void startPidDataSimulation() {
    }
    
    public final void stopPidDataSimulation() {
    }
    
    public final void clearAlerts() {
    }
    
    public final void resetAiAnalysis() {
    }
    
    public final void onBluetoothEnableResult() {
    }
}