package com.example.covy.models

data class DriverDocument(
    val title: String,
    val subtitle: String,
    val status: String = "Vérifiée"
)

data class DriverProfile(
    val name: String,
    val carModel: String,
    val rating: Double,
    val plateNumber: String,
    val fullDescription: String
)

data class SubscriptionInfo(
    val planName: String,
    val price: Int,
    val renewalDate: String
)

data class AccountUiState(
    val isLoading: Boolean = false,
    val profile: DriverProfile? = null,
    val documents: List<DriverDocument> = emptyList(),
    val subscription: SubscriptionInfo? = null
)