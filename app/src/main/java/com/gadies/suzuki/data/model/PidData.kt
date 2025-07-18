package com.gadies.suzuki.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PidData(
    val pid: String,
    val mode: String,
    val name: String,
    val unit: String,
    val formula: String,
    val bytes: Int,
    val category: String,
    val uiType: String,
    val subCategory: PidSubCategory? = null,
    val threshold: Threshold? = null,
    val currentValue: Double = 0.0,
    val rawHexValue: String = "",
    val lastUpdated: Long = 0L,
    val isEnabled: Boolean = true,
    val alertsEnabled: Boolean = true,
    val addressOffset: Int? = null,
    val isSuzukiSpecific: Boolean = false,
    val hasData: Boolean = false,
    val errorMessage: String? = null
) : Parcelable

@Parcelize
data class Threshold(
    val min: Double? = null,
    val max: Double? = null,
    val normal: DoubleRange? = null,
    val caution: DoubleRange? = null,
    val danger: DoubleRange? = null
) : Parcelable

@Parcelize
data class DoubleRange(
    val start: Double,
    val end: Double
) : Parcelable

enum class PidCategory(val displayName: String) {
    ENGINE("ENGINE SYSTEM"),
    VEHICLE("VEHICLE SYSTEM"), 
    BATTERY("BATTERY/ELECTRICAL SYSTEM"),
    BRAKE("BRAKE SYSTEM"),
    HVAC("HVAC SYSTEM"),
    ENVIRONMENT("ENVIRONMENT SYSTEM"),
    DIAGNOSTIC("DIAGNOSTIC SYSTEM")
}

enum class PidSubCategory(val displayName: String, val category: PidCategory) {
    // ENGINE SYSTEM sub-categories
    ENGINE_PERFORMANCE("Engine Performance", PidCategory.ENGINE),
    TEMPERATURE_SENSORS("Temperature Sensors", PidCategory.ENGINE),
    THROTTLE_LOAD("Throttle & Load", PidCategory.ENGINE),
    AIR_FUEL_SYSTEM("Air & Fuel System", PidCategory.ENGINE),
    FUEL_TRIM_CONTROL("Fuel Trim & Control", PidCategory.ENGINE),
    IGNITION_TIMING("Ignition & Timing", PidCategory.ENGINE),
    OXYGEN_SENSORS("Oxygen Sensors", PidCategory.ENGINE),
    ENGINE_CONTROL_RELAYS("Engine Control Relays", PidCategory.ENGINE),
    
    // VEHICLE SYSTEM sub-categories
    SPEED_MOVEMENT("Speed & Movement", PidCategory.VEHICLE),
    PEDAL_POSITION("Pedal Position", PidCategory.VEHICLE),
    
    // BATTERY/ELECTRICAL SYSTEM sub-categories
    VOLTAGE_CURRENT("Voltage & Current", PidCategory.BATTERY),
    STATUS_HEALTH("Status & Health", PidCategory.BATTERY),
    ISG_SYSTEM("ISG System", PidCategory.BATTERY),
    
    // BRAKE SYSTEM sub-categories
    VACUUM_STROKE_SENSORS("Vacuum & Stroke Sensors", PidCategory.BRAKE),
    
    // HVAC SYSTEM sub-categories
    AC_RELAY("A/C Relay", PidCategory.HVAC),
    
    // ENVIRONMENT SYSTEM sub-categories
    AMBIENT_BAROMETRIC("Ambient & Barometric Pressure", PidCategory.ENVIRONMENT),
    
    // DIAGNOSTIC SYSTEM sub-categories
    RUNTIME_MIL_OBD("Runtime, MIL, OBD Type, Emission Systems", PidCategory.DIAGNOSTIC)
}

enum class AlertLevel {
    NORMAL,
    CAUTION, 
    DANGER
}

data class PidAlert(
    val pidData: PidData,
    val level: AlertLevel,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
