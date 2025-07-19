package com.gadies.suzuki.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gadies.suzuki.data.model.PidCategory
import com.gadies.suzuki.data.model.PidData
import com.gadies.suzuki.ui.theme.GadiesColors
import com.gadies.suzuki.ui.viewmodel.MainViewModel
import com.gadies.suzuki.ui.components.GaugeView
import com.gadies.suzuki.data.model.ConnectionStatus
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveDataScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val connectionState by viewModel.connectionState.collectAsState()
    val categorizedPids by viewModel.categorizedPids.collectAsState()
    val expandedCategories = remember { mutableStateMapOf<PidCategory, Boolean>() }
    val expandedSubCategories = remember { mutableStateMapOf<String, Boolean>() }
    
    if (connectionState.status != ConnectionStatus.CONNECTED) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.BluetoothDisabled,
                    contentDescription = "Not Connected",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "Not connected to OBD device",
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    onClick = { navController.navigate("connection") }
                ) {
                    Text("Connect to OBD")
                }
            }
        }
        return
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Live OBD2 Data",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Real-time monitoring of all 74 OBD-II PIDs",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        categorizedPids.forEach { (category, pids) ->
            if (pids.isNotEmpty()) {
                item {
                    PidCategoryCard(
                        category = category,
                        pids = pids,
                        isExpanded = expandedCategories[category] ?: false,
                        expandedSubCategories = expandedSubCategories,
                        onExpandToggle = { 
                            expandedCategories[category] = !(expandedCategories[category] ?: false)
                        },
                        onSubCategoryExpandToggle = { subCategoryKey ->
                            expandedSubCategories[subCategoryKey] = !(expandedSubCategories[subCategoryKey] ?: false)
                        },
                        onToggleAlert = { pid, enabled ->
                            viewModel.togglePidAlerts(pid, enabled)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PidCategoryCard(
    category: PidCategory,
    pids: List<PidData>,
    isExpanded: Boolean,
    expandedSubCategories: MutableMap<String, Boolean>,
    onExpandToggle: () -> Unit,
    onSubCategoryExpandToggle: (String) -> Unit,
    onToggleAlert: (String, Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Category Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandToggle() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CategoryIcon(category = category)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = category.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${pids.size} PIDs available",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }
            
            // Expanded Content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    // Group PIDs by sub-category
                    val pidsBySubCategory = pids.groupBy { it.subCategory }
                    
                    pidsBySubCategory.forEach { (subCategory, subCategoryPids) ->
                        if (subCategory != null) {
                            // Sub-category section
                            val subCategoryKey = "${category.name}_${subCategory.name}"
                            val isSubExpanded = expandedSubCategories[subCategoryKey] ?: false
                            
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    // Sub-category header
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { onSubCategoryExpandToggle(subCategoryKey) },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = subCategory.displayName,
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = "${subCategoryPids.size} PIDs",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Icon(
                                            imageVector = if (isSubExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = if (isSubExpanded) "Collapse" else "Expand",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    
                                    // Sub-category PIDs
                                    AnimatedVisibility(
                                        visible = isSubExpanded,
                                        enter = expandVertically(),
                                        exit = shrinkVertically()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(top = 8.dp)
                                        ) {
                                            subCategoryPids.forEach { pid ->
                                                PidDataRow(
                                                    pidData = pid,
                                                    onToggleAlert = onToggleAlert
                                                )
                                                if (pid != subCategoryPids.last()) {
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                }}
                                        }
                                    }
                                }
                            }
                        } else {
                            // PIDs without sub-category
                            if (subCategoryPids.isNotEmpty()) {
                                Text(
                                    text = "Other PIDs",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                subCategoryPids.forEach { pid ->
                                    PidDataRow(
                                        pidData = pid,
                                        onToggleAlert = onToggleAlert
                                    )
                                    if (pid != subCategoryPids.last()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PidDataRow(
    pidData: PidData,
    onToggleAlert: (String, Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = pidData.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    if (pidData.isSuzukiSpecific) {
                        Text(
                            text = "Suzuki Specific",
                            style = MaterialTheme.typography.labelSmall,
                            color = GadiesColors.OrangePrimary,
                            modifier = Modifier
                                .background(
                                    GadiesColors.OrangePrimary.copy(alpha = 0.1f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    // Value display
                    if (pidData.hasData) {
                        if (pidData.uiType == "gauge") {
                            GaugeView(
                                value = pidData.currentValue,
                                minValue = pidData.threshold?.min ?: 0.0,
                                maxValue = pidData.threshold?.max ?: 100.0,
                                unit = pidData.unit,
                                title = pidData.name,
                                size = if (pidData.category == PidCategory.ENGINE) 200 else 160,
                                modifier = Modifier.padding(8.dp)
                            )
                        } else {
                            Text(
                                text = "${pidData.currentValue} ${pidData.unit}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    pidData.threshold?.let { threshold ->
                                        (threshold.max != null && pidData.currentValue > threshold.max) ||
                                        (threshold.min != null && pidData.currentValue < threshold.min)
                                    } == true -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                        }
                        pidData.lastUpdated?.let { timestamp ->
                            Text(
                                text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(timestamp)),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Text(
                            text = "No data",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Alert toggle and threshold info
            if (pidData.threshold != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Threshold: ${pidData.threshold.min ?: "N/A"} - ${pidData.threshold.max ?: "N/A"} ${pidData.unit}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Alerts",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = pidData.alertsEnabled,
                            onCheckedChange = { enabled ->
                                onToggleAlert(pidData.pid, enabled)
                            },
                            modifier = Modifier
                                .size(24.dp)
                                .testTag("alert_toggle_${pidData.pid}")
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryIcon(category: PidCategory) {
    val icon = when (category) {
        PidCategory.ENGINE -> Icons.Default.Settings
        PidCategory.TRANSMISSION -> Icons.Default.Settings
        PidCategory.VEHICLE -> Icons.Default.DirectionsCar
        PidCategory.BATTERY -> Icons.Default.BatteryFull
        PidCategory.BRAKE -> Icons.Default.Warning
        PidCategory.HVAC -> Icons.Default.Thermostat
        PidCategory.ENVIRONMENT -> Icons.Default.Cloud
        PidCategory.DIAGNOSTIC -> Icons.Default.BugReport
        PidCategory.EMISSIONS -> Icons.Default.Air
        PidCategory.FUEL -> Icons.Default.LocalGasStation
        PidCategory.ELECTRICAL -> Icons.Default.ElectricBolt
        PidCategory.CHASSIS -> Icons.Default.DirectionsCar
        PidCategory.BODY -> Icons.Default.CardTravel
    }
    
    val iconColor = when (category) {
        PidCategory.ENGINE -> Color(0xFFFF5722)
        PidCategory.TRANSMISSION -> Color(0xFFFF9800)
        PidCategory.VEHICLE -> Color(0xFF2196F3)
        PidCategory.BATTERY -> Color(0xFF4CAF50)
        PidCategory.BRAKE -> Color(0xFFFF9800)
        PidCategory.HVAC -> Color(0xFF9C27B0)
        PidCategory.ENVIRONMENT -> Color(0xFF00BCD4)
        PidCategory.DIAGNOSTIC -> Color(0xFF795548)
        PidCategory.EMISSIONS -> Color(0xFF4CAF50)
        PidCategory.FUEL -> Color(0xFFFF5722)
        PidCategory.ELECTRICAL -> Color(0xFFFFC107)
        PidCategory.CHASSIS -> Color(0xFF607D8B)
        PidCategory.BODY -> Color(0xFF9E9E9E)
    }
    
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                iconColor.copy(alpha = 0.1f),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = category.displayName,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}
