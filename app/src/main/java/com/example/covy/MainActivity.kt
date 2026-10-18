
package com.example.covy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

import com.example.covy.UiChauffeur.DriverProfileSelectionScreen
import com.example.covy.UiChauffeur.EcranInscriptionChauffeur
import com.example.covy.UiChauffeur.LoginScreen
import com.example.covy.UiChauffeur.ProfileChoiceScreen
import com.example.covy.UiChauffeur.ResetPasswordAccountScreen
import com.example.covy.UiChauffeur.RouteChoixProfil
import com.example.covy.UiChauffeur.RouteConnexion
import com.example.covy.UiChauffeur.RouteInscription
import com.example.covy.UiChauffeur.RouteProfilChauffeur
import com.example.covy.UiChauffeur.RouteRecuperation
import com.example.covy.UiChauffeur.RouteVehicule
import com.example.covy.UiChauffeur.RouteVerification
import com.example.covy.UiChauffeur.VehicleAndDocumentsScreen
import com.example.covy.UiChauffeur.VerifyNumberOtpScreen
import com.example.covy.UiChauffeur.RouteBienvenue
import com.example.covy.UiChauffeur.WelcomeCampusScreen
import com.example.covy.UiChauffeur.RoutePassagerAccueil

import com.example.covy.ui.components.CovyNotification
import com.example.covy.ui.components.PassengerTab
import com.example.covy.ui.screens.MyTripsScreen
import com.example.covy.ui.screens.NotificationsScreen
import com.example.covy.ui.screens.PassengerHomeScreen
import com.example.covy.ui.screens.PassengerProfile
import com.example.covy.ui.screens.ProfileScreen
import com.example.covy.ui.screens.ProfileRow
import com.example.covy.ui.screens.SearchTripScreen
import com.example.covy.ui.screens.TripResultsScreen
import com.example.covy.ui.screens.TripOffer
import com.example.covy.ui.screens.TripDetail
import com.example.covy.ui.screens.TripDetailScreen
import com.example.covy.ui.screens.PaymentScreen
import com.example.covy.ui.screens.PaymentMethod
import com.example.covy.ui.screens.TripTicket
import com.example.covy.ui.screens.TicketScreen
import com.example.covy.ui.screens.LiveTracking
import com.example.covy.ui.screens.TrackingScreen
import com.example.covy.ui.theme.CovyTheme

// Routes des écrans passager
@Serializable
data object RouteMesTrajets

@Serializable
data object RouteNotifications

@Serializable
data object RouteProfilPassager

@Serializable
data object RouteRechercheTrajet

@Serializable
data object RouteResultatsTrajet

@Serializable
data object RouteDetailTrajet

@Serializable
data object RoutePaiement

@Serializable
data object RouteBillet

@Serializable
data object RouteSuivi

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

    // Valeurs conservées pendant la navigation
    var departure by remember { mutableStateOf("St-Jean, Cocody") }
    var destination by remember { mutableStateOf("Campus Bingerville") }

    var selectedOffer by remember {
        mutableStateOf(
            TripOffer(
                id = "TR001",
                departureTime = "07:37",
                arrivalTime = "08:05",
                price = "1 500 FCFA",
                routeLabel = "Cocody → Bingerville",
                driverName = "Karim Kouassi",
                seatsAvailable = 3,
                recommended = true
            )
        )
    }

    val sampleOffers = listOf(
        selectedOffer,
        TripOffer(
            id = "TR002",
            departureTime = "07:50",
            arrivalTime = "08:20",
            price = "1 200 FCFA",
            routeLabel = "Cocody → Bingerville",
            driverName = "Aminata Koné",
            seatsAvailable = 2,
            recommended = false
        )
    )

    val tripDetail = TripDetail(
        dateLabel = "Vendredi 9 octobre 2026",
        campusName = "Bingerville",
        pickupName = departure,
        pickupHint = "Devant le point de rencontre",
        departureTime = selectedOffer.departureTime,
        dropoffName = destination,
        dropoffHint = "Portail principal",
        arrivalTime = selectedOffer.arrivalTime,
        driverName = selectedOffer.driverName,
        vehicle = "Toyota Corolla blanche",
        plate = "AB 1234 CD",
        rating = "4,8",
        groupInfo = "Places disponibles : ${selectedOffer.seatsAvailable}",
        seatsLabel = "1 place",
        price = selectedOffer.price
    )

    val sampleTicket = TripTicket(
        reference = selectedOffer.id,
        dateLabel = "Vendredi 9 octobre 2026",
        pickupName = departure,
        pickupDetail = "Point de rencontre",
        pickupTime = selectedOffer.departureTime,
        dropName = destination,
        dropDetail = "Portail principal",
        dropTime = selectedOffer.arrivalTime,
        securityCode = "COVY-2026",
        driverName = selectedOffer.driverName,
        vehicle = "Toyota Corolla blanche",
        plate = "AB 1234 CD",
        rating = 4.8,
        paymentTitle = "Paiement à confirmer",
        paymentDetail = selectedOffer.price
    )

    val sampleTracking = LiveTracking(
        driverName = selectedOffer.driverName,
        vehicle = "Toyota Corolla blanche",
        plate = "AB 1234 CD",
        rating = 4.8,
        etaMinutes = 10,
        stopName = departure,
        stopDetail = "Point de rencontre",
        destinationName = destination,
        securityCode = "COVY-2026"
    )

    NavHost(
        navController = navController,
        startDestination = RouteChoixProfil
    ) {

        // ------------------------------------------------
        // CHOIX DU PROFIL
        // ------------------------------------------------
        composable<RouteChoixProfil> {
            ProfileChoiceScreen(
                onPassagerClick = {
                    navController.navigate(RoutePassagerAccueil)
                },
                onConducteurClick = {
                    navController.navigate(RouteBienvenue)
                }
            )
        }

        // ------------------------------------------------
        // ACCUEIL PASSAGER
        // ------------------------------------------------
        composable<RoutePassagerAccueil> {
            PassengerHomeScreen(
                userName = "Passager",
                upcomingTrip = null,
                onBack = {
                    navController.popBackStack()
                },
                onFindTrip = { from, to ->
                    departure = from
                    destination = to
                    navController.navigate(RouteRechercheTrajet)
                },
                onOpenTicket = {
                    navController.navigate(RouteBillet)
                },
                onTabSelected = { tab ->
                    navigatePassengerTab(navController, tab)
                }
            )
        }

        // ------------------------------------------------
        // RECHERCHE D'UN TRAJET
        // ------------------------------------------------
        composable<RouteRechercheTrajet> {
            SearchTripScreen(
                initialDeparture = departure,
                initialDestination = destination,
                onBack = {
                    navController.popBackStack()
                },
                onSearch = { from, to, _, _, _, _ ->
                    departure = from
                    destination = to
                    navController.navigate(RouteResultatsTrajet)
                }
            )
        }

        // ------------------------------------------------
        // RÉSULTATS DE RECHERCHE
        // ------------------------------------------------
        composable<RouteResultatsTrajet> {
            TripResultsScreen(
                offers = sampleOffers,
                routeSummary = "$departure → $destination",
                dateSummary = "Date sélectionnée",
                onBack = {
                    navController.popBackStack()
                },
                onEditSearch = {
                    navController.navigate(RouteRechercheTrajet)
                },
                onChooseTrip = { offer ->
                    selectedOffer = offer
                    navController.navigate(RouteDetailTrajet)
                },
                onOpenDetail = { offer ->
                    selectedOffer = offer
                    navController.navigate(RouteDetailTrajet)
                }
            )
        }

        // ------------------------------------------------
        // DÉTAIL DU TRAJET
        // ------------------------------------------------
        composable<RouteDetailTrajet> {
            TripDetailScreen(
                detail = tripDetail,
                onBack = {
                    navController.popBackStack()
                },
                onReserve = {
                    navController.navigate(RoutePaiement)
                }
            )
        }

        // ------------------------------------------------
        // PAIEMENT
        // ------------------------------------------------
        composable<RoutePaiement> {
            PaymentScreen(
                tripLabel = "$departure → $destination",
                pricePerSeat = if (selectedOffer.id == "TR002") 1200 else 1500,
                passengers = 1,
                onBack = {
                    navController.popBackStack()
                },
                onConfirm = { method: PaymentMethod ->
                    // Démonstration uniquement :
                    // aucun paiement réel n'est effectué ici.
                    navController.navigate(RouteBillet)
                }
            )
        }

        // ------------------------------------------------
        // BILLET
        // ------------------------------------------------
        composable<RouteBillet> {
            TicketScreen(
                ticket = sampleTicket,
                onBack = {
                    navController.popBackStack()
                },
                onTrackDriver = {
                    navController.navigate(RouteSuivi)
                }
            )
        }

        // ------------------------------------------------
        // SUIVI DU CONDUCTEUR
        // ------------------------------------------------
        composable<RouteSuivi> {
            TrackingScreen(
                tracking = sampleTracking,
                onBack = {
                    navController.popBackStack()
                },
                onShareTrip = {
                    // À connecter au partage Android.
                },
                onAssistance = {
                    // À connecter à l'assistance.
                },
                onCallDriver = {
                    // À connecter à l'appel téléphonique.
                }
            )
        }

        // ------------------------------------------------
        // MES TRAJETS
        // ------------------------------------------------
        composable<RouteMesTrajets> {
            MyTripsScreen(
                upcoming = emptyList(),
                past = emptyList(),
                onBack = {
                    navController.popBackStack()
                },
                onViewTicket = {
                    navController.navigate(RouteBillet)
                },
                onManageReservation = {
                    navController.navigate(RouteDetailTrajet)
                },
                onViewReceipt = {
                    navController.navigate(RouteBillet)
                },
                onTabSelected = { tab ->
                    navigatePassengerTab(navController, tab)
                }
            )
        }

        // ------------------------------------------------
        // NOTIFICATIONS
        // ------------------------------------------------
        composable<RouteNotifications> {
            NotificationsScreen(
                notifications = listOf(
                    CovyNotification(
                        id = "N001",
                        icon = Icons.Outlined.ConfirmationNumber,
                        title = "Votre espace passager",
                        meta = "Bienvenue sur Covy",
                        body = "Retrouvez ici les informations de vos trajets.",
                        actionLabel = null,
                        unread = false
                    ),
                    CovyNotification(
                        id = "N002",
                        icon = Icons.Outlined.DirectionsCar,
                        title = "Préparez votre trajet",
                        meta = "Informations de démonstration",
                        body = "Recherchez un trajet pour découvrir les offres.",
                        actionLabel = "Rechercher",
                        unread = true
                    )
                ),
                onBack = {
                    navController.popBackStack()
                },
                onNotificationAction = {
                    navController.navigate(RouteRechercheTrajet)
                },
                onMarkAllRead = {
                    // À relier à l'état des notifications.
                },
                onTabSelected = { tab ->
                    navigatePassengerTab(navController, tab)
                }
            )
        }

        // ------------------------------------------------
        // PROFIL PASSAGER
        // ------------------------------------------------
        composable<RouteProfilPassager> {
            ProfileScreen(
                profile = PassengerProfile(
                    fullName = "Passager",
                    maskedPhone = "",
                    profileType = "Élève",
                    schoolRows = emptyList(),
                    contactRows = emptyList()
                ),
                onBack = {
                    navController.popBackStack()
                },
                onEditInfo = {
                    // À relier à l'écran de modification du profil.
                },
                onEditSchooling = {
                    // À relier à l'écran de scolarité.
                },
                onEditContacts = {
                    // À relier à l'écran des contacts.
                },
                onHelp = {
                    // À relier à l'assistance.
                },
                onLogout = {
                    navController.navigate(RouteChoixProfil) {
                        popUpTo<RouteChoixProfil> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onTabSelected = { tab ->
                    navigatePassengerTab(navController, tab)
                }
            )
        }

        // ------------------------------------------------
        // ÉCRANS CHAUFFEUR EXISTANTS
        // ------------------------------------------------
        composable<RouteBienvenue> {
            WelcomeCampusScreen(
                onCreateAccountClick = {
                    navController.navigate(RouteInscription)
                },
                onLoginClick = {
                    navController.navigate(RouteConnexion)
                }
            )
        }

        composable<RouteConnexion> {
            LoginScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLoginClick = { _, _ ->
                    // Connexion à relier au backend.
                },
                onForgotPasswordClick = {
                    navController.navigate(RouteRecuperation)
                },
                onRegisterClick = {
                    navController.navigate(RouteProfilChauffeur)
                }
            )
        }

        composable<RouteRecuperation> {
            ResetPasswordAccountScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSendSmsCodeClick = {
                    navController.navigate(RouteVerification)
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<RouteVerification> {
            VerifyNumberOtpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onVerifyCodeClick = {
                    // Vérification à relier au backend.
                },
                onResendCodeClick = {
                    // Renvoi du code à relier au backend.
                },
                onEditNumberClick = {
                    navController.popBackStack()
                }
            )
        }

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

        composable<RouteVehicule> {
            VehicleAndDocumentsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSubmitForReview = {
                    // Envoi au backend à implémenter.
                }
            )
        }
    }
}

// Navigation entre les quatre onglets passager
private fun navigatePassengerTab(
    navController: NavHostController,
    tab: PassengerTab
) {
    when (tab) {
        PassengerTab.HOME -> navController.navigate(RoutePassagerAccueil) {
            popUpTo(RoutePassagerAccueil) {
                inclusive = false
            }
            launchSingleTop = true
        }

        PassengerTab.TRIPS -> navController.navigate(RouteMesTrajets) {
            launchSingleTop = true
        }

        PassengerTab.ALERTS -> navController.navigate(RouteNotifications) {
            launchSingleTop = true
        }

        PassengerTab.PROFILE -> navController.navigate(RouteProfilPassager) {
            launchSingleTop = true
        }
    }
}
