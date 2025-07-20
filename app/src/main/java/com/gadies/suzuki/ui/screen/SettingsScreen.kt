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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gadies.suzuki.ui.theme.*
import com.gadies.suzuki.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val settings by viewModel.settings.collectAsState()
    
    var showApiKeyDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showModelDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = GadiesColors.OrangePrimary
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // General Settings
            SettingsSection(title = "General") {
                SettingsItem(
                    icon = Icons.Default.Language,
                    title = "Language",
                    subtitle = if (settings.language == "id") "Bahasa Indonesia" else "English",
                    onClick = { showLanguageDialog = true }
                )
                
                SettingsItem(
                    icon = Icons.Default.Palette,
                    title = "Theme",
                    subtitle = settings.theme.replaceFirstChar { it.uppercase() },
                    onClick = { showThemeDialog = true }
                )
            }
            
            // OBD2 Settings
            SettingsSection(title = "OBD2 Connection") {
                SettingsItem(
                    icon = Icons.Default.Bluetooth,
                    title = "Auto Connect",
                    subtitle = "Automatically connect to last known device",
                    trailing = {
                        Switch(
                            checked = settings.autoConnect,
                            onCheckedChange = { viewModel.updateAutoConnect(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GadiesColors.OrangePrimary,
                                checkedTrackColor = GadiesColors.OrangePrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                )
                
                SettingsItem(
                    icon = Icons.Default.Timer,
                    title = "Polling Interval",
                    subtitle = "${settings.pollingInterval}ms between requests",
                    onClick = { /* TODO: Show interval picker */ }
                )
            }
            
            // Notifications
            SettingsSection(title = "Notifications") {
                SettingsItem(
                    icon = Icons.Default.Notifications,
                    title = "Enable Notifications",
                    subtitle = "Show alerts for threshold violations",
                    trailing = {
                        Switch(
                            checked = settings.notificationsEnabled,
                            onCheckedChange = { viewModel.updateNotifications(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GadiesColors.OrangePrimary,
                                checkedTrackColor = GadiesColors.OrangePrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                )
                
                SettingsItem(
                    icon = Icons.Default.Vibration,
                    title = "Vibration",
                    subtitle = "Vibrate on critical alerts",
                    trailing = {
                        Switch(
                            checked = settings.vibrationEnabled,
                            onCheckedChange = { viewModel.updateVibration(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GadiesColors.OrangePrimary,
                                checkedTrackColor = GadiesColors.OrangePrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                )
            }
            
            // AI Settings
            SettingsSection(title = "AI Assistant") {
                SettingsItem(
                    icon = Icons.Default.Psychology,
                    title = "Enable AI Features",
                    subtitle = "AI analysis and chat functionality",
                    trailing = {
                        Switch(
                            checked = settings.aiEnabled,
                            onCheckedChange = { viewModel.updateAiEnabled(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GadiesColors.OrangePrimary,
                                checkedTrackColor = GadiesColors.OrangePrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                )
                
                if (settings.aiEnabled) {
                    SettingsItem(
                        icon = Icons.Default.SmartToy,
                        title = "AI Model",
                        subtitle = settings.aiModel,
                        onClick = { showModelDialog = true }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Key,
                        title = "OpenRouter API Key",
                        subtitle = if (settings.openRouterApiKey.isNotBlank()) "Configured" else "Not set",
                        onClick = { showApiKeyDialog = true }
                    )
                }
            }
            
            // Data Management
            SettingsSection(title = "Data Management") {
                SettingsItem(
                    icon = Icons.Default.History,
                    title = "Clear Chat History",
                    subtitle = "Remove all AI chat messages",
                    onClick = { viewModel.clearChatHistory() }
                )
                
                SettingsItem(
                    icon = Icons.Default.Refresh,
                    title = "Reset Thresholds",
                    subtitle = "Restore default alert thresholds",
                    onClick = { viewModel.resetThresholds() }
                )
            }
            
            // About
            SettingsSection(title = "About") {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "App Version",
                    subtitle = "1.0.0 (Beta)",
                    onClick = { }
                )
                
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Developer",
                    subtitle = "Samsul Arifin - Gadies Jatim",
                    onClick = { }
                )
                
                SettingsItem(
                    icon = Icons.Default.Favorite,
                    title = "Support Development",
                    subtitle = "BCA: 0181539509",
                    onClick = { }
                )
            }
        }
    }
    
    // Dialogs
    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = settings.language,
            onLanguageSelected = { language ->
                viewModel.updateLanguage(language)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
    
    if (showModelDialog) {
        ModelSelectionDialog(
            currentModel = settings.aiModel,
            onModelSelected = { model ->
                viewModel.updateAiModel(model)
                showModelDialog = false
            },
            onDismiss = { showModelDialog = false }
        )
    }
    
    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = settings.theme,
            onThemeSelected = { theme ->
                viewModel.updateTheme(theme)
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }
    
    if (showApiKeyDialog) {
        ApiKeyDialog(
            currentApiKey = settings.openRouterApiKey,
            onApiKeySet = { apiKey ->
                viewModel.updateOpenRouterApiKey(apiKey)
                showApiKeyDialog = false
            },
            onDismiss = { showApiKeyDialog = false }
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = GadiesColors.OrangePrimary
            ),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick ?: { },
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = GadiesColors.OrangePrimary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (trailing != null) {
                trailing()
            } else if (onClick != null) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf(
        "en" to "English",
        "id" to "Bahasa Indonesia"
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Language") },
        text = {
            Column {
                languages.forEach { (code, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentLanguage == code,
                            onClick = { onLanguageSelected(code) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = GadiesColors.OrangePrimary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = name)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ModelSelectionDialog(
    currentModel: String,
    onModelSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val models = listOf(
        "deepseek/deepseek-r1",
        "openai/gpt-4o",
        "anthropic/claude-3.5-sonnet",
        "google/gemini-pro"
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select AI Model") },
        text = {
            Column {
                models.forEach { model ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentModel == model,
                            onClick = { onModelSelected(model) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = GadiesColors.OrangePrimary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = model)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ThemeSelectionDialog(
    currentTheme: String,
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val themes = listOf(
        "system" to "System Default",
        "light" to "Light",
        "dark" to "Dark"
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Theme") },
        text = {
            Column {
                themes.forEach { (code, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentTheme == code,
                            onClick = { onThemeSelected(code) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = GadiesColors.OrangePrimary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = name)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiKeyDialog(
    currentApiKey: String,
    onApiKeySet: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var apiKey by remember { mutableStateOf(currentApiKey) }
    var showApiKey by remember { mutableStateOf(false) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("OpenRouter API Key") },
        text = {
            Column {
                Text(
                    text = "Enter your OpenRouter API key to enable AI features. Get your key from openrouter.ai",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("API Key") },
                    visualTransformation = if (showApiKey) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showApiKey = !showApiKey }) {
                            Icon(
                                imageVector = if (showApiKey) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showApiKey) "Hide" else "Show"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onApiKeySet(apiKey) },
                enabled = apiKey.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
