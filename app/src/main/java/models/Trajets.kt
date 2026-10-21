package com.example.covy.models

data class NavigationInfo(
    val distanceAndStreet: String,
    val instruction: String
)

data class PassengerInfo(
    val name: String,
    val pickupDetails: String,
    val validationCode: String
)

data class ActiveRideUiState(
    val isLoading: Boolean = false,
    val nextStopName: String = "St-Jean",
    val navigationInfo: NavigationInfo = NavigationInfo("DANS 200 M · BD DE FRANCE", "Tournez à droite"),
    val passengerInfo: PassengerInfo = PassengerInfo("Jean K.", "Devant la pharmacie St-Jean, Cocody", "4 8 2 1"),
    val totalPassengers: Int = 4,
    val destination: String = "campus Bingerville",
    val estimatedArrival: String = "08:05 estimée"
)