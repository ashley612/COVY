package com.example.covy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.covy.model.DocumentType

class InscriptionViewModel : ViewModel() {

    // ====== Champs du formulaire ======
    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var phone by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var selectedDocType by mutableStateOf(DocumentType.CNI)
        private set

    var docNumber by mutableStateOf("")
        private set

    var licenseNumber by mutableStateOf("")
        private set

    var licenseCategory by mutableStateOf("B (Véhicules légers / Berlines)")
        private set

    // ====== État de l'UI ======
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // ====== Actions sur les champs ======
    fun onFirstNameChange(value: String) {
        firstName = value
        errorMessage = null
    }

    fun onLastNameChange(value: String) {
        lastName = value
        errorMessage = null
    }

    fun onPhoneChange(value: String) {
        phone = value
        errorMessage = null
    }

    fun onPasswordChange(value: String) {
        password = value
        errorMessage = null
    }

    fun onDocTypeChange(value: DocumentType) {
        selectedDocType = value
        errorMessage = null
    }

    fun onDocNumberChange(value: String) {
        docNumber = value
        errorMessage = null
    }

    fun onLicenseNumberChange(value: String) {
        licenseNumber = value
        errorMessage = null
    }

    fun onLicenseCategoryChange(value: String) {
        licenseCategory = value
        errorMessage = null
    }

    // ====== Action : soumettre le formulaire ======
    fun continuerVersVehicule(onSuccess: () -> Unit) {
        // Validation
        if (firstName.isBlank()) {
            errorMessage = "Le prénom est obligatoire"
            return
        }
        if (lastName.isBlank()) {
            errorMessage = "Le nom est obligatoire"
            return
        }
        if (phone.isBlank()) {
            errorMessage = "Le téléphone est obligatoire"
            return
        }
        if (password.length < 6) {
            errorMessage = "Le mot de passe doit contenir au moins 6 caractères"
            return
        }
        if (docNumber.isBlank()) {
            errorMessage = "Le numéro du document est obligatoire"
            return
        }
        if (licenseNumber.isBlank()) {
            errorMessage = "Le numéro du permis est obligatoire"
            return
        }

        isLoading = true
        errorMessage = null

        // TODO: appel API plus tard (POST /driver/register)
        // Pour l'instant, simulation
        isLoading = false
        onSuccess()
    }
}