package com.gadies.suzuki.data.repository;

import android.content.Context;
import com.gadies.suzuki.data.model.*;
import com.google.gson.Gson;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000eH\u0002J\u0006\u0010\u001f\u001a\u00020\u001dJ\b\u0010 \u001a\u00020\u001dH\u0002J\u0006\u0010!\u001a\u00020\u001dJ\u0016\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\u00112\u0006\u0010$\u001a\u00020%J\b\u0010&\u001a\u00020\u001dH\u0002J\u0006\u0010\'\u001a\u00020\u001dJ\u001a\u0010(\u001a\u00020\u001d2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e0\fJ\u001e\u0010*\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\u00112\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0011R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u000b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R)\u0010\u0016\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u001a\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e0\f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015\u00a8\u0006."}, d2 = {"Lcom/gadies/suzuki/data/repository/PidRepository;", "", "context", "Landroid/content/Context;", "gson", "Lcom/google/gson/Gson;", "(Landroid/content/Context;Lcom/google/gson/Gson;)V", "_alerts", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/gadies/suzuki/data/model/PidAlert;", "_categorizedPids", "", "Lcom/gadies/suzuki/data/model/PidCategory;", "Lcom/gadies/suzuki/data/model/PidData;", "_dashboardPids", "_pidDataMap", "", "alerts", "Lkotlinx/coroutines/flow/StateFlow;", "getAlerts", "()Lkotlinx/coroutines/flow/StateFlow;", "categorizedPids", "getCategorizedPids", "dashboardPids", "getDashboardPids", "pidDataMap", "getPidDataMap", "checkAndCreateAlert", "", "pidData", "clearAlerts", "loadPidConfiguration", "resetThresholds", "togglePidAlerts", "pid", "enabled", "", "updateCategorizedPids", "updateDashboardPids", "updatePidDataMap", "newData", "updatePidValue", "value", "", "rawHex", "app_debug"})
public final class PidRepository {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.gadies.suzuki.data.model.PidData>> _pidDataMap = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.gadies.suzuki.data.model.PidData>> pidDataMap = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.gadies.suzuki.data.model.PidData>> _dashboardPids = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.PidData>> dashboardPids = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<com.gadies.suzuki.data.model.PidCategory, java.util.List<com.gadies.suzuki.data.model.PidData>>> _categorizedPids = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<com.gadies.suzuki.data.model.PidCategory, java.util.List<com.gadies.suzuki.data.model.PidData>>> categorizedPids = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.gadies.suzuki.data.model.PidAlert>> _alerts = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.PidAlert>> alerts = null;
    
    @javax.inject.Inject
    public PidRepository(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.google.gson.Gson gson) {
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
    
    private final void loadPidConfiguration() {
    }
    
    public final void updatePidValue(@org.jetbrains.annotations.NotNull
    java.lang.String pid, double value, @org.jetbrains.annotations.NotNull
    java.lang.String rawHex) {
    }
    
    private final void checkAndCreateAlert(com.gadies.suzuki.data.model.PidData pidData) {
    }
    
    private final void updateCategorizedPids() {
    }
    
    public final void clearAlerts() {
    }
    
    public final void togglePidAlerts(@org.jetbrains.annotations.NotNull
    java.lang.String pid, boolean enabled) {
    }
    
    public final void resetThresholds() {
    }
    
    public final void updatePidDataMap(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, com.gadies.suzuki.data.model.PidData> newData) {
    }
    
    public final void updateDashboardPids() {
    }
}