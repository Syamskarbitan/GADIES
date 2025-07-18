package com.gadies.suzuki.data.repository

import android.content.Context
import com.gadies.suzuki.data.model.PidData
import com.gadies.suzuki.data.model.PidCategory
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.io.ByteArrayInputStream

class PidRepositoryTest {

    private lateinit var pidRepository: PidRepository
    private val mockContext = mockk<Context>()
    private val gson = Gson()

    private val mockThresholdsJson = """
    {
        "metadata": {
            "version": "1.0",
            "description": "Test thresholds"
        },
        "standard_obd2": {
            "01_05": {
                "name": "Engine Coolant Temperature",
                "unit": "°C",
                "formula": "A-40",
                "bytes": 1,
                "category": "ENGINE",
                "ui_type": "gauge",
                "threshold": {
                    "min": -40,
                    "max": 215,
                    "normal": {"start": 80, "end": 100},
                    "caution": {"start": 100, "end": 110},
                    "danger": {"start": 110, "end": 215}
                }
            }
        },
        "suzuki_custom": {},
        "d13a_ecu": {}
    }
    """.trimIndent()

    @Before
    fun setup() {
        every { mockContext.assets.open("thresholds.json") } returns 
            ByteArrayInputStream(mockThresholdsJson.toByteArray())
        
        pidRepository = PidRepository(mockContext, gson)
    }

    @Test
    fun `test PID data loading from JSON`() = runTest {
        // Wait for initialization
        val pidDataMap = pidRepository.pidDataMap.first()
        
        // Verify PID is loaded
        assertTrue("PID 01_05 should be loaded", pidDataMap.containsKey("01_05"))
        
        val pidData = pidDataMap["01_05"]
        assertNotNull("PID data should not be null", pidData)
        assertEquals("Engine Coolant Temperature", pidData?.name)
        assertEquals("°C", pidData?.unit)
        assertEquals("ENGINE", pidData?.category)
    }

    @Test
    fun `test categorized PIDs grouping`() = runTest {
        val categorizedPids = pidRepository.categorizedPids.first()
        
        // Verify ENGINE category exists
        assertTrue("ENGINE category should exist", 
            categorizedPids.containsKey(PidCategory.ENGINE))
        
        val enginePids = categorizedPids[PidCategory.ENGINE]
        assertNotNull("Engine PIDs should not be null", enginePids)
        assertTrue("Engine PIDs should not be empty", enginePids!!.isNotEmpty())
    }

    @Test
    fun `test dashboard PIDs identification`() = runTest {
        val dashboardPids = pidRepository.dashboardPids.first()
        
        // Should have dashboard PIDs (coolant temp is a dashboard PID)
        assertTrue("Dashboard PIDs should not be empty", dashboardPids.isNotEmpty())
        
        // Check if coolant temperature PID is in dashboard
        val coolantPid = dashboardPids.find { it.pid == "01_05" }
        assertNotNull("Coolant temperature should be in dashboard PIDs", coolantPid)
    }

    @Test
    fun `test PID data update`() = runTest {
        val testPidData = mapOf(
            "01_05" to 85.0,
            "01_0C" to 2500.0
        )
        
        pidRepository.updatePidData(testPidData)
        
        val pidDataMap = pidRepository.pidDataMap.first()
        val coolantPid = pidDataMap["01_05"]
        
        assertNotNull("Coolant PID should exist", coolantPid)
        assertEquals("Coolant temperature should be updated", 85.0, coolantPid?.currentValue, 0.1)
        assertTrue("PID should have data", coolantPid?.hasData == true)
    }

    @Test
    fun `test alert generation for threshold violation`() = runTest {
        // Update with high temperature (danger zone)
        val testPidData = mapOf("01_05" to 115.0) // Above danger threshold
        
        pidRepository.updatePidData(testPidData)
        
        val alerts = pidRepository.alerts.first()
        
        // Should generate alert for high temperature
        assertTrue("Should have alerts for high temperature", alerts.isNotEmpty())
        
        val coolantAlert = alerts.find { it.pidId == "01_05" }
        assertNotNull("Should have alert for coolant temperature", coolantAlert)
    }
}
