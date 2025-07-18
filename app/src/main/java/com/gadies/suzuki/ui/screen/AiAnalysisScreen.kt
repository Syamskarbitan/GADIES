package com.gadies.suzuki.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gadies.suzuki.data.model.AiAnalysisResponse
import com.gadies.suzuki.ui.theme.*
import com.gadies.suzuki.ui.viewmodel.AiAnalysisState
import com.gadies.suzuki.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAnalysisScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val aiAnalysisState by viewModel.aiAnalysisState.collectAsState()
    val connectionState by viewModel.connectionState.collectAsState()
    val settings by viewModel.settings.collectAsState()
    
    var odometerKm by remember { mutableStateOf("") }
    var lastServiceKm by remember { mutableStateOf("") }
    var showAnalysisDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("AI Car Analysis") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = GadiesYellow
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // AI Status Card
            AiStatusCard(
                isConnected = connectionState.status == com.gadies.suzuki.data.model.ConnectionStatus.CONNECTED,
                aiEnabled = settings.aiEnabled
            )
            
            // Analysis Form
            if (settings.aiEnabled) {
                AnalysisFormCard(
                    odometerKm = odometerKm,
                    onOdometerChange = { odometerKm = it },
                    lastServiceKm = lastServiceKm,
                    onLastServiceChange = { lastServiceKm = it },
                    onAnalyze = {
                        val userInputs = mapOf(
                            "odometer_km" to odometerKm,
                            "service_last_km" to lastServiceKm
                        )
                        viewModel.startAiAnalysis(userInputs)
                        showAnalysisDialog = true
                    },
                    isLoading = aiAnalysisState is AiAnalysisState.Loading,
                    isConnected = connectionState.status == com.gadies.suzuki.data.model.ConnectionStatus.CONNECTED
                )
            }
            
            // Previous Analysis Results
            when (aiAnalysisState) {
                is AiAnalysisState.Success -> {
                    AnalysisResultCard(
                        result = aiAnalysisState.response,
                        onStartChat = { navController.navigate("ai_chat") }
                    )
                }
                is AiAnalysisState.Error -> {
                    ErrorCard(
                        message = aiAnalysisState.message,
                        onRetry = { viewModel.resetAiAnalysis() }
                    )
                }
                else -> {}
            }
            
            // AI Info Card
            AiInfoCard()
        }
    }
    
    // Analysis Loading Dialog
    if (showAnalysisDialog && aiAnalysisState is AiAnalysisState.Loading) {
        AnalysisLoadingDialog(
            onDismiss = { 
                showAnalysisDialog = false
                viewModel.resetAiAnalysis()
            }
        )
    } else if (showAnalysisDialog && aiAnalysisState !is AiAnalysisState.Loading) {
        showAnalysisDialog = false
    }
}

@Composable
fun AiStatusCard(
    isConnected: Boolean,
    aiEnabled: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                !aiEnabled -> GadiesGray.copy(alpha = 0.1f)
                !isConnected -> GadiesOrange.copy(alpha = 0.1f)
                else -> GadiesGreen.copy(alpha = 0.1f)
            }
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when {
                    !aiEnabled -> Icons.Default.SmartToy
                    !isConnected -> Icons.Default.Warning
                    else -> Icons.Default.Psychology
                },
                contentDescription = "AI Status",
                tint = when {
                    !aiEnabled -> GadiesGray
                    !isConnected -> GadiesOrange
                    else -> GadiesGreen
                },
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = when {
                        !aiEnabled -> "AI Disabled"
                        !isConnected -> "Limited AI Mode"
                        else -> "AI Ready"
                    },
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = when {
                        !aiEnabled -> GadiesGray
                        !isConnected -> GadiesOrange
                        else -> GadiesGreen
                    }
                )
                Text(
                    text = when {
                        !aiEnabled -> "Enable AI in settings to use this feature"
                        !isConnected -> "Connect to OBD2 for complete analysis"
                        else -> "Ready to analyze your vehicle health"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun AnalysisFormCard(
    odometerKm: String,
    onOdometerChange: (String) -> Unit,
    lastServiceKm: String,
    onLastServiceChange: (String) -> Unit,
    onAnalyze: () -> Unit,
    isLoading: Boolean,
    isConnected: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Vehicle Information",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            // Odometer Input
            OutlinedTextField(
                value = odometerKm,
                onValueChange = onOdometerChange,
                label = { Text("Current Odometer (KM) *") },
                placeholder = { Text("e.g., 85000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Last Service Input
            OutlinedTextField(
                value = lastServiceKm,
                onValueChange = onLastServiceChange,
                label = { Text("Last Service KM (Optional)") },
                placeholder = { Text("e.g., 80000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Analyze Button
            Button(
                onClick = onAnalyze,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && odometerKm.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GadiesYellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analyzing...")
                } else {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "Analyze",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analyze My Car")
                }
            }
            
            if (!isConnected) {
                Text(
                    text = "⚠️ Analysis will be limited without OBD2 connection",
                    style = MaterialTheme.typography.bodySmall,
                    color = GadiesOrange,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AnalysisResultCard(
    result: AiAnalysisResponse,
    onStartChat: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Car Health Report",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                HealthScoreIndicator(score = result.healthScore)
            }
            
            // Summary
            Text(
                text = result.summary,
                style = MaterialTheme.typography.bodyMedium
            )
            
            // Issues Found
            if (result.issuesFound.isNotEmpty()) {
                Column {
                    Text(
                        text = "Issues Found:",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = GadiesRed
                        )
                    )
                    result.issuesFound.forEach { issue ->
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text("• ", color = GadiesRed)
                            Text(
                                text = issue,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            
            // Recommendations
            if (result.recommendations.isNotEmpty()) {
                Column {
                    Text(
                        text = "Recommendations:",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = GadiesGreen
                        )
                    )
                    result.recommendations.forEach { recommendation ->
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text("• ", color = GadiesGreen)
                            Text(
                                text = recommendation,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            
            // Next Service
            result.nextServiceKm?.let { nextService ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = GadiesYellow.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Next Service",
                            tint = GadiesDarkYellow
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Next service recommended at: ${nextService} KM",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
            
            // Chat Button
            Button(
                onClick = onStartChat,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GadiesYellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Chat",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ask AI Mechanic")
            }
        }
    }
}

@Composable
fun HealthScoreIndicator(score: Int) {
    val color = when {
        score >= 80 -> GadiesGreen
        score >= 60 -> GadiesOrange
        else -> GadiesRed
    }
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${score}%",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = when {
                    score >= 80 -> Icons.Default.CheckCircle
                    score >= 60 -> Icons.Default.Warning
                    else -> Icons.Default.Error
                },
                contentDescription = "Health Score",
                tint = color,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ErrorCard(
    message: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = GadiesRed.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                tint = GadiesRed,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Analysis Failed",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GadiesRed
                )
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = onRetry) {
                Text("Try Again", color = GadiesRed)
            }
        }
    }
}

@Composable
fun AiInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "About AI Analysis",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "• Powered by Deepseek R1 AI model\n" +
                      "• Analyzes real-time OBD2 data\n" +
                      "• Provides personalized recommendations\n" +
                      "• Available in Indonesian and English",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AnalysisLoadingDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Analyzing Your Vehicle")
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = GadiesYellow,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "AI is analyzing your vehicle data...\nThis may take a few moments.",
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
