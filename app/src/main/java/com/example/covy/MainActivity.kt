package com.example.covy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.covy.UiChauffeur.DriverProfileSelectionScreen
import com.example.covy.UiChauffeur.EcranInscriptionChauffeur
import com.example.covy.UiChauffeur.LoginScreen
import com.example.covy.UiChauffeur.ResetPasswordAccountScreen
import com.example.covy.UiChauffeur.RouteBienvenue
import com.example.covy.UiChauffeur.RouteConnexion
import com.example.covy.UiChauffeur.RouteInscription
import com.example.covy.UiChauffeur.RouteProfilChauffeur
import com.example.covy.UiChauffeur.RouteRecuperation
import com.example.covy.UiChauffeur.RouteVehicule
import com.example.covy.UiChauffeur.RouteVerification
import com.example.covy.UiChauffeur.VehicleAndDocumentsScreen
import com.example.covy.UiChauffeur.VerifyNumberOtpScreen
import com.example.covy.UiChauffeur.WelcomeCampusScreen
import com.example.covy.ui.theme.CovyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CovyTheme {
                CovyApp()
            }
        }
    }
}

@Composable
fun CovyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteBienvenue
    ) {

        // 1. Écran de bienvenue
        composable<RouteBienvenue> {
            WelcomeCampusScreen(
                onCreateAccountClick = {
                    navController.navigate(RouteProfilChauffeur)
                },
                onLoginClick = {
                    navController.navigate(RouteConnexion)
                }
            )
        }

        // 2. Écran de connexion
        composable<RouteConnexion> {
            LoginScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLoginClick = { phone, password ->
                    // TODO: logique de connexion plus tard
                },
                onForgotPasswordClick = {
                    navController.navigate(RouteRecuperation)
                },
                onRegisterClick = {
                    navController.navigate(RouteProfilChauffeur)
                }
            )
        }

        // 3. Écran de récupération
        composable<RouteRecuperation> {
            ResetPasswordAccountScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSendSmsCodeClick = { phone ->
                    navController.navigate(RouteVerification)
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Écran de vérification OTP
        composable<RouteVerification> {
            VerifyNumberOtpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onVerifyCodeClick = { code ->
                    // TODO: logique de vérification plus tard
                },
                onResendCodeClick = {
                    // TODO: renvoyer le code
                },
                onEditNumberClick = {
                    navController.popBackStack()
                }
            )
        }

        // 5. Écran de profil chauffeur
        composable<RouteProfilChauffeur> {
            DriverProfileSelectionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterAsDriverClick = {
                    navController.navigate(RouteInscription)
                },
                onLoginClick = {
                    navController.navigate(RouteConnexion)
                }
            )
        }

        // 6. Écran d'inscription chauffeur
        composable<RouteInscription> {
            EcranInscriptionChauffeur(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onContinueToVehicle = {
                    navController.navigate(RouteVehicule)
                }
            )
        }

        // 7. Écran véhicule et documents
        composable<RouteVehicule> {
            VehicleAndDocumentsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSubmitForReview = {
                    // TODO: envoyer au backend plus tard
                }
            )
        }
    }
}