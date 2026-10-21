package com.example.covy.UiChauffeur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covy.models.ActiveRideUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActiveRideViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ActiveRideUiState())
    val uiState: StateFlow<ActiveRideUiState> = _uiState.asStateFlow()

    init {
        loadRideData()
    }

    private fun loadRideData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulation d'un appel réseau pour récupérer la course en cours
            // delay(500)

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onValidatePickupClick() {
        // Logique métier : Valider la montée du passager (envoi API)
        println("Passager validé avec le code : ${_uiState.value.passengerInfo.validationCode}")
    }

    fun onSosClick() {
        // Logique métier : Déclencher l'alerte SOS
        println("URGENCE : Bouton SOS cliqué")
    }

    fun onViewPassengersClick() {
        // Logique métier : Naviguer vers la liste des passagers
        println("Voir les passagers")
    }
}