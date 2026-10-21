package com.example.covy.UiChauffeur


import kotlinx.serialization.Serializable

// =========================================================================================
// 1. DESTINATIONS TYPE-SAFE (kotlinx.serialization)
// =========================================================================================

// --- Authentification & onboarding chauffeur ---
@Serializable object RouteBienvenue          // WelcomeCampusScreen (01 / 30)
@Serializable object RouteConnexion          // LoginScreen (02 / 30)
@Serializable object RouteRecuperation       // ResetPasswordAccountScreen (03 / 30)
@Serializable object RouteVerification       // VerifyNumberOtpScreen (04 / 30)
@Serializable object RouteProfilChauffeur    // DriverProfileSelectionScreen (03 / 30)
@Serializable object RouteInscription        // EcranInscriptionChauffeur (12 / 30)
@Serializable object RouteVehicule           // VehicleAndDocumentsScreen (13 / 30)



// =========================================================================================
// 2. ÉCRANS PRINCIPAUX (Ajoutés ici)
// =========================================================================================
@Serializable object RouteDashboard       // L'écran d'accueil principal du chauffeur
@Serializable object RouteDemandeGroupee  // L'écran d'acceptation de la course groupée
@Serializable object RouteCourseEnCours   // L'écran de navigation pendant la course
@Serializable object RoutePortefeuille    // L'écran des revenus
@Serializable object RouteCompte          // L'écran du profil chauffeur


