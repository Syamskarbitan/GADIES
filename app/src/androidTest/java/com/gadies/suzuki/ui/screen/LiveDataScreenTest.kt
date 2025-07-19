package com.gadies.suzuki.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.ui.viewmodel.MainViewModel
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveDataScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavController
    private val mockViewModel = mockk<MainViewModel>(relaxed = true)
    private val categorizedPidsFlow = MutableStateFlow<Map<PidCategory, List<PidData>>>(emptyMap())

    @Before
    fun setup() {
        navController = mockk(relaxed = true)
        every { mockViewModel.categorizedPids } returns categorizedPidsFlow
    }

    @Test
    fun testCategoryExpansion() {
        val enginePid = PidData(
            mode = "01",
            pid = "05",
            name = "Coolant Temperature",
            bytes = 1,
            unit = "°C",
            formula = "A-40",
            category = PidCategory.ENGINE,  // Using enum instead of String
            uiType = "gauge",
            currentValue = 90.0,
            hasData = true,
            lastUpdated = System.currentTimeMillis(),
            alertsEnabled = false,
            isSuzukiSpecific = false
        )

        categorizedPidsFlow.value = mapOf(
            PidCategory.ENGINE to listOf(enginePid)  // Using enum instead of String
        )

        composeTestRule.setContent {
            LiveDataScreen(
                navController = navController,
                viewModel = mockViewModel
            )
        }

        // Verify category header
        composeTestRule.onNodeWithText(PidCategory.ENGINE.displayName)
            .assertExists()
            .assertIsDisplayed()

        // Verify PID count
        composeTestRule.onNodeWithText("1 PIDs available")
            .assertExists()
            .assertIsDisplayed()

        // Click to expand category
        composeTestRule.onNodeWithText(PidCategory.ENGINE.displayName)
            .performClick()

        // Verify PID name becomes visible
        composeTestRule.onNodeWithText("Coolant Temperature")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testPidAlertToggle() {
        val enginePid = PidData(
            mode = "01",
            pid = "05",
            name = "Coolant Temperature",
            bytes = 1,
            unit = "°C",
            formula = "A-40",
            category = PidCategory.ENGINE,
            uiType = "gauge",
            currentValue = 90.0,
            hasData = true,
            lastUpdated = System.currentTimeMillis(),
            alertsEnabled = false,
            isSuzukiSpecific = false,
            threshold = Threshold(min = 0.0, max = 100.0)
        )

        categorizedPidsFlow.value = mapOf(
            PidCategory.ENGINE to listOf(enginePid)
        )

        composeTestRule.setContent {
            LiveDataScreen(
                navController = navController,
                viewModel = mockViewModel
            )
        }

        // Expand the category
        composeTestRule.onNodeWithText(PidCategory.ENGINE.displayName)
            .performClick()

        // Toggle the alert
        composeTestRule.onNode(hasTestTag("alert_toggle_05"))
            .performClick()

        // Verify the toggle was called
        verify { mockViewModel.togglePidAlerts("05", true) }
    }
}
