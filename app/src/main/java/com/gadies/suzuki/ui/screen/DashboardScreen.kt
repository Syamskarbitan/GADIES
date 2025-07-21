package com.gadies.suzuki.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gadies.suzuki.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val connectionState by viewModel.connectionState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ConnectionStatusCard(
                connectionState = connectionState,
                onConnectClick = { navController.navigate("connection") }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DataWidget(
                    title = "RPM",
                    value = "2,500",
                    modifier = Modifier.weight(1f)
                )
                DataWidget(
                    title = "Speed",
                    value = "60",
                    modifier = Modifier.weight(1f)
                )
            }
            DataWidget(
                title = "Coolant Temp",
                value = "90°C",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ConnectionStatusCard(
    connectionState: com.gadies.suzuki.data.model.ConnectionState,
    onConnectClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Connection Status",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status: ${connectionState.status}",
                style = MaterialTheme.typography.bodyLarge
            )
            connectionState.device?.let {
                Text(
                    text = "Device: ${it.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onConnectClick) {
                Icon(Icons.Default.Bluetooth, contentDescription = "Connect")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Connect to OBD-II")
            }
        }
    }
}
