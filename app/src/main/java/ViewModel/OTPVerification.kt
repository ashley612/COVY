package com.example.covy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.covy.model.OtpState

class OTPVerification : ViewModel() {
    var state by mutableStateOf(OtpState())
        private set

    fun onPhoneNumberChange(value: String) {
        state = state.copy(phoneNumber = value)
    }

    fun onCodeChange(value: String) {
        // On accepte seulement les chiffres, max 6
        if (value.all { it.isDigit() } && value.length <= 6) {
            state = state.copy(code = value, errorMessage = null)
        }
    }

    fun verifierCode(onSuccess: () -> Unit) {
        if (state.code.length != 6) {
            state = state.copy(errorMessage = "Le code doit contenir 6 chiffres")
            return
        }
        state = state.copy(isLoading = true, errorMessage = null)

        // TODO: appel API plus tard (POST /api/otp/verify)
        // Simulation : tout code à 6 chiffres est accepté
        state = state.copy(isLoading = false, isValid = true)
        onSuccess()
    }

    fun renvoyerCode() {
        // TODO: appel API plus tard (POST /api/otp/send)
        state = state.copy(errorMessage = null)
    }
}