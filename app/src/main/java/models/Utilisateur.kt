

package com.example.covy.model

data class User(
    val id: String = "",
    val firstName: String,
    val lastName: String,
    val phone: String,
    val role: Role = Role.CHAUFFEUR
)
