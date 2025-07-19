package com.gadies.suzuki.data.simulator

import com.gadies.suzuki.data.model.PidData
import com.gadies.suzuki.data.model.PidMapping
import com.gadies.suzuki.data.model.PidCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * PID Data Simulator for testing and demonstration
 * Generates realistic values for all 74 OBD-II PIDs
 */
class PidDataSimulator(private val scope: CoroutineScope) {
    
    private val _simulatedData = MutableStateFlow<Map<String, PidData>>(emptyMap())
    val simulatedData: StateFlow<Map<String, PidData>> = _simulatedData.asStateFlow()
    
    private var isRunning = false
    
    fun startSimulation() {
        if (isRunning) return
        isRunning = true
        
        scope.launch {
            while (isRunning) {
                updateSimulatedValues()
                delay(1000) // Update every second
            }
        }
    }
    
    fun stopSimulation() {
        isRunning = false
    }
    
    private fun updateSimulatedValues() {
        val currentTime = System.currentTimeMillis()
        val updatedData = mutableMapOf<String, PidData>()
        
        // Standard OBD-II PIDs (Mode 01)
        updatedData.putAll(generateStandardObdPids(currentTime))
        
        // Suzuki Custom PIDs (Mode 21)
        updatedData.putAll(generateSuzukiCustomPids(currentTime))
        
        // Suzuki D13A PIDs (Mode 22)
        updatedData.putAll(generateSuzukiD13APids(currentTime))
        
        _simulatedData.value = updatedData
    }
    
    private fun generateStandardObdPids(timestamp: Long): Map<String, PidData> {
        val data = mutableMapOf<String, PidData>()
        
        // Engine Performance PIDs
        data["01_0C"] = createPidData("01", "0C", "Engine RPM", "rpm", 
            Random.nextDouble(800.0, 3000.0), timestamp)
        data["01_04"] = createPidData("01", "04", "Calculated Load Value", "%", 
            Random.nextDouble(10.0, 80.0), timestamp)
        data["01_0B"] = createPidData("01", "0B", "Intake Manifold Absolute Pressure", "kPa", 
            Random.nextDouble(20.0, 100.0), timestamp)
        data["01_10"] = createPidData("01", "10", "Air Flow Rate (MAF)", "g/s", 
            Random.nextDouble(2.0, 25.0), timestamp)
        
        // Temperature Sensors
        data["01_05"] = createPidData("01", "05", "Engine Coolant Temperature", "°C", 
            Random.nextDouble(80.0, 95.0), timestamp)
        data["01_0F"] = createPidData("01", "0F", "Intake Air Temperature", "°C", 
            Random.nextDouble(20.0, 50.0), timestamp)
        
        // Throttle & Load
        data["01_11"] = createPidData("01", "11", "Absolute Throttle Position", "%", 
            Random.nextDouble(0.0, 100.0), timestamp)
        
        // Air & Fuel System
        data["01_03"] = createPidData("01", "03", "Fuel System Status", "", 
            Random.nextDouble(1.0, 4.0), timestamp)
        data["01_2E"] = createPidData("01", "2E", "Commanded Evaporative Purge", "%", 
            Random.nextDouble(0.0, 100.0), timestamp)
        data["01_2F"] = createPidData("01", "2F", "Fuel Level Input", "%", 
            Random.nextDouble(20.0, 100.0), timestamp)
        data["01_33"] = createPidData("01", "33", "Barometric Pressure", "kPa", 
            Random.nextDouble(95.0, 105.0), timestamp)
        
        // Fuel Trim & Control
        data["01_06"] = createPidData("01", "06", "Short Term Fuel Trim Bank 1", "%", 
            Random.nextDouble(-10.0, 10.0), timestamp)
        data["01_07"] = createPidData("01", "07", "Long Term Fuel Trim Bank 1", "%", 
            Random.nextDouble(-10.0, 10.0), timestamp)
        data["01_08"] = createPidData("01", "08", "Short Term Fuel Trim Bank 2", "%", 
            Random.nextDouble(-10.0, 10.0), timestamp)
        data["01_09"] = createPidData("01", "09", "Long Term Fuel Trim Bank 2", "%", 
            Random.nextDouble(-10.0, 10.0), timestamp)
        
        // Ignition & Timing
        data["01_0E"] = createPidData("01", "0E", "Timing Advance", "°", 
            Random.nextDouble(-10.0, 30.0), timestamp)
        
        // Oxygen Sensors
        data["01_13"] = createPidData("01", "13", "Oxygen Sensors Location", "", 
            Random.nextDouble(1.0, 8.0), timestamp)
        data["01_14"] = createPidData("01", "14", "Bank 1 - Sensor 1", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        data["01_15"] = createPidData("01", "15", "Bank 1 - Sensor 2", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        data["01_16"] = createPidData("01", "16", "Bank 1 - Sensor 3", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        data["01_17"] = createPidData("01", "17", "Bank 1 - Sensor 4", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        data["01_18"] = createPidData("01", "18", "Bank 2 - Sensor 1", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        data["01_19"] = createPidData("01", "19", "Bank 2 - Sensor 2", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        data["01_1A"] = createPidData("01", "1A", "Bank 2 - Sensor 3", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        data["01_1B"] = createPidData("01", "1B", "Bank 2 - Sensor 4", "V", 
            Random.nextDouble(0.1, 0.9), timestamp)
        
        // Speed & Movement
        data["01_0D"] = createPidData("01", "0D", "Vehicle Speed", "km/h", 
            Random.nextDouble(0.0, 120.0), timestamp)
        
        // Environment
        data["01_46"] = createPidData("01", "46", "Ambient Air Temperature", "°C", 
            Random.nextDouble(15.0, 35.0), timestamp)
        
        // Diagnostic
        data["01_01"] = createPidData("01", "01", "Monitor Status", "", 
            Random.nextDouble(0.0, 255.0), timestamp)
        data["01_1C"] = createPidData("01", "1C", "OBD Type", "", 
            Random.nextDouble(1.0, 6.0), timestamp)
        data["01_1F"] = createPidData("01", "1F", "Time Since Engine Start", "s", 
            Random.nextDouble(0.0, 3600.0), timestamp)
        data["01_21"] = createPidData("01", "21", "Distance with MIL On", "km", 
            Random.nextDouble(0.0, 1000.0), timestamp)
        data["01_31"] = createPidData("01", "31", "Distance Since Codes Cleared", "km", 
            Random.nextDouble(0.0, 10000.0), timestamp)
        data["01_4D"] = createPidData("01", "4D", "Time Run by Engine while MIL Activated", "min", 
            Random.nextDouble(0.0, 1000.0), timestamp)
        data["01_4E"] = createPidData("01", "4E", "Time Since Diagnostic Trouble Codes Cleared", "min", 
            Random.nextDouble(0.0, 10000.0), timestamp)
        
        return data
    }
    
    private fun generateSuzukiCustomPids(timestamp: Long): Map<String, PidData> {
        val data = mutableMapOf<String, PidData>()
        
        // Engine Performance
        data["21_00"] = createPidData("21", "00", "Engine RPM (Suzuki)", "rpm", 
            Random.nextDouble(800.0, 3000.0), timestamp, isSuzukiSpecific = true)
        data["21_07"] = createPidData("21", "07", "Manifold Absolute Pressure (Suzuki)", "kPa", 
            Random.nextDouble(20.0, 100.0), timestamp, isSuzukiSpecific = true)
        
        // Temperature Sensors
        data["21_04"] = createPidData("21", "04", "Engine Coolant Temperature (Suzuki)", "°C", 
            Random.nextDouble(80.0, 95.0), timestamp, isSuzukiSpecific = true)
        data["21_05"] = createPidData("21", "05", "Intake Air Temperature (Suzuki)", "°C", 
            Random.nextDouble(20.0, 50.0), timestamp, isSuzukiSpecific = true)
        
        // Throttle & Load
        data["21_06"] = createPidData("21", "06", "Throttle Position (Suzuki)", "%", 
            Random.nextDouble(0.0, 100.0), timestamp, isSuzukiSpecific = true)
        
        // Speed & Movement
        data["21_02"] = createPidData("21", "02", "Vehicle Speed (Suzuki)", "km/h", 
            Random.nextDouble(0.0, 120.0), timestamp, isSuzukiSpecific = true)
        
        // Battery/Electrical
        data["21_08"] = createPidData("21", "08", "Battery Voltage (Suzuki)", "V", 
            Random.nextDouble(11.5, 14.5), timestamp, isSuzukiSpecific = true)
        data["21_09"] = createPidData("21", "09", "Battery Status (Suzuki)", "", 
            Random.nextDouble(0.0, 3.0), timestamp, isSuzukiSpecific = true)
        
        // Engine Control Relays
        data["21_0A"] = createPidData("21", "0A", "Glow Plug Relay (Suzuki)", "", 
            Random.nextDouble(0.0, 1.0), timestamp, isSuzukiSpecific = true)
        data["21_0B"] = createPidData("21", "0B", "Fuel Pump Relay (Suzuki)", "", 
            Random.nextDouble(0.0, 1.0), timestamp, isSuzukiSpecific = true)
        data["21_0C"] = createPidData("21", "0C", "Main Relay (Suzuki)", "", 
            Random.nextDouble(0.0, 1.0), timestamp, isSuzukiSpecific = true)
        data["21_16"] = createPidData("21", "16", "Glow Indicator (Suzuki)", "", 
            Random.nextDouble(0.0, 1.0), timestamp, isSuzukiSpecific = true)
        
        // HVAC
        data["21_0D"] = createPidData("21", "0D", "A/C Relay (Suzuki)", "", 
            Random.nextDouble(0.0, 1.0), timestamp, isSuzukiSpecific = true)
        
        return data
    }
    
    private fun generateSuzukiD13APids(timestamp: Long): Map<String, PidData> {
        val data = mutableMapOf<String, PidData>()
        
        // Temperature Sensors
        data["22_00"] = createPidData("22", "00", "Engine Coolant Temperature (D13A)", "°C", 
            Random.nextDouble(80.0, 95.0), timestamp, isSuzukiSpecific = true)
        data["22_01"] = createPidData("22", "01", "Intake Air Temperature (D13A)", "°C", 
            Random.nextDouble(20.0, 50.0), timestamp, isSuzukiSpecific = true)
        
        // Throttle & Load
        data["22_02"] = createPidData("22", "02", "Throttle Position (D13A)", "%", 
            Random.nextDouble(0.0, 100.0), timestamp, isSuzukiSpecific = true)
        data["22_03"] = createPidData("22", "03", "Accelerator Pedal Position (D13A)", "%", 
            Random.nextDouble(0.0, 100.0), timestamp, isSuzukiSpecific = true)
        
        // Air & Fuel System
        data["22_04"] = createPidData("22", "04", "Fuel Injection Quantity (D13A)", "mm³", 
            Random.nextDouble(0.0, 50.0), timestamp, isSuzukiSpecific = true)
        data["22_05"] = createPidData("22", "05", "Fuel Rail Pressure (D13A)", "bar", 
            Random.nextDouble(200.0, 1600.0), timestamp, isSuzukiSpecific = true)
        
        // Ignition & Timing
        data["22_06"] = createPidData("22", "06", "Injection Timing (D13A)", "°", 
            Random.nextDouble(-10.0, 30.0), timestamp, isSuzukiSpecific = true)
        
        // Speed & Movement
        data["22_07"] = createPidData("22", "07", "Vehicle Speed (D13A)", "km/h", 
            Random.nextDouble(0.0, 120.0), timestamp, isSuzukiSpecific = true)
        
        // Pedal Position
        data["22_08"] = createPidData("22", "08", "Brake Pedal Position (D13A)", "%", 
            Random.nextDouble(0.0, 100.0), timestamp, isSuzukiSpecific = true)
        data["22_09"] = createPidData("22", "09", "Clutch Pedal Position (D13A)", "%", 
            Random.nextDouble(0.0, 100.0), timestamp, isSuzukiSpecific = true)
        
        // Battery/Electrical
        data["22_0A"] = createPidData("22", "0A", "Battery Voltage (D13A)", "V", 
            Random.nextDouble(11.5, 14.5), timestamp, isSuzukiSpecific = true)
        data["22_0B"] = createPidData("22", "0B", "Alternator Voltage (D13A)", "V", 
            Random.nextDouble(12.0, 14.8), timestamp, isSuzukiSpecific = true)
        data["22_0C"] = createPidData("22", "0C", "Battery Current (D13A)", "A", 
            Random.nextDouble(-50.0, 100.0), timestamp, isSuzukiSpecific = true)
        data["22_0D"] = createPidData("22", "0D", "Battery Temperature (D13A)", "°C", 
            Random.nextDouble(15.0, 45.0), timestamp, isSuzukiSpecific = true)
        data["22_0E"] = createPidData("22", "0E", "Battery SOC (D13A)", "%", 
            Random.nextDouble(20.0, 100.0), timestamp, isSuzukiSpecific = true)
        
        // ISG System
        data["22_0F"] = createPidData("22", "0F", "ISG Motor Speed (D13A)", "rpm", 
            Random.nextDouble(0.0, 1500.0), timestamp, isSuzukiSpecific = true)
        data["22_10"] = createPidData("22", "10", "ISG Motor Torque (D13A)", "Nm", 
            Random.nextDouble(-50.0, 50.0), timestamp, isSuzukiSpecific = true)
        data["22_11"] = createPidData("22", "11", "ISG System Status (D13A)", "", 
            Random.nextDouble(0.0, 7.0), timestamp, isSuzukiSpecific = true)
        
        // Brake System
        data["22_12"] = createPidData("22", "12", "Brake Vacuum Sensor (D13A)", "kPa", 
            Random.nextDouble(40.0, 90.0), timestamp, isSuzukiSpecific = true)
        data["22_13"] = createPidData("22", "13", "Brake Stroke Sensor (D13A)", "mm", 
            Random.nextDouble(0.0, 50.0), timestamp, isSuzukiSpecific = true)
        
        // Environment
        data["22_14"] = createPidData("22", "14", "Atmospheric Pressure (D13A)", "kPa", 
            Random.nextDouble(95.0, 105.0), timestamp, isSuzukiSpecific = true)
        
        // Diagnostic
        data["22_15"] = createPidData("22", "15", "DTC Count (D13A)", "", 
            Random.nextDouble(0.0, 10.0), timestamp, isSuzukiSpecific = true)
        
        return data
    }
    
    private fun createPidData(
        mode: String,
        pid: String,
        name: String,
        unit: String,
        value: Double,
        timestamp: Long,
        isSuzukiSpecific: Boolean = false
    ): PidData {
        val subCategory = PidMapping.getSubCategory(mode, pid)
        return PidData(
            pid = pid,
            mode = mode,
            name = name,
            unit = unit,
            formula = "A", // Simplified formula
            bytes = 1,
            category = subCategory?.category ?: PidCategory.DIAGNOSTIC,
            uiType = "gauge",
            subCategory = subCategory,
            currentValue = value,
            hasData = true,
            lastUpdated = timestamp,
            isSuzukiSpecific = isSuzukiSpecific,
            alertsEnabled = true
        )
    }
}
