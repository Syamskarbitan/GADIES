package com.gadies.suzuki.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.ui.theme.GADIESTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockCoolantPid = PidData(
        pid = "01_05",
        mode = "01",
        name = "Engine Coolant Temperature",
        unit = "°C",
        formula = "A-40",
        bytes = 1,
        category = "ENGINE",
        uiType = "gauge",
        currentValue = 85.0,
        hasData = true,
        threshold = Threshold(
            normal = DoubleRange(80.0, 100.0),
            caution = DoubleRange(100.0, 110.0),
            danger = DoubleRange(110.0, 215.0)
        )
    )

    private val mockBatteryPid = PidData(
        pid = "21_08",
        mode = "21",
        name = "Battery Voltage",
        unit = "V",
        formula = "A*0.1",
        bytes = 1,
        category = "BATTERY",
        uiType = "gauge",
        currentValue = 12.5,
        hasData = true,
        threshold = Threshold(
            normal = DoubleRange(12.0, 14.0),
            caution = DoubleRange(11.0, 12.0),
            danger = DoubleRange(0.0, 11.0)
        )
    )

    @Test
    fun dashboardScreen_displaysCorrectTitle() {
        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.CONNECTED),
                    coolantTemperaturePid = MutableStateFlow(mockCoolantPid),
                    batteryVoltagePid = MutableStateFlow(mockBatteryPid),
                    alerts = MutableStateFlow(emptyList()),
                    onNavigateToLiveData = {},
                    onNavigateToConnection = {},
                    onNavigateToSettings = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Dashboard")
            .assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_displaysConnectionStatus() {
        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.CONNECTED),
                    coolantTemperaturePid = MutableStateFlow(mockCoolantPid),
                    batteryVoltagePid = MutableStateFlow(mockBatteryPid),
                    alerts = MutableStateFlow(emptyList()),
                    onNavigateToLiveData = {},
                    onNavigateToConnection = {},
                    onNavigateToSettings = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Connected")
            .assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_displaysCoolantTemperature() {
        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.CONNECTED),
                    coolantTemperaturePid = MutableStateFlow(mockCoolantPid),
                    batteryVoltagePid = MutableStateFlow(mockBatteryPid),
                    alerts = MutableStateFlow(emptyList()),
                    onNavigateToLiveData = {},
                    onNavigateToConnection = {},
                    onNavigateToSettings = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Engine Coolant Temperature")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("85.0°C")
            .assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_displaysBatteryVoltage() {
        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.CONNECTED),
                    coolantTemperaturePid = MutableStateFlow(mockCoolantPid),
                    batteryVoltagePid = MutableStateFlow(mockBatteryPid),
                    alerts = MutableStateFlow(emptyList()),
                    onNavigateToLiveData = {},
                    onNavigateToConnection = {},
                    onNavigateToSettings = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Battery Voltage")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("12.5V")
            .assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_displaysQuickActions() {
        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.CONNECTED),
                    coolantTemperaturePid = MutableStateFlow(mockCoolantPid),
                    batteryVoltagePid = MutableStateFlow(mockBatteryPid),
                    alerts = MutableStateFlow(emptyList()),
                    onNavigateToLiveData = {},
                    onNavigateToConnection = {},
                    onNavigateToSettings = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Quick Actions")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("View All Data")
            .assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_handlesDisconnectedState() {
        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.DISCONNECTED),
                    coolantTemperaturePid = MutableStateFlow(null),
                    batteryVoltagePid = MutableStateFlow(null),
                    alerts = MutableStateFlow(emptyList()),
                    onNavigateToLiveData = {},
                    onNavigateToConnection = {},
                    onNavigateToSettings = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Disconnected")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("No data")
            .assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_displaysAlertsWhenPresent() {
        val testAlert = PidAlert(
            pidId = "01_05",
            pidName = "Engine Coolant Temperature",
            message = "High temperature detected",
            level = AlertLevel.DANGER,
            timestamp = System.currentTimeMillis()
        )

        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.CONNECTED),
                    coolantTemperaturePid = MutableStateFlow(mockCoolantPid),
                    batteryVoltagePid = MutableStateFlow(mockBatteryPid),
                    alerts = MutableStateFlow(listOf(testAlert)),
                    onNavigateToLiveData = {},
                    onNavigateToConnection = {},
                    onNavigateToSettings = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("1 Alerts Active")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("High temperature detected")
            .assertIsDisplayed()
    }

    @Test
    fun dashboardScreen_quickActionButtonsWork() {
        var liveDataClicked = false
        var connectionClicked = false
        var settingsClicked = false

        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    connectionState = MutableStateFlow(ConnectionState.CONNECTED),
                    coolantTemperaturePid = MutableStateFlow(mockCoolantPid),
                    batteryVoltagePid = MutableStateFlow(mockBatteryPid),
                    alerts = MutableStateFlow(emptyList()),
                    onNavigateToLiveData = { liveDataClicked = true },
                    onNavigateToConnection = { connectionClicked = true },
                    onNavigateToSettings = { settingsClicked = true }
                )
            }
        }

        // Test Live Data navigation
        composeTestRule
            .onNodeWithText("View All Data")
            .performClick()
        
        assert(liveDataClicked) { "Live Data navigation should be triggered" }
    }
}
