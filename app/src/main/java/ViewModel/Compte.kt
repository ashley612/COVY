package com.example.covy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covy.models.AccountUiState
import com.example.covy.models.DriverDocument
import com.example.covy.models.DriverProfile
import com.example.covy.models.SubscriptionInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    init {
        loadAccountData()
    }

    private fun loadAccountData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulation de la récupération des données (API / Base de données)
            val mockProfile = DriverProfile(
                name = "Karim Kouassi",
                carModel = "Toyota Corolla blanche",
                rating = 4.8,
                plateNumber = "AB 1234 CD",
                fullDescription = "Toyota Corolla 2021 · Blanche · AB 1234 CD"
            )

            val mockDocuments = listOf(
                DriverDocument("Permis de conduire", "Catégorie B"),
                DriverDocument("Assurance", "Document du véhicule"),
                DriverDocument("Visite technique", "Justificatif enregistré")
            )

            val mockSubscription = SubscriptionInfo(
                planName = "Pass Campus Mensuel",
                price = 25000,
                renewalDate = "15 octobre 2026"
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    profile = mockProfile,
                    documents = mockDocuments,
                    subscription = mockSubscription
                )
            }
        }
    }

    fun onEditProfileClick() {
        // Logique métier pour éditer le profil
    }

    fun onManageSubscriptionClick() {
        // Logique métier pour gérer l'abonnement
    }

    fun onLogoutClick() {
        // Logique métier pour la déconnexion
    }
}