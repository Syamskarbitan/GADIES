package com.gadies.suzuki.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u000bH\u00c6\u0003J?\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u000b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\u0007H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\""}, d2 = {"Lcom/gadies/suzuki/data/model/ConnectionState;", "", "status", "Lcom/gadies/suzuki/data/model/ConnectionStatus;", "device", "Lcom/gadies/suzuki/data/model/ObdDevice;", "errorMessage", "", "lastConnected", "", "needsBluetoothEnable", "", "(Lcom/gadies/suzuki/data/model/ConnectionStatus;Lcom/gadies/suzuki/data/model/ObdDevice;Ljava/lang/String;JZ)V", "getDevice", "()Lcom/gadies/suzuki/data/model/ObdDevice;", "getErrorMessage", "()Ljava/lang/String;", "getLastConnected", "()J", "getNeedsBluetoothEnable", "()Z", "getStatus", "()Lcom/gadies/suzuki/data/model/ConnectionStatus;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ConnectionState {
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.data.model.ConnectionStatus status = null;
    @org.jetbrains.annotations.Nullable
    private final com.gadies.suzuki.data.model.ObdDevice device = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String errorMessage = null;
    private final long lastConnected = 0L;
    private final boolean needsBluetoothEnable = false;
    
    public ConnectionState(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ConnectionStatus status, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.ObdDevice device, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage, long lastConnected, boolean needsBluetoothEnable) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.ConnectionStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.ObdDevice getDevice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public final long getLastConnected() {
        return 0L;
    }
    
    public final boolean getNeedsBluetoothEnable() {
        return false;
    }
    
    public ConnectionState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.ConnectionStatus component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.gadies.suzuki.data.model.ObdDevice component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component3() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.model.ConnectionState copy(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ConnectionStatus status, @org.jetbrains.annotations.Nullable
    com.gadies.suzuki.data.model.ObdDevice device, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage, long lastConnected, boolean needsBluetoothEnable) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}