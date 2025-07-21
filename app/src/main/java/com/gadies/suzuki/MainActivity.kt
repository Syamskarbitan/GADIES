package com.gadies.suzuki

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gadies.suzuki.service.NotificationService
import com.gadies.suzuki.service.ObdService
import com.gadies.suzuki.ui.screen.*
import com.gadies.suzuki.ui.theme.GADIESTheme
import com.gadies.suzuki.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val TAG = "MainActivity"

    private var obdService: ObdService? = null
    private var isServiceBound = false

    internal val bluetoothEnableLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            Log.d(TAG, "Bluetooth enabled by user.")
            // You can optionally retry the scan here
        } else {
            Log.w(TAG, "User did not enable Bluetooth.")
            showPermissionError() // Or a more specific message
        }
        // Notify the ViewModel that the request has been handled
        viewModel.onBluetoothEnableResult()
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: Service connected")
            val binder = service as ObdService.ObdBinder
            obdService = binder.getService()
            viewModel.setObdService(obdService!!)
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: Service disconnected")
            obdService = null
            isServiceBound = false
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        Log.d(TAG, "Permission result received: $permissions")
        permissions.entries.forEach {
            Log.d(TAG, "Permission: ${it.key}, Granted: ${it.value}")
        }
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            Log.d(TAG, "All permissions granted.")
            startServices()
        } else {
            Log.e(TAG, "One or more permissions were denied.")
            showPermissionError()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Checking Bluetooth permissions...")
        if (!hasBluetoothPermissions()) {
            Log.d(TAG, "Bluetooth permissions not granted. Requesting...")
            requestBluetoothPermissions()
        } else {
            Log.d(TAG, "Bluetooth permissions already granted. Starting services.")
            startServices()
        }

        setContent {
            GADIESTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GadiesApp(this, viewModel)
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

    private fun hasBluetoothPermissions(): Boolean {
        val hasPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12+ requires BLUETOOTH_SCAN, BLUETOOTH_CONNECT, and ACCESS_FINE_LOCATION
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            // Android 11 and below requires ACCESS_FINE_LOCATION and legacy Bluetooth permissions
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
        }
        Log.d(TAG, "hasBluetoothPermissions check returned: $hasPermissions")
        return hasPermissions
    }

    private fun requestBluetoothPermissions() {
        Log.d(TAG, "Requesting Bluetooth permissions for API version ${Build.VERSION.SDK_INT}...")
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12+ requires these three permissions
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            // Android 11 and below requires these permissions
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        }
        permissionLauncher.launch(permissions)
    }

    private fun showPermissionError() {
        runOnUiThread {
            android.widget.Toast.makeText(
                this,
                "Izin Bluetooth dan Lokasi diperlukan untuk memindai perangkat OBD2. Silakan berikan izin di pengaturan aplikasi.",
                android.widget.Toast.LENGTH_LONG
            ).show()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GadiesApp(activity: MainActivity, mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val requestBluetoothEnable by mainViewModel.requestBluetoothEnable.collectAsState()

    LaunchedEffect(requestBluetoothEnable) {
        if (requestBluetoothEnable) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.bluetoothEnableLauncher.launch(enableBtIntent)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Dashboard",
                        route = "dashboard",
                        icon = Icons.Default.Dashboard
                    ),
                    BottomNavItem(
                        name = "Live Data",
                        route = "live_data",
                        icon = Icons.Default.Sensors
                    ),
                    BottomNavItem(
                        name = "AI Analysis",
                        route = "ai_analysis",
                        icon = Icons.Default.AutoAwesome
                    ),
                    BottomNavItem(
                        name = "Settings",
                        route = "settings",
                        icon = Icons.Default.Settings
                    )
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    ) {
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
}
