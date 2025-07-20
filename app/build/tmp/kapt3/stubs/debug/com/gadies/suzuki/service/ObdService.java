package com.gadies.suzuki.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.gadies.suzuki.MainActivity;
import com.gadies.suzuki.R;
import com.gadies.suzuki.data.model.*;
import com.gadies.suzuki.data.repository.PidRepository;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.StateFlow;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 M2\u00020\u0001:\u0002MNB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\n2\u0006\u0010*\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010+J\u0016\u0010-\u001a\u00020\n2\u0006\u0010*\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010+J\b\u0010.\u001a\u00020/H\u0002J\b\u00100\u001a\u000201H\u0002J\u0006\u00102\u001a\u000201J\f\u00103\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u000e\u00104\u001a\u00020\nH\u0082@\u00a2\u0006\u0002\u00105J\u0006\u00106\u001a\u00020\nJ\u0010\u00107\u001a\u00020\n2\u0006\u00108\u001a\u000209H\u0002J\u0010\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=H\u0016J\b\u0010>\u001a\u000201H\u0016J\b\u0010?\u001a\u000201H\u0016J\u0018\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u00108\u001a\u000209H\u0002J\u0016\u0010D\u001a\u0002012\u0006\u00108\u001a\u000209H\u0082@\u00a2\u0006\u0002\u0010EJ\u0016\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020CH\u0082@\u00a2\u0006\u0002\u0010IJ\u0006\u0010J\u001a\u000201J\b\u0010K\u001a\u000201H\u0002J\u0006\u0010L\u001a\u000201R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00060\fR\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001f\u001a\u00020 8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020(X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006O"}, d2 = {"Lcom/gadies/suzuki/service/ObdService;", "Landroid/app/Service;", "()V", "_connectionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gadies/suzuki/data/model/ConnectionState;", "_discoveredDevices", "", "Lcom/gadies/suzuki/data/model/ObdDevice;", "_isScanning", "", "binder", "Lcom/gadies/suzuki/service/ObdService$ObdBinder;", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "bluetoothReceiver", "Landroid/content/BroadcastReceiver;", "bluetoothSocket", "Landroid/bluetooth/BluetoothSocket;", "connectionState", "Lkotlinx/coroutines/flow/StateFlow;", "getConnectionState", "()Lkotlinx/coroutines/flow/StateFlow;", "discoveredDevices", "getDiscoveredDevices", "inputStream", "Ljava/io/InputStream;", "isInitialized", "isScanning", "outputStream", "Ljava/io/OutputStream;", "pidRepository", "Lcom/gadies/suzuki/data/repository/PidRepository;", "getPidRepository", "()Lcom/gadies/suzuki/data/repository/PidRepository;", "setPidRepository", "(Lcom/gadies/suzuki/data/repository/PidRepository;)V", "pollingJob", "Lkotlinx/coroutines/Job;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "connectBluetooth", "device", "(Lcom/gadies/suzuki/data/model/ObdDevice;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectToDevice", "connectWifi", "createNotification", "Landroid/app/Notification;", "createNotificationChannel", "", "disconnect", "getAvailableDevices", "initializeElm327", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isConnected", "isDashboardPid", "pidData", "Lcom/gadies/suzuki/data/model/PidData;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "parseObdResponse", "", "rawResponse", "", "pollPid", "(Lcom/gadies/suzuki/data/model/PidData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendCommand", "Lcom/gadies/suzuki/data/model/ObdResponse;", "command", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startBluetoothScan", "startPolling", "stopBluetoothScan", "Companion", "ObdBinder", "app_debug"})
public final class ObdService extends android.app.Service {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "ObdService";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String ELM327_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private static final long POLLING_INTERVAL = 1000L;
    private static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_ID = "OBD_SERVICE_CHANNEL";
    @javax.inject.Inject
    public com.gadies.suzuki.data.repository.PidRepository pidRepository;
    @org.jetbrains.annotations.NotNull
    private final com.gadies.suzuki.service.ObdService.ObdBinder binder = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable
    private android.bluetooth.BluetoothAdapter bluetoothAdapter;
    @org.jetbrains.annotations.Nullable
    private android.bluetooth.BluetoothSocket bluetoothSocket;
    @org.jetbrains.annotations.Nullable
    private java.io.InputStream inputStream;
    @org.jetbrains.annotations.Nullable
    private java.io.OutputStream outputStream;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gadies.suzuki.data.model.ConnectionState> _connectionState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.ConnectionState> connectionState = null;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job pollingJob;
    private boolean isInitialized = false;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.gadies.suzuki.data.model.ObdDevice>> _discoveredDevices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.ObdDevice>> discoveredDevices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isScanning = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.BroadcastReceiver bluetoothReceiver = null;
    @org.jetbrains.annotations.NotNull
    public static final com.gadies.suzuki.service.ObdService.Companion Companion = null;
    
    public ObdService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.repository.PidRepository getPidRepository() {
        return null;
    }
    
    public final void setPidRepository(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.repository.PidRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.gadies.suzuki.data.model.ConnectionState> getConnectionState() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.os.IBinder onBind(@org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gadies.suzuki.data.model.ObdDevice>> getDiscoveredDevices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning() {
        return null;
    }
    
    public final void startBluetoothScan() {
    }
    
    public final void stopBluetoothScan() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.gadies.suzuki.data.model.ObdDevice> getAvailableDevices() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object connectToDevice(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.model.ObdDevice device, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object connectBluetooth(com.gadies.suzuki.data.model.ObdDevice device, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object connectWifi(com.gadies.suzuki.data.model.ObdDevice device, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object initializeElm327(kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object sendCommand(java.lang.String command, kotlin.coroutines.Continuation<? super com.gadies.suzuki.data.model.ObdResponse> $completion) {
        return null;
    }
    
    private final void startPolling() {
    }
    
    private final java.lang.Object pollPid(com.gadies.suzuki.data.model.PidData pidData, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final double parseObdResponse(java.lang.String rawResponse, com.gadies.suzuki.data.model.PidData pidData) {
        return 0.0;
    }
    
    private final boolean isDashboardPid(com.gadies.suzuki.data.model.PidData pidData) {
        return false;
    }
    
    public final void disconnect() {
    }
    
    public final boolean isConnected() {
        return false;
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/gadies/suzuki/service/ObdService$Companion;", "", "()V", "CHANNEL_ID", "", "ELM327_UUID", "NOTIFICATION_ID", "", "POLLING_INTERVAL", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/gadies/suzuki/service/ObdService$ObdBinder;", "Landroid/os/Binder;", "(Lcom/gadies/suzuki/service/ObdService;)V", "getService", "Lcom/gadies/suzuki/service/ObdService;", "app_debug"})
    public final class ObdBinder extends android.os.Binder {
        
        public ObdBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.gadies.suzuki.service.ObdService getService() {
            return null;
        }
    }
}