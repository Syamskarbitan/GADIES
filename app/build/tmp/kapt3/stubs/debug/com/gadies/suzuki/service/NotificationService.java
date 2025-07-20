package com.gadies.suzuki.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.gadies.suzuki.GadiesApplication;
import com.gadies.suzuki.MainActivity;
import com.gadies.suzuki.R;
import com.gadies.suzuki.data.model.AlertLevel;
import com.gadies.suzuki.data.model.PidAlert;
import com.gadies.suzuki.data.repository.PidRepository;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/gadies/suzuki/service/NotificationService;", "Landroid/app/Service;", "()V", "notificationId", "", "notificationManager", "Landroid/app/NotificationManager;", "pidRepository", "Lcom/gadies/suzuki/data/repository/PidRepository;", "getPidRepository", "()Lcom/gadies/suzuki/data/repository/PidRepository;", "setPidRepository", "(Lcom/gadies/suzuki/data/repository/PidRepository;)V", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "", "onDestroy", "showNotification", "alert", "Lcom/gadies/suzuki/data/model/PidAlert;", "startMonitoring", "Companion", "app_debug"})
public final class NotificationService extends android.app.Service {
    private static final int NOTIFICATION_ID_BASE = 1000;
    @javax.inject.Inject
    public com.gadies.suzuki.data.repository.PidRepository pidRepository;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable
    private android.app.NotificationManager notificationManager;
    private int notificationId = 1000;
    @org.jetbrains.annotations.NotNull
    public static final com.gadies.suzuki.service.NotificationService.Companion Companion = null;
    
    public NotificationService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.gadies.suzuki.data.repository.PidRepository getPidRepository() {
        return null;
    }
    
    public final void setPidRepository(@org.jetbrains.annotations.NotNull
    com.gadies.suzuki.data.repository.PidRepository p0) {
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    private final void startMonitoring() {
    }
    
    private final void showNotification(com.gadies.suzuki.data.model.PidAlert alert) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gadies/suzuki/service/NotificationService$Companion;", "", "()V", "NOTIFICATION_ID_BASE", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}