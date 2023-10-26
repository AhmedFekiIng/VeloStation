package com.example.stationsvelos.api

data class Station(
    val number: Int,
    val contractName: String,
    val name: String,
    val address: String,
    val position: Position,
    val banking: Boolean,
    val bonus: Boolean,
    val status: String,
    val lastUpdate: String,
    val connected: Boolean,
    val overflow: Boolean,
    val shape: Any?,
    val totalStands: StandAvailability,
    val mainStands: StandAvailability,
    val overflowStands: Any?
)

data class Position(
    val latitude: Double,
    val longitude: Double
)

data class StandAvailability(
    val availabilities: BikeAvailability,
    val capacity: Int
)

data class BikeAvailability(
    val bikes: Int,
    val stands: Int,
    val mechanicalBikes: Int,
    val electricalBikes: Int,
    val electricalInternalBatteryBikes: Int,
    val electricalRemovableBatteryBikes: Int
)