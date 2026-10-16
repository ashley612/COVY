
package com.example.covy.model

data class InformationsChauffeur(
    val user: User,
    val document: Document,
    val licenseNumber: String,
    val licenseCategory: String,
    val vehicule: Vehicule? = null,
    val estVerifie: Boolean = false
)
