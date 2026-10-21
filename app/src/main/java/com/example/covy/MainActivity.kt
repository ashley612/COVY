package com.example.covy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.covy.UiChauffeur.AccountScreen
import com.example.covy.UiChauffeur.ActiveRideScreen
import com.example.covy.UiChauffeur.DashboardChauffeur
import com.example.covy.UiChauffeur.DriverProfileSelectionScreen
import com.example.covy.UiChauffeur.EcranInscriptionChauffeur
import com.example.covy.UiChauffeur.GroupRideScreen
import com.example.covy.UiChauffeur.LoginScreen
import com.example.covy.UiChauffeur.ResetPasswordAccountScreen
import com.example.covy.UiChauffeur.RouteBienvenue
import com.example.covy.UiChauffeur.RouteCompte
import com.example.covy.UiChauffeur.RouteConnexion
import com.example.covy.UiChauffeur.RouteCourseEnCours
import com.example.covy.UiChauffeur.RouteDashboard
import com.example.covy.UiChauffeur.RouteDemandeGroupee
import com.example.covy.UiChauffeur.RouteInscription
import com.example.covy.UiChauffeur.RoutePortefeuille
import com.example.covy.UiChauffeur.RouteProfilChauffeur
import com.example.covy.UiChauffeur.RouteRecuperation
import com.example.covy.UiChauffeur.RouteVehicule
import com.example.covy.UiChauffeur.RouteVerification
import com.example.covy.UiChauffeur.VehicleAndDocumentsScreen
import com.example.covy.UiChauffeur.VerifyNumberOtpScreen
import com.example.covy.UiChauffeur.WelcomeCampusScreen
import com.example.covy.ui.theme.CovyColors.CovyDarkGreen
import com.example.covy.ui.theme.CovyColors.CovyLightGreen
import com.example.covy.ui.theme.CovyColors.TextTertiary
import com.example.covy.ui.theme.CovyTheme
import com.example.covy.ui.wallet.WalletScreen
import kotlinx.serialization.Serializable
import androidx.compose.ui.graphics.Color
import com.example.covy.ui.theme.CovyColors.SurfaceWhite

// Destination typée pour le compte chauffeur
@Serializable
object RouteCompteChauffeur

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
                    // TODO : Ajouter la logique de connexion
                },
                onForgotPasswordClick = {
                    navController.navigate(RouteRecuperation)
                },
                onRegisterClick = {
                    navController.navigate(RouteProfilChauffeur)
                }
            )
        }

        // 3. Écran de récupération du compte
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
                    // TODO : Ajouter la logique de vérification
                },
                onResendCodeClick = {
                    // TODO : Ajouter la logique de renvoi du code
                },
                onEditNumberClick = {
                    navController.popBackStack()
                }
            )
        }

        // 5. Écran de sélection du profil chauffeur
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
                    // Redirection vers le compte chauffeur
                    navController.navigate(RouteCompteChauffeur)
                }
            )
        }


    }


}




@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RouteDashboard,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Tes écrans
            composable<RouteDashboard> {
                DashboardChauffeur(
                    onNavigateToGroupRide = { navController.navigate(RouteDemandeGroupee) }
                )
            }
            composable<RouteDemandeGroupee> {
                GroupRideScreen(
                    onBackClick = { navController.popBackStack() },
                    onAcceptRide = { navController.navigate(RouteCourseEnCours) }
                )
            }
            composable<RouteCourseEnCours> {
                ActiveRideScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable<RoutePortefeuille> {
                WalletScreen()
            }
            composable<RouteCompte> {
                AccountScreen(


                    onNavigateToDocumentDetail = { documentId ->
                        // Action quand on clique sur un document (ex: permis)
                        // navController.navigate("detail_document/$documentId")
                    },
                    onNavigateToSubscription = {
                        // Action quand on clique sur "Gérer ma formule"
                        // navController.navigate(RouteAbonnement)
                    },
                    onNavigateToSupport = {
                        // Action quand on clique sur "Aide et assistance"
                        // navController.navigate(RouteSupport)
                    },
                    onNavigateToAuth = {
                        // Action quand on clique sur "Se déconnecter"
                        navController.navigate(RouteConnexion) {
                            popUpTo(RouteBienvenue) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = {
                        // Action quand on clique sur la flèche de retour
                        navController.popBackStack()
                    }
                )
            }


        }
    }
}


// =========================================================================
// BARRE DE NAVIGATION
// =========================================================================
@Composable
fun AppBottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (Any) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceWhite,
        tonalElevation = 8.dp
    ) {
        // 1. ACCUEIL (Utilisation de "contains" pour éviter l'erreur de version)
        val isDashboard = currentDestination?.route?.contains("RouteDashboard") == true
        NavigationBarItem(
            icon = {
                if (isDashboard) {
                    SelectedIconWrapper {
                        Icon(
                            Icons.Default.Home,
                            "Accueil",
                            tint = CovyDarkGreen
                        )
                    }
                } else {
                    Icon(Icons.Default.Home, "Accueil", modifier = Modifier.size(22.dp))
                }
            },
            label = {
                Text(
                    "Accueil",
                    fontSize = 10.sp,
                    fontWeight = if (isDashboard) FontWeight.Bold else FontWeight.Normal
                )
            },
            selected = isDashboard,
            onClick = { onNavigate(RouteDashboard) },
            colors = colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary,
                selectedTextColor = CovyDarkGreen
            )
        )

        // 2. COURSES
        val isCourses = currentDestination?.route?.contains("RouteCourseEnCours") == true
        NavigationBarItem(
            icon = {
                if (isCourses) {
                    SelectedIconWrapper {
                        Icon(
                            Icons.Default.DirectionsCar,
                            "Courses",
                            tint = CovyDarkGreen
                        )
                    }
                } else {
                    Icon(Icons.Default.DirectionsCar, "Courses", modifier = Modifier.size(22.dp))
                }
            },
            label = {
                Text(
                    "Courses",
                    fontSize = 10.sp,
                    fontWeight = if (isCourses) FontWeight.Bold else FontWeight.Normal
                )
            },
            selected = isCourses,
            onClick = { onNavigate(RouteCourseEnCours) },
            colors = colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary,
                selectedTextColor = CovyDarkGreen
            )
        )

        // 3. REVENUS
        val isWallet = currentDestination?.route?.contains("RoutePortefeuille") == true
        NavigationBarItem(
            icon = {
                if (isWallet) {
                    SelectedIconWrapper {
                        Icon(
                            Icons.Default.Wallet,
                            "Revenus",
                            tint = CovyDarkGreen
                        )
                    }
                } else {
                    Icon(Icons.Default.Wallet, "Revenus", modifier = Modifier.size(22.dp))
                }
            },
            label = {
                Text(
                    "Revenus",
                    fontSize = 10.sp,
                    fontWeight = if (isWallet) FontWeight.Bold else FontWeight.Normal
                )
            },
            selected = isWallet,
            onClick = { onNavigate(RoutePortefeuille) },
            colors = colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary,
                selectedTextColor = CovyDarkGreen
            )
        )

        // 4. COMPTE
        val isAccount = currentDestination?.route?.contains("RouteCompte") == true
        NavigationBarItem(
            icon = {
                if (isAccount) {
                    SelectedIconWrapper {
                        Icon(
                            Icons.Default.Person,
                            "Compte",
                            tint = CovyDarkGreen
                        )
                    }
                } else {
                    Icon(Icons.Default.Person, "Compte", modifier = Modifier.size(22.dp))
                }
            },
            label = {
                Text(
                    "Compte",
                    fontSize = 10.sp,
                    fontWeight = if (isAccount) FontWeight.Bold else FontWeight.Normal
                )
            },
            selected = isAccount,
            onClick = { onNavigate(RouteCompte) },
            colors = colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary,
                selectedTextColor = CovyDarkGreen
            )
        )
    }
}

@Composable
fun SelectedIconWrapper(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CovyLightGreen)
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        content()
    }
}
