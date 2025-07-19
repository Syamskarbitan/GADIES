package com.gadies.suzuki.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.data.model.ConnectionStatus
import com.gadies.suzuki.ui.theme.GADIESTheme
import com.gadies.suzuki.ui.viewmodel.MainViewModel
import io.mockk.mockk
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    
    private lateinit var mockViewModel: MainViewModel

    @org.junit.Before
    fun setup() {
        mockViewModel = mockk(relaxed = true)
    }

    private val mockCoolantPid = PidData(
        pid = "01_05",
        mode = "01",
        name = "Engine Coolant Temperature",
        unit = "°C",
        formula = "A-40",
        bytes = 1,
        category = PidCategory.ENGINE,
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
        pid = "01_42",
        mode = "01",
        name = "Control Module Voltage",
        unit = "V",
        formula = "((A*256)+B)/1000",
        bytes = 2,
        category = PidCategory.ENGINE,
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
                    navController = rememberNavController(),
                    viewModel = mockViewModel
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
                    navController = rememberNavController(),
                    viewModel = mockViewModel
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
                    navController = rememberNavController(),
                    viewModel = mockViewModel
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
                    navController = rememberNavController(),
                    viewModel = mockViewModel
                )
            }
        }

        composeTestRule
            .onNodeWithText("Control Module Voltage")
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
                    navController = rememberNavController(),
                    viewModel = mockViewModel
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
                    navController = rememberNavController(),
                    viewModel = mockViewModel
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
        val mockPidData = PidData(
            pid = "01_05",
            mode = "01",
            name = "Engine Coolant Temperature",
            unit = "°C",
            formula = "A-40",
            bytes = 1,
            category = PidCategory.ENGINE,
            uiType = "gauge",
            currentValue = 95.0
        )
        
        val testAlert = PidAlert(
            pidData = mockPidData,
            message = "High temperature detected",
            level = AlertLevel.DANGER,
            timestamp = System.currentTimeMillis()
        )

        composeTestRule.setContent {
            GADIESTheme {
                DashboardScreen(
                    navController = rememberNavController(),
                    viewModel = mockViewModel
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
                    navController = rememberNavController(),
                    viewModel = mockViewModel
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
