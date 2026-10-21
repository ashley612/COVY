package com.example.covy.models

data class PassengerDetail(
    val name: String,
    val info: String
)

data class StopInfo(
    val id: String,
    val locationName: String,
    val passengers: List<PassengerDetail>,
    val time: String,
    val isDestination: Boolean = false
)

data class GroupRideUiState(
    val isLoading: Boolean = false,
    val title: String = "Un groupe vous attend",
    val fareAmount: Int = 6000,
    val seats: Int = 4,
    val pricePerSeat: Int = 2500,
    val estimatedTime: String = "35 min estimées",
    val totalPassengers: Int = 4,
    val stops: List<StopInfo> = emptyList(),
    val terminusInfo: String = "Terminus commun : campus Bingerville."
)