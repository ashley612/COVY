package com.example.covy.model

data class CourseHistory(
    val id: String,
    val depart: String,
    val arrivee: String,
    val heure: String,
    val details: String,
    val prix: Int
)

data class WithdrawalOption(
    val name: String,
    val iconRes: Int? = null // Optionnel pour ajouter le logo Wave/OM plus tard
)

data class WalletUiState(
    val isLoading: Boolean = false,
    val soldeDisponible: Int = 148500,
    val revenusDuJour: Int = 32500,
    val selectedWithdrawalMethod: String = "Wave",
    val withdrawalOptions: List<String> = listOf("Wave", "Orange Money"),
    val coursesDuJour: List<CourseHistory> = emptyList(),
    val totalCoursesAujourdhui: Int = 0
)