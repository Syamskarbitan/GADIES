package com.gadies.suzuki.data.model

data class ThresholdsData(
    val standard_obdii_pids: List<PidInfo>,
    val suzuki_d13a_02_pids: List<PidInfo>
)

data class PidInfo(
    val mode: String,
    val pid: String,
    val name: String,
    val unit: String,
    val formula: String,
    val bytes: Int,
    val category: PidCategory,
    val ui_type: String,
    val threshold: ThresholdInfo? = null,
    val address_offset: Int? = null,
    val suzuki_specific: Boolean? = false
)

data class ThresholdInfo(
    val min: Double? = null,
    val max: Double? = null,
    val normal: DoubleRange? = null,
    val caution: DoubleRange? = null,
    val danger: DoubleRange? = null
)
