package com.gadies.suzuki

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gadies.suzuki.service.NotificationService
import com.gadies.suzuki.service.ObdService
import com.gadies.suzuki.ui.screen.*
import com.gadies.suzuki.ui.theme.GADIESTheme
import com.gadies.suzuki.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private var obdService: ObdService? = null
    private var isServiceBound = false
    
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as ObdService.ObdBinder
            obdService = binder.getService()
            isServiceBound = true
        }
        
        override fun onServiceDisconnected(name: ComponentName?) {
            obdService = null
            isServiceBound = false
        }
    }
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            startServices()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        requestPermissions()
        
        setContent {
            GADIESTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GadiesApp()
                }
            }
        }
    }
    
    override fun onStart() {
        super.onStart()
        bindObdService()
    }
    
    override fun onStop() {
        super.onStop()
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }
    
    private fun requestPermissions() {
        val permissions = mutableListOf<String>().apply {
            add(Manifest.permission.BLUETOOTH)
            add(Manifest.permission.BLUETOOTH_ADMIN)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.INTERNET)
            add(Manifest.permission.ACCESS_NETWORK_STATE)
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_CONNECT)
                add(Manifest.permission.BLUETOOTH_SCAN)
            }
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        
        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            startServices()
        }
    }
    
    private fun startServices() {
        // Start OBD Service
        val obdIntent = Intent(this, ObdService::class.java)
        startService(obdIntent)
        
        // Start Notification Service
        val notificationIntent = Intent(this, NotificationService::class.java)
        startService(notificationIntent)
    }
    
    private fun bindObdService() {
        val intent = Intent(this, ObdService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }
}

@Composable
fun GadiesApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    
    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {
        composable("dashboard") {
            DashboardScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        
        composable("live_data") {
            LiveDataScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        
        composable("ai_analysis") {
            AiAnalysisScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        
        composable("ai_chat") {
            AiChatScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        
        composable("settings") {
            SettingsScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        
        composable("connection") {
            ConnectionScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
    }
}
