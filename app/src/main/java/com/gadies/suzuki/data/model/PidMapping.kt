package com.gadies.suzuki.data.model

/**
 * Comprehensive PID mapping for all 74 OBD-II PIDs from thresholds.json
 * Maps each PID to its appropriate sub-category for organized display
 */
object PidMapping {
    
    /**
     * Maps PID identifiers to their sub-categories
     * Format: "mode_pid" -> PidSubCategory
     */
    val pidSubCategoryMap = mapOf(
        // ENGINE SYSTEM - Engine Performance
        "01_0C" to PidSubCategory.ENGINE_PERFORMANCE, // Engine RPM
        "21_00" to PidSubCategory.ENGINE_PERFORMANCE, // Engine RPM (Suzuki)
        "01_04" to PidSubCategory.ENGINE_PERFORMANCE, // Calculated load value
        "01_0B" to PidSubCategory.ENGINE_PERFORMANCE, // Intake manifold absolute pressure
        "21_07" to PidSubCategory.ENGINE_PERFORMANCE, // Manifold absolute pressure (Suzuki)
        "01_10" to PidSubCategory.ENGINE_PERFORMANCE, // Air flow rate (MAF)
        
        // ENGINE SYSTEM - Temperature Sensors
        "01_05" to PidSubCategory.TEMPERATURE_SENSORS, // Engine coolant temperature
        "21_04" to PidSubCategory.TEMPERATURE_SENSORS, // Engine coolant temperature (Suzuki)
        "01_0F" to PidSubCategory.TEMPERATURE_SENSORS, // Intake air temperature
        "21_05" to PidSubCategory.TEMPERATURE_SENSORS, // Intake air temperature (Suzuki)
        "22_00" to PidSubCategory.TEMPERATURE_SENSORS, // Engine coolant temperature (D13A)
        "22_01" to PidSubCategory.TEMPERATURE_SENSORS, // Intake air temperature (D13A)
        
        // ENGINE SYSTEM - Throttle & Load
        "01_11" to PidSubCategory.THROTTLE_LOAD, // Absolute throttle position
        "21_06" to PidSubCategory.THROTTLE_LOAD, // Throttle position (Suzuki)
        "22_02" to PidSubCategory.THROTTLE_LOAD, // Throttle position (D13A)
        "22_03" to PidSubCategory.THROTTLE_LOAD, // Accelerator pedal position (D13A)
        
        // ENGINE SYSTEM - Air & Fuel System
        "01_03" to PidSubCategory.AIR_FUEL_SYSTEM, // Fuel system status
        "01_2E" to PidSubCategory.AIR_FUEL_SYSTEM, // Commanded Evaporative Purge
        "01_2F" to PidSubCategory.AIR_FUEL_SYSTEM, // Fuel Level Input
        "01_33" to PidSubCategory.AIR_FUEL_SYSTEM, // Barometric pressure
        "22_04" to PidSubCategory.AIR_FUEL_SYSTEM, // Fuel injection quantity (D13A)
        "22_05" to PidSubCategory.AIR_FUEL_SYSTEM, // Fuel rail pressure (D13A)
        
        // ENGINE SYSTEM - Fuel Trim & Control
        "01_06" to PidSubCategory.FUEL_TRIM_CONTROL, // Short term fuel trim Bank 1
        "01_07" to PidSubCategory.FUEL_TRIM_CONTROL, // Long term fuel trim Bank 1
        "01_08" to PidSubCategory.FUEL_TRIM_CONTROL, // Short term fuel trim Bank 2
        "01_09" to PidSubCategory.FUEL_TRIM_CONTROL, // Long term fuel trim Bank 2
        
        // ENGINE SYSTEM - Ignition & Timing
        "01_0E" to PidSubCategory.IGNITION_TIMING, // Timing advance
        "22_06" to PidSubCategory.IGNITION_TIMING, // Injection timing (D13A)
        
        // ENGINE SYSTEM - Oxygen Sensors
        "01_13" to PidSubCategory.OXYGEN_SENSORS, // Oxygen sensors location
        "01_14" to PidSubCategory.OXYGEN_SENSORS, // Bank 1 - Sensor 1 (wide range O2S)
        "01_15" to PidSubCategory.OXYGEN_SENSORS, // Bank 1 - Sensor 2 (wide range O2S)
        "01_16" to PidSubCategory.OXYGEN_SENSORS, // Bank 1 - Sensor 3 (wide range O2S)
        "01_17" to PidSubCategory.OXYGEN_SENSORS, // Bank 1 - Sensor 4 (wide range O2S)
        "01_18" to PidSubCategory.OXYGEN_SENSORS, // Bank 2 - Sensor 1 (wide range O2S)
        "01_19" to PidSubCategory.OXYGEN_SENSORS, // Bank 2 - Sensor 2 (wide range O2S)
        "01_1A" to PidSubCategory.OXYGEN_SENSORS, // Bank 2 - Sensor 3 (wide range O2S)
        "01_1B" to PidSubCategory.OXYGEN_SENSORS, // Bank 2 - Sensor 4 (wide range O2S)
        
        // ENGINE SYSTEM - Engine Control Relays
        "21_0A" to PidSubCategory.ENGINE_CONTROL_RELAYS, // Glow plug relay (Suzuki)
        "21_0B" to PidSubCategory.ENGINE_CONTROL_RELAYS, // Fuel pump relay (Suzuki)
        "21_0C" to PidSubCategory.ENGINE_CONTROL_RELAYS, // Main relay (Suzuki)
        "21_16" to PidSubCategory.ENGINE_CONTROL_RELAYS, // Glow indicator (Suzuki)
        
        // VEHICLE SYSTEM - Speed & Movement
        "01_0D" to PidSubCategory.SPEED_MOVEMENT, // Vehicle speed
        "21_02" to PidSubCategory.SPEED_MOVEMENT, // Vehicle speed (Suzuki)
        "22_07" to PidSubCategory.SPEED_MOVEMENT, // Vehicle speed (D13A)
        
        // VEHICLE SYSTEM - Pedal Position
        "22_08" to PidSubCategory.PEDAL_POSITION, // Brake pedal position (D13A)
        "22_09" to PidSubCategory.PEDAL_POSITION, // Clutch pedal position (D13A)
        
        // BATTERY/ELECTRICAL SYSTEM - Voltage & Current
        "21_08" to PidSubCategory.VOLTAGE_CURRENT, // Battery voltage (Suzuki)
        "22_0A" to PidSubCategory.VOLTAGE_CURRENT, // Battery voltage (D13A)
        "22_0B" to PidSubCategory.VOLTAGE_CURRENT, // Alternator voltage (D13A)
        "22_0C" to PidSubCategory.VOLTAGE_CURRENT, // Battery current (D13A)
        
        // BATTERY/ELECTRICAL SYSTEM - Status & Health
        "21_09" to PidSubCategory.STATUS_HEALTH, // Battery status (Suzuki)
        "22_0D" to PidSubCategory.STATUS_HEALTH, // Battery temperature (D13A)
        "22_0E" to PidSubCategory.STATUS_HEALTH, // Battery SOC (State of Charge) (D13A)
        
        // BATTERY/ELECTRICAL SYSTEM - ISG System
        "22_0F" to PidSubCategory.ISG_SYSTEM, // ISG motor speed (D13A)
        "22_10" to PidSubCategory.ISG_SYSTEM, // ISG motor torque (D13A)
        "22_11" to PidSubCategory.ISG_SYSTEM, // ISG system status (D13A)
        
        // BRAKE SYSTEM - Vacuum & Stroke Sensors
        "22_12" to PidSubCategory.VACUUM_STROKE_SENSORS, // Brake vacuum sensor (D13A)
        "22_13" to PidSubCategory.VACUUM_STROKE_SENSORS, // Brake stroke sensor (D13A)
        
        // HVAC SYSTEM - A/C Relay
        "21_0D" to PidSubCategory.AC_RELAY, // A/C relay (Suzuki)
        
        // ENVIRONMENT SYSTEM - Ambient & Barometric Pressure
        "01_46" to PidSubCategory.AMBIENT_BAROMETRIC, // Ambient air temperature
        "22_14" to PidSubCategory.AMBIENT_BAROMETRIC, // Atmospheric pressure (D13A)
        
        // DIAGNOSTIC SYSTEM - Runtime, MIL, OBD Type, Emission Systems
        "01_01" to PidSubCategory.RUNTIME_MIL_OBD, // Monitor status
        "01_1C" to PidSubCategory.RUNTIME_MIL_OBD, // OBD type
        "01_1F" to PidSubCategory.RUNTIME_MIL_OBD, // Time since engine start
        "01_21" to PidSubCategory.RUNTIME_MIL_OBD, // Distance with MIL on
        "01_31" to PidSubCategory.RUNTIME_MIL_OBD, // Distance since codes cleared
        "01_4D" to PidSubCategory.RUNTIME_MIL_OBD, // Time run by the engine while MIL activated
        "01_4E" to PidSubCategory.RUNTIME_MIL_OBD, // Time since diagnostic trouble codes cleared
        "22_15" to PidSubCategory.RUNTIME_MIL_OBD  // DTC count (D13A)
    )
    
    /**
     * Gets the sub-category for a given PID
     */
    fun getSubCategory(mode: String, pid: String): PidSubCategory? {
        val key = "${mode}_${pid.removePrefix("0x").uppercase()}"
        return pidSubCategoryMap[key]
    }
    
    /**
     * Gets all PIDs for a specific category
     */
    fun getPidsForCategory(category: PidCategory): List<String> {
        return pidSubCategoryMap.filter { (_, subCategory) ->
            subCategory.category == category
        }.keys.toList()
    }
    
    /**
     * Gets all PIDs for a specific sub-category
     */
    fun getPidsForSubCategory(subCategory: PidSubCategory): List<String> {
        return pidSubCategoryMap.filter { (_, sub) ->
            sub == subCategory
        }.keys.toList()
    }
    
    /**
     * Dashboard PIDs - highlighted on main dashboard
     */
    val dashboardPids = setOf(
        "01_05", // Engine coolant temperature
        "21_04", // Engine coolant temperature (Suzuki)
        "21_08", // Battery voltage (Suzuki)
        "22_0A"  // Battery voltage (D13A)
    )
    
    /**
     * Checks if a PID should be displayed on the main dashboard
     */
    fun isDashboardPid(mode: String, pid: String): Boolean {
        val key = "${mode}_${pid.removePrefix("0x").uppercase()}"
        return dashboardPids.contains(key)
    }
}
