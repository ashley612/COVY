package com.example.covy.model

data class Vehicule(
    val carModel: String,
    val carYear: String,
    val carColor: String,
    val licensePlate: String,
    val driverLicenseFile: String? = null,
    val insuranceFile: String? = null,
    val technicalInspectionFile: String? = null
)