package com.gadies.suzuki;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import dagger.hilt.android.HiltAndroidApp;

@dagger.hilt.android.HiltAndroidApp
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0016\u00a8\u0006\u0007"}, d2 = {"Lcom/gadies/suzuki/GadiesApplication;", "Landroid/app/Application;", "()V", "createNotificationChannel", "", "onCreate", "Companion", "app_debug"})
public final class GadiesApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String NOTIFICATION_CHANNEL_ID = "gadies_alerts";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String NOTIFICATION_CHANNEL_NAME = "GADIES Alerts";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications for vehicle parameter alerts";
    @org.jetbrains.annotations.NotNull
    public static final com.gadies.suzuki.GadiesApplication.Companion Companion = null;
    
    public GadiesApplication() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    private final void createNotificationChannel() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/gadies/suzuki/GadiesApplication$Companion;", "", "()V", "NOTIFICATION_CHANNEL_DESCRIPTION", "", "NOTIFICATION_CHANNEL_ID", "NOTIFICATION_CHANNEL_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}