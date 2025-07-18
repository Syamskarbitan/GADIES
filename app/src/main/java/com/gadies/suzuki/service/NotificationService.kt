package com.gadies.suzuki.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gadies.suzuki.GadiesApplication
import com.gadies.suzuki.MainActivity
import com.gadies.suzuki.R
import com.gadies.suzuki.data.model.AlertLevel
import com.gadies.suzuki.data.model.PidAlert
import com.gadies.suzuki.data.repository.PidRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : Service() {
    
    companion object {
        private const val NOTIFICATION_ID_BASE = 1000
    }
    
    @Inject
    lateinit var pidRepository: PidRepository
    
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var notificationManager: NotificationManager? = null
    private var notificationId = NOTIFICATION_ID_BASE
    
    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        startMonitoring()
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
    
    private fun startMonitoring() {
        serviceScope.launch {
            pidRepository.alerts.collect { alerts ->
                alerts.firstOrNull()?.let { latestAlert ->
                    showNotification(latestAlert)
                }
            }
        }
    }
    
    private fun showNotification(alert: PidAlert) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val (title, icon, priority) = when (alert.level) {
            AlertLevel.DANGER -> Triple(
                "⚠️ PERINGATAN KRITIS",
                R.drawable.ic_warning,
                NotificationCompat.PRIORITY_HIGH
            )
            AlertLevel.CAUTION -> Triple(
                "⚠️ Perhatian",
                R.drawable.ic_caution,
                NotificationCompat.PRIORITY_DEFAULT
            )
            AlertLevel.NORMAL -> Triple(
                "ℹ️ Informasi",
                R.drawable.ic_info,
                NotificationCompat.PRIORITY_LOW
            )
        }
        
        val notification = NotificationCompat.Builder(this, GadiesApplication.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(alert.message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(alert.message))
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(if (alert.level == AlertLevel.DANGER) longArrayOf(0, 500, 200, 500) else longArrayOf(0, 200))
            .build()
        
        notificationManager?.notify(notificationId++, notification)
        
        // Reset notification ID to prevent overflow
        if (notificationId > NOTIFICATION_ID_BASE + 100) {
            notificationId = NOTIFICATION_ID_BASE
        }
    }
}
