package com.gadies.suzuki.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.ui.theme.*
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
    
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Bluetooth", "WiFi")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("OBD2 Connection") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(
                    onClick = { 
                        if (connectionState.status == ConnectionStatus.CONNECTED) {
                            viewModel.disconnectObd()
                        } else {
                            viewModel.scanForDevices()
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (connectionState.status == ConnectionStatus.CONNECTED) 
                            Icons.Default.BluetoothDisabled else Icons.Default.Refresh,
                        contentDescription = if (connectionState.status == ConnectionStatus.CONNECTED) 
                            "Disconnect" else "Scan"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = GadiesYellow
            )
        )
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Connection Status
            ConnectionStatusSection(
                connectionState = connectionState,
                onDisconnect = { viewModel.disconnectObd() }
            )
            
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = GadiesYellow
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            
            // Tab Content
            when (selectedTab) {
                0 -> BluetoothTab(
                    availableDevices = availableDevices,
                    isScanning = isScanning,
                    connectionState = connectionState,
                    onScanDevices = { viewModel.scanForDevices() },
                    onConnectDevice = { device -> viewModel.connectToDevice(device) },
                    onStopScan = { viewModel.stopScan() }
                )
                1 -> WiFiTab(
                    connectionState = connectionState,
                    onConnectWiFi = { ip, port -> viewModel.connectToWiFi(ip, port) }
                )
            }
        }
    }
}

@Composable
fun ConnectionStatusSection(
    connectionState: ConnectionState,
    onDisconnect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (connectionState.status) {
                ConnectionStatus.CONNECTED -> GadiesGreen.copy(alpha = 0.1f)
                ConnectionStatus.CONNECTING -> GadiesOrange.copy(alpha = 0.1f)
                ConnectionStatus.DISCONNECTED -> GadiesRed.copy(alpha = 0.1f)
                ConnectionStatus.ERROR -> GadiesRed.copy(alpha = 0.1f)
            }
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            when (connectionState.status) {
                                ConnectionStatus.CONNECTED -> GadiesGreen
                                ConnectionStatus.CONNECTING -> GadiesOrange
                                ConnectionStatus.DISCONNECTED -> GadiesGray
                                ConnectionStatus.ERROR -> GadiesRed
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (connectionState.status) {
                            ConnectionStatus.CONNECTED -> Icons.Default.Bluetooth
                            ConnectionStatus.CONNECTING -> Icons.Default.BluetoothSearching
                            ConnectionStatus.DISCONNECTED -> Icons.Default.BluetoothDisabled
                            ConnectionStatus.ERROR -> Icons.Default.Error
                        },
                        contentDescription = "Connection Status",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Status Info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = when (connectionState.status) {
                            ConnectionStatus.CONNECTED -> "Connected"
                            ConnectionStatus.CONNECTING -> "Connecting..."
                            ConnectionStatus.DISCONNECTED -> "Disconnected"
                            ConnectionStatus.ERROR -> "Connection Error"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = when (connectionState.status) {
                            ConnectionStatus.CONNECTED -> GadiesGreen
                            ConnectionStatus.CONNECTING -> GadiesOrange
                            ConnectionStatus.DISCONNECTED -> GadiesGray
                            ConnectionStatus.ERROR -> GadiesRed
                        }
                    )
                    
                    connectionState.device?.let { device ->
                        Text(
                            text = device.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${device.type.name} - ${device.address}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    
                    connectionState.errorMessage?.let { error ->
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodySmall,
                            color = GadiesRed
                        )
                    }
                }
                
                // Disconnect Button
                if (connectionState.status == ConnectionStatus.CONNECTED) {
                    IconButton(onClick = onDisconnect) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Disconnect",
                            tint = GadiesRed
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BluetoothTab(
    availableDevices: List<ObdDevice>,
    isScanning: Boolean,
    connectionState: ConnectionState,
    onScanDevices: () -> Unit,
    onConnectDevice: (ObdDevice) -> Unit,
    onStopScan: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Scan Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Available Devices",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            if (isScanning) {
                TextButton(onClick = onStopScan) {
                    Text("Stop Scan")
                }
            } else {
                Button(
                    onClick = onScanDevices,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GadiesYellow,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Scan",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Scan")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Device List
        if (isScanning) {
            ScanningIndicator()
        } else if (availableDevices.isEmpty()) {
            EmptyDeviceList(onScan = onScanDevices)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableDevices) { device ->
                    DeviceItem(
                        device = device,
                        isConnected = connectionState.device?.address == device.address,
                        isConnecting = connectionState.status == ConnectionStatus.CONNECTING && 
                                      connectionState.device?.address == device.address,
                        onConnect = { onConnectDevice(device) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WiFiTab(
    connectionState: ConnectionState,
    onConnectWiFi: (String, Int) -> Unit
) {
    var ipAddress by remember { mutableStateOf("192.168.0.10") }
    var port by remember { mutableStateOf("35000") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "WiFi ELM327 Connection",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = ipAddress,
                    onValueChange = { ipAddress = it },
                    label = { Text("IP Address") },
                    placeholder = { Text("192.168.0.10") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = port,
                    onValueChange = { port = it },
                    label = { Text("Port") },
                    placeholder = { Text("35000") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                Button(
                    onClick = { 
                        val portInt = port.toIntOrNull() ?: 35000
                        onConnectWiFi(ipAddress, portInt)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = ipAddress.isNotBlank() && connectionState.status != ConnectionStatus.CONNECTING,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GadiesYellow,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (connectionState.status == ConnectionStatus.CONNECTING) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Connecting...")
                    } else {
                        Icon(
                            imageVector = Icons.Default.Wifi,
                            contentDescription = "Connect",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Connect")
                    }
                }
            }
        }
        
        // WiFi Setup Instructions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Setup Instructions",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "1. Connect your phone to the ELM327 WiFi network\n" +
                          "2. The default IP is usually 192.168.0.10\n" +
                          "3. Default port is 35000\n" +
                          "4. Check your ELM327 manual for specific settings",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DeviceItem(
    device: ObdDevice,
    isConnected: Boolean,
    isConnecting: Boolean,
    onConnect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isConnected) GadiesGreen.copy(alpha = 0.1f) 
                           else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Device Icon
            Icon(
                imageVector = when (device.type) {
                    ConnectionType.BLUETOOTH -> Icons.Default.Bluetooth
                    ConnectionType.WIFI -> Icons.Default.Wifi
                    ConnectionType.NONE -> Icons.Default.Close
                },
                contentDescription = "Device Type",
                tint = if (isConnected) GadiesGreen else GadiesYellow,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Device Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = device.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                if (device.rssi != null) {
                    Text(
                        text = "Signal: ${device.rssi} dBm",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            
            // Connect Button
            when {
                isConnected -> {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Connected",
                        tint = GadiesGreen
                    )
                }
                isConnecting -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = GadiesYellow
                    )
                }
                else -> {
                    TextButton(onClick = onConnect) {
                        Text("Connect")
                    }
                }
            }
        }
    }
}

@Composable
fun ScanningIndicator() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = GadiesYellow,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Scanning for devices...",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmptyDeviceList(onScan: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.BluetoothDisabled,
            contentDescription = "No Devices",
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No devices found",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Text(
            text = "Make sure your ELM327 device is in pairing mode",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onScan,
            colors = ButtonDefaults.buttonColors(
                containerColor = GadiesYellow,
                contentColor = Color.Black
            )
        ) {
            Text("Scan Again")
        }
    }
}
