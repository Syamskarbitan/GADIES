package com.gadies.suzuki.data.model

enum class ConnectionType {
    BLUETOOTH, WIFI
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
    val rssi: Int? = null // For Bluetooth devices
)

data class ConnectionState(
    val status: ConnectionStatus = ConnectionStatus.DISCONNECTED,
    val device: ObdDevice? = null,
    val errorMessage: String? = null,
    val lastConnected: Long? = null
)
