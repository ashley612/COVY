package com.example.covy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covy.model.CourseHistory
import com.example.covy.model.WalletUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulation d'un appel réseau
            // delay(1000)

            val mockCourses = listOf(
                CourseHistory("1", "Riviera 2", "Campus Bingerville", "07:30", "Groupe de 4", 10000),
                CourseHistory("2", "Bingerville", "Angré", "09:10", "2 passagers", 5000),
                CourseHistory("3", "St-Jean", "Campus Bingerville", "11:20", "3 passagers", 7500),
                CourseHistory("4", "Angré", "Campus Bingerville", "14:15", "2 passagers", 5000),
                CourseHistory("5", "Bingerville", "Riviera 2", "16:30", "2 passagers", 5000)
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    coursesDuJour = mockCourses,
                    totalCoursesAujourdhui = mockCourses.size
                )
            }
        }
    }

    fun onWithdrawalMethodSelected(method: String) {
        _uiState.update { it.copy(selectedWithdrawalMethod = method) }
    }

    fun effectuerRetrait() {
        // Logique métier pour le retrait
        println("Retrait initié via ${_uiState.value.selectedWithdrawalMethod}")
    }
}