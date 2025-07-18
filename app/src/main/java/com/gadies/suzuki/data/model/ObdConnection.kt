package com.gadies.suzuki.data.model

enum class ConnectionType {
    BLUETOOTH,
    WIFI,
    NONE
}

enum class ConnectionStatus {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR
}

data class ObdDevice(
    val name: String,
    val address: String,
    val type: ConnectionType,
    val isAvailable: Boolean = true
)

data class ConnectionState(
    val status: ConnectionStatus = ConnectionStatus.DISCONNECTED,
    val device: ObdDevice? = null,
    val errorMessage: String? = null,
    val lastConnected: Long = 0L
)

data class ObdCommand(
    val command: String,
    val expectedResponseLength: Int = 0,
    val timeout: Long = 5000L
)

data class ObdResponse(
    val command: String,
    val rawResponse: String,
    val isSuccess: Boolean,
    val errorMessage: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
