package com.example.covy.UiChauffeur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covy.models.DashboardUiState
import com.example.covy.models.GroupOpportunity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulation de l'appel API
            val mockOpportunity = GroupOpportunity(
                price = 6000,
                seats = 4,
                pricePerSeat = 1500,
                route = "Riviera 2 > St-Jean > Angré > Campus Bingerville",
                estimatedTime = "35 min estimées"
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    opportunity = mockOpportunity
                )
            }
        }
    }

    // Gestion du bouton On/Off (En ligne / Hors ligne)
    fun onToggleOnlineStatus(isOnline: Boolean) {
        _uiState.update { it.copy(isOnline = isOnline) }
        // Logique métier : envoyer le statut au serveur
    }

    fun onViewGroupRideClick() {
        // Logique métier : Naviguer vers l'écran de la demande groupée
        println("Navigation vers la demande groupée")
    }
}