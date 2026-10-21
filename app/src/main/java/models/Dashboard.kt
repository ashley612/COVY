package com.example.covy.models

data class DriverStats(
    val dailyRevenue: Int,
    val completedRides: Int
)

data class GroupOpportunity(
    val price: Int,
    val seats: Int,
    val pricePerSeat: Int,
    val route: String,
    val estimatedTime: String
)

data class DashboardUiState(
    val isLoading: Boolean = false,
    val driverName: String = "Karim",
    val isOnline: Boolean = true,
    val stats: DriverStats = DriverStats(32500, 5),
    val opportunity: GroupOpportunity? = null,
    val newRequestsCount: Int = 1,
    val activeZones: String = "Riviera 2 - St-Jean - Angré - Bingerville"
)