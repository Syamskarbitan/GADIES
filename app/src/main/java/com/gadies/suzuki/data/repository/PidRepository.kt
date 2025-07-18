package com.gadies.suzuki.data.repository

import android.content.Context
import com.gadies.suzuki.data.model.*
import com.gadies.suzuki.data.model.PidData
import com.gadies.suzuki.data.model.PidCategory
import com.gadies.suzuki.data.model.PidMapping
import com.gadies.suzuki.data.model.Threshold
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PidRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    
    private val _pidDataMap = MutableStateFlow<Map<String, PidData>>(emptyMap())
    val pidDataMap: StateFlow<Map<String, PidData>> = _pidDataMap.asStateFlow()
    
    private val _dashboardPids = MutableStateFlow<List<PidData>>(emptyList())
    val dashboardPids: StateFlow<List<PidData>> = _dashboardPids.asStateFlow()
    
    private val _categorizedPids = MutableStateFlow<Map<PidCategory, List<PidData>>>(emptyMap())
    val categorizedPids: StateFlow<Map<PidCategory, List<PidData>>> = _categorizedPids.asStateFlow()
    
    private val _alerts = MutableStateFlow<List<PidAlert>>(emptyList())
    val alerts: StateFlow<List<PidAlert>> = _alerts.asStateFlow()
    
    init {
        loadPidConfiguration()
    }
    
    private fun loadPidConfiguration() {
        try {
            val inputStream = context.assets.open("thresholds.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val thresholdsData = gson.fromJson(jsonString, ThresholdsData::class.java)
            
            val pidMap = mutableMapOf<String, PidData>()
            val dashboardPids = mutableListOf<PidData>()
            
            // Load Standard OBD-II PIDs (Mode 01)
            thresholdsData.standard_obdii_pids.forEach { pidInfo ->
                val subCategory = PidMapping.getSubCategory(pidInfo.mode, pidInfo.pid)
                val pidData = PidData(
                    pid = pidInfo.pid,
                    mode = pidInfo.mode,
                    name = pidInfo.name,
                    unit = pidInfo.unit,
                    formula = pidInfo.formula,
                    bytes = pidInfo.bytes,
                    category = pidInfo.category,
                    uiType = pidInfo.ui_type,
                    subCategory = subCategory,
                    threshold = pidInfo.threshold?.let {
                        Threshold(
                            min = it.min,
                            max = it.max
                        )
                    },
                    isSuzukiSpecific = false
                )
                
                val key = "${pidInfo.mode}_${pidInfo.pid.removePrefix("0x")}"
                pidMap[key] = pidData
                
                // Add to dashboard if it's a key PID
                if (PidMapping.isDashboardPid(pidInfo.mode, pidInfo.pid)) {
                    dashboardPids.add(pidData)
                }
            }
            
            // Load Suzuki Custom PIDs (Mode 21)
            thresholdsData.suzuki_custom_pids.forEach { pidInfo ->
                val subCategory = PidMapping.getSubCategory(pidInfo.mode, pidInfo.pid)
                val pidData = PidData(
                    pid = pidInfo.pid,
                    mode = pidInfo.mode,
                    name = pidInfo.name,
                    unit = pidInfo.unit,
                    formula = pidInfo.formula,
                    bytes = pidInfo.bytes,
                    category = pidInfo.category,
                    uiType = pidInfo.ui_type,
                    subCategory = subCategory,
                    threshold = pidInfo.threshold?.let {
                        Threshold(
                            min = it.min,
                            max = it.max
                        )
                    },
                    addressOffset = pidInfo.address_offset,
                    isSuzukiSpecific = pidInfo.suzuki_specific ?: true
                )
                
                val key = "${pidInfo.mode}_${pidInfo.pid.removePrefix("0x")}"
                pidMap[key] = pidData
                
                // Add to dashboard if it's a key PID
                if (PidMapping.isDashboardPid(pidInfo.mode, pidInfo.pid)) {
                    dashboardPids.add(pidData)
                }
            }
            
            // Load Suzuki D13A PIDs (Mode 22)
            thresholdsData.suzuki_d13a_02_pids.forEach { pidInfo ->
                val subCategory = PidMapping.getSubCategory(pidInfo.mode, pidInfo.pid)
                val pidData = PidData(
                    pid = pidInfo.pid,
                    mode = pidInfo.mode,
                    name = pidInfo.name,
                    unit = pidInfo.unit,
                    formula = pidInfo.formula,
                    bytes = pidInfo.bytes,
                    category = pidInfo.category,
                    uiType = pidInfo.ui_type,
                    subCategory = subCategory,
                    threshold = pidInfo.threshold?.let {
                        Threshold(
                            min = it.min,
                            max = it.max
                        )
                    },
                    addressOffset = pidInfo.address_offset,
                    isSuzukiSpecific = pidInfo.suzuki_specific ?: true
                )
                
                val key = "${pidInfo.mode}_${pidInfo.pid.removePrefix("0x")}"
                pidMap[key] = pidData
                
                // Add to dashboard if it's a key PID
                if (PidMapping.isDashboardPid(pidInfo.mode, pidInfo.pid)) {
                    dashboardPids.add(pidData)
                }
            }
            
            _pidDataMap.value = pidMap
            _dashboardPids.value = dashboardPids
            updateCategorizedPids()
            _categorizedPids.value = categorized
            
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    
    fun updatePidValue(pid: String, value: Double, rawHex: String) {
        val currentMap = _pidDataMap.value.toMutableMap()
        currentMap[pid]?.let { pidData ->
            val updatedPid = pidData.copy(
                currentValue = value,
                rawHexValue = rawHex,
                lastUpdated = System.currentTimeMillis()
            )
            currentMap[pid] = updatedPid
            
            // Check for alerts
            checkAndCreateAlert(updatedPid)
        }
        _pidDataMap.value = currentMap
        updateCategorizedPids()
    }
    
    private fun checkAndCreateAlert(pidData: PidData) {
        if (!pidData.alertsEnabled) return
        
        pidData.threshold?.let { threshold ->
            val alertLevel = when {
                threshold.max != null && pidData.currentValue > threshold.max -> AlertLevel.DANGER
                threshold.min != null && pidData.currentValue < threshold.min -> AlertLevel.DANGER
                else -> AlertLevel.NORMAL
            }
            
            if (alertLevel != AlertLevel.NORMAL) {
                val message = when (alertLevel) {
                    AlertLevel.DANGER -> "${pidData.name} is at critical level: ${pidData.currentValue}${pidData.unit}"
                    AlertLevel.CAUTION -> "${pidData.name} is at caution level: ${pidData.currentValue}${pidData.unit}"
                    else -> ""
                }
                
                val alert = PidAlert(pidData, alertLevel, message)
                val currentAlerts = _alerts.value.toMutableList()
                currentAlerts.add(0, alert) // Add to beginning
                
                // Keep only last 50 alerts
                if (currentAlerts.size > 50) {
                    currentAlerts.removeAt(currentAlerts.size - 1)
                }
                
                _alerts.value = currentAlerts
            }
        }
    }
    
    private fun updateCategorizedPids() {
        val allPids = _pidDataMap.value
        val categorized = mutableMapOf<PidCategory, List<PidData>>()
        
        PidCategory.values().forEach { category ->
            categorized[category] = allPids.values.filter { pidData ->
                // Match by category name or sub-category's parent category
                pidData.category.equals(category.displayName, true) ||
                pidData.category.contains(category.name, true) ||
                pidData.subCategory?.category == category
            }
        }
        _categorizedPids.value = categorized
    }
    
    fun clearAlerts() {
        _alerts.value = emptyList()
    }
    
    fun togglePidAlerts(pid: String, enabled: Boolean) {
        val currentMap = _pidDataMap.value.toMutableMap()
        currentMap[pid]?.let { pidData ->
            currentMap[pid] = pidData.copy(alertsEnabled = enabled)
        }
        _pidDataMap.value = currentMap
    }
    
    fun resetThresholds() {
        // Reload thresholds from JSON file
        loadPidConfiguration()
    }
    
    // Methods for simulator integration
    fun updatePidDataMap(newData: Map<String, PidData>) {
        _pidDataMap.value = newData
        updateCategorizedPids()
        updateDashboardPids()
    }
    
    fun updateDashboardPids() {
        val allPids = _pidDataMap.value
        val dashboardPids = allPids.values.filter { pidData ->
            PidMapping.isDashboardPid(pidData.mode, pidData.pid)
        }
        _dashboardPids.value = dashboardPids
    }
}

// Data classes for JSON parsing
data class ThresholdsData(
    val metadata: MetadataInfo,
    val standard_obdii_pids: List<PidInfo>,
    val suzuki_custom_pids: List<PidInfo>,
    val suzuki_d13a_02_pids: List<PidInfo>
) {
    // Gson naming strategy
    val standardObdiiPids get() = standard_obdii_pids
    val suzukiCustomPids get() = suzuki_custom_pids
    val suzukiD13a02Pids get() = suzuki_d13a_02_pids
}

data class MetadataInfo(
    val title: String,
    val description: String,
    val version: String
)

data class PidInfo(
    val pid: String,
    val mode: String,
    val name: String,
    val unit: String,
    val formula: String,
    val bytes: Int,
    val category: String,
    val ui_type: String,
    val threshold: ThresholdInfo? = null,
    val address_offset: Int? = null,
    val suzuki_specific: Boolean? = null
) {
    val uiType get() = ui_type
}

data class ThresholdInfo(
    val min: Double? = null,
    val max: Double? = null
)

data class ConfigData(
    val dashboard_highlight: List<String>,
    val categories: Map<String, List<String>>,
    val ai_analysis_questions: List<AiQuestion>
) {
    val dashboardHighlight get() = dashboard_highlight
    val aiAnalysisQuestions get() = ai_analysis_questions
}
