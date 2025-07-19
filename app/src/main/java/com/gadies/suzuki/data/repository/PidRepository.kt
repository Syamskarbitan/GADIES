package com.gadies.suzuki.data.repository

import android.content.Context
import com.gadies.suzuki.data.model.*
import com.google.gson.Gson
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
            val dashboardPidsList = mutableListOf<PidData>() // DIUBAH: Ganti nama agar tidak konflik

            // Helper function untuk mengubah String ke PidCategory
            fun String.toPidCategory(): PidCategory {
                return try {
                    PidCategory.valueOf(this.uppercase())
                } catch (e: IllegalArgumentException) {
                    PidCategory.DIAGNOSTIC // Default jika tidak ditemukan
                }
            }

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
                    category = pidInfo.category.toPidCategory(), // DIUBAH: Konversi String ke Enum
                    uiType = pidInfo.ui_type,
                    subCategory = subCategory,
                    threshold = pidInfo.threshold?.let {
                        Threshold(min = it.min, max = it.max)
                    },
                    isSuzukiSpecific = false
                )

                val key = "${pidInfo.mode}_${pidInfo.pid.removePrefix("0x")}"
                pidMap[key] = pidData

                if (PidMapping.isDashboardPid(pidInfo.mode, pidInfo.pid)) {
                    dashboardPidsList.add(pidData)
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
                    category = pidInfo.category.toPidCategory(), // DIUBAH: Konversi String ke Enum
                    uiType = pidInfo.ui_type,
                    subCategory = subCategory,
                    threshold = pidInfo.threshold?.let {
                        Threshold(min = it.min, max = it.max)
                    },
                    addressOffset = pidInfo.address_offset,
                    isSuzukiSpecific = pidInfo.suzuki_specific ?: true
                )

                val key = "${pidInfo.mode}_${pidInfo.pid.removePrefix("0x")}"
                pidMap[key] = pidData

                if (PidMapping.isDashboardPid(pidInfo.mode, pidInfo.pid)) {
                    dashboardPidsList.add(pidData)
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
                    category = pidInfo.category.toPidCategory(), // DIUBAH: Konversi String ke Enum
                    uiType = pidInfo.ui_type,
                    subCategory = subCategory,
                    threshold = pidInfo.threshold?.let {
                        Threshold(min = it.min, max = it.max)
                    },
                    addressOffset = pidInfo.address_offset,
                    isSuzukiSpecific = pidInfo.suzuki_specific ?: true
                )

                val key = "${pidInfo.mode}_${pidInfo.pid.removePrefix("0x")}"
                pidMap[key] = pidData

                if (PidMapping.isDashboardPid(pidInfo.mode, pidInfo.pid)) {
                    dashboardPidsList.add(pidData)
                }
            }

            _pidDataMap.value = pidMap
            _dashboardPids.value = dashboardPidsList
            updateCategorizedPids()
            // _categorizedPids.value = categorized // DIHAPUS: Baris ini tidak perlu dan menyebabkan error

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
                lastUpdated = System.currentTimeMillis(),
                hasData = true // DIUBAH: Tandai bahwa data sudah ada
            )
            currentMap[pid] = updatedPid
            checkAndCreateAlert(updatedPid)
        }
        _pidDataMap.value = currentMap
        updateCategorizedPids()
        updateDashboardPids() // DIUBAH: Pastikan dashboard juga terupdate
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
                currentAlerts.add(0, alert)

                if (currentAlerts.size > 50) {
                    currentAlerts.removeAt(currentAlerts.size - 1)
                }

                _alerts.value = currentAlerts
            }
        }
    }

    private fun updateCategorizedPids() {
        val allPids = _pidDataMap.value.values.toList() // DIUBAH: Ambil values sebagai list
        _categorizedPids.value = allPids.groupBy { it.category } // DIUBAH: Logika dipermudah
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
        updateCategorizedPids() // DIUBAH: Pastikan data di-update di semua list
    }

    fun resetThresholds() {
        loadPidConfiguration()
    }

    fun updatePidDataMap(newData: Map<String, PidData>) {
        _pidDataMap.value = newData
        updateCategorizedPids()
        updateDashboardPids()
    }

    fun updateDashboardPids() {
        _dashboardPids.value = _pidDataMap.value.values.filter { pidData ->
            PidMapping.isDashboardPid(pidData.mode, pidData.pid)
        }
    }
}

// Data classes for JSON parsing (tidak diubah)
data class ThresholdsData(
    val metadata: MetadataInfo,
    val standard_obdii_pids: List<PidInfo>,
    val suzuki_custom_pids: List<PidInfo>,
    val suzuki_d13a_02_pids: List<PidInfo>
)

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
    val category: String, // Tetap String karena dari JSON
    val ui_type: String,
    val threshold: ThresholdInfo? = null,
    val address_offset: Int? = null,
    val suzuki_specific: Boolean? = null
)

data class ThresholdInfo(
    val min: Double? = null,
    val max: Double? = null
)

// Harusnya tidak ada di sini, tapi untuk sementara biarkan
data class ConfigData(
    val dashboard_highlight: List<String>,
    val categories: Map<String, List<String>>,
    val ai_analysis_questions: List<AiQuestion>
)