package com.gadies.suzuki.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gadies.suzuki.data.model.AlertLevel
import com.gadies.suzuki.data.model.ConnectionStatus
import com.gadies.suzuki.data.model.PidData
import com.gadies.suzuki.ui.theme.*
import com.gadies.suzuki.ui.viewmodel.MainViewModel
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val connectionState by viewModel.connectionState.collectAsState()
    val dashboardPids by viewModel.dashboardPids.collectAsState()
    val alerts by viewModel.alerts.collectAsState()
    val coolantTemp by viewModel.getCoolantTemperature().collectAsState()
    val batteryVoltage by viewModel.getBatteryVoltage().collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GADIES",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Suzuki Ertiga Diesel",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )
                }
            },
            actions = {
                // Connection Status Indicator
                ConnectionStatusIndicator(
                    status = connectionState.status,
                    onClick = { navController.navigate("connection") }
                )
                
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = GadiesYellow
            )
        )
        
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Connection Status Banner
            if (connectionState.status != ConnectionStatus.CONNECTED) {
                OfflineBanner()
            }
            
            // Alert Banner
            if (alerts.isNotEmpty()) {
                AlertBanner(
                    alertCount = alerts.size,
                    latestAlert = alerts.first(),
                    onViewAlerts = { /* TODO: Navigate to alerts */ }
                )
            }
            
            // Main Gauges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Coolant Temperature Gauge
                GaugeCard(
                    modifier = Modifier.weight(1f),
                    title = "Coolant Temperature",
                    pidData = coolantTemp,
                    minValue = 0.0,
                    maxValue = 120.0,
                    normalRange = 0.0..90.0,
                    cautionRange = 91.0..100.0,
                    dangerRange = 101.0..120.0
                )
                
                // Battery Voltage Gauge
                GaugeCard(
                    modifier = Modifier.weight(1f),
                    title = "Battery Voltage",
                    pidData = batteryVoltage,
                    minValue = 10.0,
                    maxValue = 15.0,
                    normalRange = 12.6..15.0,
                    cautionRange = 11.8..12.6,
                    dangerRange = 10.0..11.8
                )
            }
            
            // Quick Actions
            QuickActionsSection(navController = navController)
            
            // Recent Data Summary
            RecentDataSummary(
                dashboardPids = dashboardPids,
                onViewAll = { navController.navigate("live_data") }
            )
            
            // Author & Donation Section
            AuthorSection()
        }
    }
}

@Composable
fun ConnectionStatusIndicator(
    status: ConnectionStatus,
    onClick: () -> Unit
) {
    val (color, icon, text) = when (status) {
        ConnectionStatus.CONNECTED -> Triple(GadiesGreen, Icons.Default.Bluetooth, "Online")
        ConnectionStatus.CONNECTING -> Triple(GadiesOrange, Icons.Default.BluetoothSearching, "Connecting")
        ConnectionStatus.DISCONNECTED -> Triple(GadiesGray, Icons.Default.BluetoothDisabled, "Offline")
        ConnectionStatus.ERROR -> Triple(GadiesRed, Icons.Default.Error, "Error")
    }
    
    TextButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = color,
            fontSize = 12.sp
        )
    }
}

@Composable
fun OfflineBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = GadiesOrange.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                tint = GadiesOrange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Offline Mode",
                    style = MaterialTheme.typography.titleSmall,
                    color = GadiesOrange
                )
                Text(
                    text = "AI features disabled. Connect to OBD2 for real-time data.",
                    style = MaterialTheme.typography.bodySmall,
                    color = GadiesOrange
                )
            }
        }
    }
}

@Composable
fun AlertBanner(
    alertCount: Int,
    latestAlert: com.gadies.suzuki.data.model.PidAlert,
    onViewAlerts: () -> Unit
) {
    val alertColor = when (latestAlert.level) {
        AlertLevel.DANGER -> GadiesRed
        AlertLevel.CAUTION -> GadiesOrange
        AlertLevel.NORMAL -> GadiesGreen
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = alertColor.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Alert",
                tint = alertColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$alertCount Alert${if (alertCount > 1) "s" else ""}",
                    style = MaterialTheme.typography.titleSmall,
                    color = alertColor
                )
                Text(
                    text = latestAlert.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = alertColor,
                    maxLines = 2
                )
            }
            TextButton(onClick = onViewAlerts) {
                Text("View", color = alertColor)
            }
        }
    }
}

@Composable
fun GaugeCard(
    modifier: Modifier = Modifier,
    title: String,
    pidData: PidData?,
    minValue: Double,
    maxValue: Double,
    normalRange: ClosedFloatingPointRange<Double>,
    cautionRange: ClosedFloatingPointRange<Double>,
    dangerRange: ClosedFloatingPointRange<Double>
) {
    val currentValue = pidData?.currentValue ?: 0.0
    val unit = pidData?.unit ?: ""
    
    val status = when {
        currentValue in normalRange -> "Normal" to GadiesGreen
        currentValue in cautionRange -> "Caution" to GadiesOrange
        currentValue in dangerRange -> if (title.contains("Temperature")) "Overheat" to GadiesRed else "Low Battery" to GadiesRed
        else -> "Unknown" to GadiesGray
    }
    
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Gauge
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularGauge(
                    value = currentValue,
                    minValue = minValue,
                    maxValue = maxValue,
                    normalRange = normalRange,
                    cautionRange = cautionRange,
                    dangerRange = dangerRange
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Value
            Text(
                text = "${String.format("%.1f", currentValue)} $unit",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            // Status
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(status.second)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = status.first,
                    style = MaterialTheme.typography.bodySmall,
                    color = status.second
                )
            }
        }
    }
}

@Composable
fun CircularGauge(
    value: Double,
    minValue: Double,
    maxValue: Double,
    normalRange: ClosedFloatingPointRange<Double>,
    cautionRange: ClosedFloatingPointRange<Double>,
    dangerRange: ClosedFloatingPointRange<Double>
) {
    Canvas(modifier = Modifier.size(100.dp)) {
        val strokeWidth = 12.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2
        val center = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)
        
        // Background arc
        drawArc(
            color = Color.LightGray,
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
            topLeft = androidx.compose.ui.geometry.Offset(
                center.x - radius,
                center.y - radius
            )
        )
        
        // Value arc
        val normalizedValue = ((value - minValue) / (maxValue - minValue)).coerceIn(0.0, 1.0)
        val sweepAngle = (normalizedValue * 270).toFloat()
        
        val arcColor = when {
            value in normalRange -> GadiesGreen
            value in cautionRange -> GadiesOrange
            value in dangerRange -> GadiesRed
            else -> Color.Gray
        }
        
        drawArc(
            color = arcColor,
            startAngle = 135f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
            topLeft = androidx.compose.ui.geometry.Offset(
                center.x - radius,
                center.y - radius
            )
        )
        
        // Needle
        val needleAngle = 135 + sweepAngle
        val needleLength = radius * 0.8f
        rotate(needleAngle, center) {
            drawLine(
                color = Color.Black,
                start = center,
                end = androidx.compose.ui.geometry.Offset(
                    center.x,
                    center.y - needleLength
                ),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        
        // Center dot
        drawCircle(
            color = Color.Black,
            radius = 6.dp.toPx(),
            center = center
        )
    }
}

@Composable
fun QuickActionsSection(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Analytics,
                    title = "Live Data",
                    subtitle = "View all PIDs",
                    onClick = { navController.navigate("live_data") }
                )
                
                QuickActionButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Psychology,
                    title = "AI Analysis",
                    subtitle = "Analyze car health",
                    onClick = { navController.navigate("ai_analysis") }
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = GadiesYellow.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = GadiesDarkYellow
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun RecentDataSummary(
    dashboardPids: List<PidData>,
    onViewAll: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Data",
                    style = MaterialTheme.typography.titleMedium
                )
                TextButton(onClick = onViewAll) {
                    Text("View All")
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            dashboardPids.take(3).forEach { pid ->
                DataRow(
                    name = pid.name,
                    value = "${String.format("%.1f", pid.currentValue)} ${pid.unit}",
                    timestamp = pid.lastUpdated
                )
            }
        }
    }
}

@Composable
fun DataRow(
    name: String,
    value: String,
    timestamp: Long
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
fun AuthorSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = GadiesYellow.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "☕ Enjoy using GADIES?",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Support the developer with a coffee",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Donate via BCA Bank: 0181539509 (Samsul Arifin)",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                color = GadiesDarkYellow
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Member of Gadies-Jatim",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}
