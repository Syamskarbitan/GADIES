package com.gadies.suzuki.data.model

data class Alert(
    val id: String,
    val message: String,
    val level: AlertLevel,
    val timestamp: Long = System.currentTimeMillis()
)
