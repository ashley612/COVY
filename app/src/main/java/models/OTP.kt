package com.example.covy.model

data class OtpState(
    val phoneNumber: String = "",
    val code: String = "",
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
    val errorMessage: String? = null
)