package com.gadies.suzuki.ui.viewmodel

import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.data.repository.PidRepository
import com.gadies.suzuki.service.AiService
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val mockPidRepository = mockk<PidRepository>()
    private val mockAiService = mockk<AiService>()
    
    private val testDispatcher = StandardTestDispatcher()

    // Mock StateFlows
    private val mockPidDataMap = MutableStateFlow<Map<String, PidData>>(emptyMap())
    private val mockCategorizedPids = MutableStateFlow<Map<PidCategory, List<PidData>>>(emptyMap())
    private val mockDashboardPids = MutableStateFlow<List<PidData>>(emptyList())
    private val mockAlerts = MutableStateFlow<List<PidAlert>>(emptyList())
    private val mockConnectionState = MutableStateFlow(ConnectionState())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock repository flows
        every { mockPidRepository.pidDataMap } returns mockPidDataMap
        every { mockPidRepository.categorizedPids } returns mockCategorizedPids
        every { mockPidRepository.dashboardPids } returns mockDashboardPids
        every { mockPidRepository.alerts } returns mockAlerts
        
        // Mock repository methods
        every { mockPidRepository.updatePidDataMap(any()) } just Runs
        
        // Mock AI service
        coEvery { mockAiService.analyzeVehicleHealth(any()) } returns Result.success(
            AiAnalysisResult(
                healthScore = 85,
                summary = "Vehicle is in good condition",
                recommendations = listOf("Regular maintenance recommended"),
                issues = emptyList(),
                timestamp = System.currentTimeMillis()
            )
        )
        
        coEvery { mockAiService.sendChatMessage(any(), any(), any(), any(), any()) } returns Result.success("AI response")
        
        viewModel = MainViewModel(mockPidRepository, mockAiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test initial state`() = runTest {
        // Verify initial connection state
        assertEquals(ConnectionState(), viewModel.connectionState.first())
        
        // Verify empty initial data
        assertTrue("PID data should be empty initially", viewModel.pidDataMap.first().isEmpty())
        assertTrue("Alerts should be empty initially", viewModel.alerts.first().isEmpty())
    }

    @Test 
    fun `test category selection`() = runTest {
        val enginePid = PidData(
            mode = "01",
            pid = "05",
            name = "Coolant Temperature",
            unit = "°C",
            formula = "A-40",
            uiType = "gauge",
            bytes = 1,
            category = PidCategory.ENGINE
        )

        val transmissionPid = PidData(
            mode = "01", 
            pid = "0D",
            name = "Vehicle Speed",
            unit = "km/h",
            formula = "A",
            uiType = "gauge", 
            bytes = 1,
            category = PidCategory.TRANSMISSION
        )

        mockCategorizedPids.value = mapOf(
            PidCategory.ENGINE to listOf(enginePid),
            PidCategory.TRANSMISSION to listOf(transmissionPid)
        )

        viewModel.selectCategory(PidCategory.ENGINE)
        
        assertEquals(PidCategory.ENGINE, viewModel.selectedCategory.first())
    }

    @Test
    fun `test chat message`() = runTest {
        val testMessage = "What's wrong with my engine?"
        
        viewModel.sendChatMessage(testMessage)
        testScheduler.advanceUntilIdle()
        
        // Verify chat messages were updated
        val chatMessages = viewModel.chatMessages.first()
        assertTrue("Should have user message", chatMessages.any { 
                        it.content == testMessage && it.isFromUser
        })
        assertTrue("Should have AI response", chatMessages.any { 
            it.content == "AI response" && !it.isFromUser
        })
        
        // Verify AI service was called
        coVerify { mockAiService.sendChatMessage(testMessage, any()) }
    }

    @Test
    fun `test connection state management`() = runTest {
        // Test connecting state
        viewModel.updateConnectionState(ConnectionState.CONNECTING)
        assertEquals(ConnectionState.CONNECTING, viewModel.connectionState.first())
        
        // Test connected state
        viewModel.updateConnectionState(ConnectionState.CONNECTED)
        assertEquals(ConnectionState.CONNECTED, viewModel.connectionState.first())
        
        // Test disconnected state
        viewModel.updateConnectionState(ConnectionState.DISCONNECTED)
        assertEquals(ConnectionState.DISCONNECTED, viewModel.connectionState.first())
    }

    @Test
    fun `test coolant temperature PID getter`() = runTest {
        val coolantPid = PidData(
            pid = "01_05",
            mode = "01",
            name = "Engine Coolant Temperature",
            unit = "°C",
            formula = "A-40",
            bytes = 1,
            category = "ENGINE",
            uiType = "gauge",
            currentValue = 90.0,
            hasData = true
        )
        
        mockPidDataMap.value = mapOf("01_05" to coolantPid)
        
        val result = viewModel.getCoolantTemperaturePid().first()
        assertNotNull("Coolant PID should not be null", result)
        assertEquals("Should return coolant PID", coolantPid, result)
    }

    @Test
    fun `test battery voltage PID getter`() = runTest {
        val batteryPid = PidData(
            pid = "21_08",
            mode = "21",
            name = "Battery Voltage",
            unit = "V",
            formula = "A*0.1",
            bytes = 1,
            category = "BATTERY",
            uiType = "gauge",
            currentValue = 12.5,
            hasData = true
        )
        
        mockPidDataMap.value = mapOf("21_08" to batteryPid)
        
        val result = viewModel.getBatteryVoltagePid().first()
        assertNotNull("Battery PID should not be null", result)
        assertEquals("Should return battery PID", batteryPid, result)
    }
}
