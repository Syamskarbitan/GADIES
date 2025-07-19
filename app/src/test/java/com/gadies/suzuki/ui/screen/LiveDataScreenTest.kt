package com.gadies.suzuki.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.gadies.suzuki.data.model.PidCategory
import com.gadies.suzuki.data.model.PidData
import com.gadies.suzuki.ui.viewmodel.MainViewModel
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(androidx.compose.ui.test.ExperimentalTestApi::class)
class LiveDataScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel = mockk<MainViewModel>(relaxed = true)
    private val categorizedPidsFlow = MutableStateFlow<Map<PidCategory, List<PidData>>>(emptyMap())

    @Before
    fun setup() {
        every { mockViewModel.categorizedPids } returns categorizedPidsFlow

        // Mock other necessary ViewModel properties
        every { mockViewModel.togglePidAlerts(any(), any()) } just Runs
    }

    @Test
    fun testPidDataRowDisplay() {
        val testPid = PidData(
            mode = "01",
            pid = "0C",
            name = "Engine RPM",
            bytes = 2,
            unit = "RPM",
            formula = "((A * 256) + B) / 4",
            category = PidCategory.ENGINE,
            uiType = "gauge",
            currentValue = 2500.0,
            hasData = true,
            lastUpdated = System.currentTimeMillis(),
            alertsEnabled = true,
            isSuzukiSpecific = true
        )

        composeTestRule.setContent {
            PidDataRowTest(testPid)
        }

        // Verify PID name is displayed
        composeTestRule.onNodeWithText("Engine RPM")
            .assertExists()
            .assertIsDisplayed()

        // Verify PID and Mode info
        composeTestRule.onNodeWithText("PID: 0C | Mode: 01")
            .assertExists()
            .assertIsDisplayed()

        // Verify current value with unit
        composeTestRule.onNodeWithText("2500.0 RPM")
            .assertExists()
            .assertIsDisplayed()

        // Verify Suzuki Specific tag
        composeTestRule.onNodeWithText("Suzuki Specific")
            .assertExists()
            .assertIsDisplayed()

        // Verify threshold info
        composeTestRule.onNodeWithText("Threshold: 0.0 - 8000.0 RPM")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testCategoryExpansion() {
        val enginePids = listOf(
            PidData(
                mode = "01",
                pid = "0C",
                name = "Engine RPM",
                bytes = 2,
                unit = "RPM",
                formula = "((A * 256) + B) / 4",
                category = PidCategory.ENGINE,
                subCategory = PidSubCategory.ENGINE_LOAD,
                threshold = null,
                currentValue = null,
                hasData = false,
                alertsEnabled = false,
                isSuzukiSpecific = false
            )
        )

        categorizedPidsFlow.value = mapOf(
            PidCategory.ENGINE to enginePids
        )

        composeTestRule.setContent {
            LiveDataScreen(
                navController = mockk(relaxed = true),
                viewModel = mockViewModel
            )
        }

        // Verify category title is displayed
        composeTestRule.onNodeWithText("Engine")
            .assertExists()
            .assertIsDisplayed()

        // Verify PID count is displayed
        composeTestRule.onNodeWithText("1 PIDs available")
            .assertExists()
            .assertIsDisplayed()

        // Click to expand category
        composeTestRule.onNodeWithText("Engine")
            .performClick()

        // Verify PID name becomes visible after expansion
        composeTestRule.onNodeWithText("Engine RPM")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testAlertToggle() {
        val testPid = PidData(
            mode = "01",
            pid = "0C",
            name = "Engine RPM",
            bytes = 2,
            unit = "RPM",
            formula = "((A * 256) + B) / 4",
            category = PidCategory.ENGINE,
            subCategory = PidSubCategory.ENGINE_LOAD,
            threshold = Threshold(min = 0.0, max = 8000.0),
            currentValue = 2500.0,
            hasData = true,
            lastUpdated = System.currentTimeMillis(),
            alertsEnabled = false,
            isSuzukiSpecific = false
        )

        var alertToggled = false
        
        composeTestRule.setContent {
            PidDataRow(
                pidData = testPid,
                onToggleAlert = { pid, enabled ->
                    alertToggled = true
                    assert(pid == "0C")
                    assert(enabled)
                }
            )
        }

        // Find and click the switch
        composeTestRule.onNode(hasTestTag("alert_toggle_0C"))
            .performClick()

        // Verify the toggle callback was called
        assert(alertToggled)
    }
}
