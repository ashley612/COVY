package com.example.covy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DriverProfileViewModel : ViewModel() {

    // État : le chauffeur a-t-il confirmé son choix ?
    var roleConfirme by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Action : confirmer le choix du profil chauffeur
    fun confirmerProfilChauffeur(onSuccess: () -> Unit) {
        isLoading = true
        errorMessage = null

        // TODO: appel API plus tard (POST /driver/confirm-role)
        // Pour l'instant, simulation
        isLoading = false
        roleConfirme = true
        onSuccess()
    }

    // Action : se connecter si déjà chauffeur
    fun seConnecter(onNavigate: () -> Unit) {
        onNavigate()
    }
}