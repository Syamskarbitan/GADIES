package com.gadies.suzuki.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gadies.suzuki.data.model.ObdDevice
import com.gadies.suzuki.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val connectionState by viewModel.connectionState.collectAsState()
    val availableDevices by viewModel.availableDevices.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Connect to OBD-II") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isScanning) {
                        CircularProgressIndicator()
                    } else {
                        IconButton(onClick = { viewModel.scanForDevices() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Scan")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (connectionState.status == com.gadies.suzuki.data.model.ConnectionStatus.CONNECTED) {
                Text(
                    text = "Connected to ${connectionState.device?.name}",
                    style = MaterialTheme.typography.titleLarge
                )
                Button(onClick = { viewModel.disconnectObd() }) {
                    Text("Disconnect")
                }
            } else {
                DeviceList(
                    devices = availableDevices,
                    onDeviceClick = { viewModel.connectToDevice(it) }
                )
            }
        }
    }
}

@Composable
fun DeviceList(
    devices: List<ObdDevice>,
    onDeviceClick: (ObdDevice) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(devices) { device ->
            DeviceListItem(
                device = device,
                onClick = { onDeviceClick(device) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListItem(
    device: ObdDevice,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Bluetooth, contentDescription = "Bluetooth Device")
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = device.address,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
