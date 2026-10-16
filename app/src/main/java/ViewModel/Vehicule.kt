package com.example.covy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VehiculeViewModel : ViewModel() {

    // ====== Champs du véhicule ======
    var carModel by mutableStateOf("")
        private set

    var carYear by mutableStateOf("")
        private set

    var carColor by mutableStateOf("")
        private set

    var licensePlate by mutableStateOf("")
        private set

    // ====== Fichiers (chemins locaux pour l'instant) ======
    var driverLicenseFile by mutableStateOf<String?>(null)
        private set

    var insuranceFile by mutableStateOf<String?>(null)
        private set

    var technicalInspectionFile by mutableStateOf<String?>(null)
        private set

    // ====== État de l'UI ======
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // ====== Actions sur les champs ======
    fun onCarModelChange(value: String) {
        carModel = value
        errorMessage = null
    }

    fun onCarYearChange(value: String) {
        carYear = value
        errorMessage = null
    }

    fun onCarColorChange(value: String) {
        carColor = value
        errorMessage = null
    }

    fun onLicensePlateChange(value: String) {
        licensePlate = value
        errorMessage = null
    }

    fun onDriverLicenseFileChange(path: String) {
        driverLicenseFile = path
    }

    fun onInsuranceFileChange(path: String) {
        insuranceFile = path
    }

    fun onTechnicalInspectionFileChange(path: String) {
        technicalInspectionFile = path
    }

    // ====== Action : soumettre au backend ======
    fun envoyerPourExamen(onSuccess: () -> Unit) {
        // Validation
        if (carModel.isBlank()) {
            errorMessage = "La marque et le modèle sont obligatoires"
            return
        }
        if (carYear.isBlank()) {
            errorMessage = "L'année est obligatoire"
            return
        }
        if (carColor.isBlank()) {
            errorMessage = "La couleur est obligatoire"
            return
        }
        if (licensePlate.isBlank()) {
            errorMessage = "L'immatriculation est obligatoire"
            return
        }

        isLoading = true
        errorMessage = null

        // TODO: appel API plus tard (POST /driver/vehicle)
        // Pour l'instant, simulation
        isLoading = false
        onSuccess()
    }
}