package com.example.covy.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covy.models.GroupRideUiState
import com.example.covy.models.PassengerDetail
import com.example.covy.models.StopInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroupRideViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GroupRideUiState())
    val uiState: StateFlow<GroupRideUiState> = _uiState.asStateFlow()

    init {
        loadRideRequest()
    }

    private fun loadRideRequest() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulation de la récupération des données depuis l'API
            val mockStops = listOf(
                StopInfo(
                    id = "1",
                    locationName = "Riviera 2",
                    passengers = listOf(PassengerDetail("Kevin B.", "")),
                    time = "07:30"
                ),
                StopInfo(
                    id = "2",
                    locationName = "St-Jean",
                    passengers = listOf(PassengerDetail("Jean K. et Ashley M.", "Pharmacie")),
                    time = "07:37"
                ),
                StopInfo(
                    id = "3",
                    locationName = "Angré 8e Tranche",
                    passengers = listOf(PassengerDetail("Mariam T.", "Station Petro Ivoire")),
                    time = "07:44"
                ),
                StopInfo(
                    id = "4",
                    locationName = "Campus Bingerville",
                    passengers = listOf(PassengerDetail("", "Portail principal")),
                    time = "08:05",
                    isDestination = true
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    stops = mockStops
                )
            }
        }
    }

    fun onAcceptRideClick() {
        // Logique métier : Accepter la course (Appel API)
        println("Course acceptée pour ${_uiState.value.fareAmount} FCFA")
    }

    fun onDeclineRideClick() {
        // Logique métier : Refuser la course
        println("Course refusée")
    }
}