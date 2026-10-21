package com.example.covy.UiChauffeur

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.models.DashboardUiState
import com.example.covy.ui.theme.*
import com.example.covy.ui.theme.CovyColors.BackgroundGray
import com.example.covy.ui.theme.CovyColors.CovyDarkGreen
import com.example.covy.ui.theme.CovyColors.CovyGreenText
import com.example.covy.ui.theme.CovyColors.CovyLightGreen
import com.example.covy.ui.theme.CovyColors.SurfaceWhite
import com.example.covy.ui.theme.CovyColors.TextPrimary
import com.example.covy.ui.theme.CovyColors.TextSecondary
import com.example.covy.ui.theme.CovyColors.TextTertiary
import com.example.covy.ui.theme.CovyColors.TextWhite
import com.example.covy.ui.theme.CovyColors.TextWhiteAlpha
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DashboardChauffeur(viewModel: DashboardViewModel = viewModel(),
                       onNavigateToGroupRide: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            CovyBottomNavigationBarDashboard()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CovyDarkGreen)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item { TopAppBarDashboard() }

                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "ESPACE CHAUFFEUR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CovyGreenText,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Bonjour ${uiState.driverName}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // Statut En ligne
                            StatusCard(
                                isOnline = uiState.isOnline,
                                onToggle = viewModel::onToggleOnlineStatus
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Statistiques
                            StatsRow(uiState)

                            Spacer(modifier = Modifier.height(16.dp))

                            // Opportunité groupée
                            uiState.opportunity?.let { opp ->
                                OpportunityCard(
                                    price = opp.price,
                                    seats = opp.seats,
                                    pricePerSeat = opp.pricePerSeat,
                                    route = opp.route,
                                    time = opp.estimatedTime
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Notifications et Zones
                            NotificationsCard(
                                newRequestsCount = uiState.newRequestsCount,
                                activeZones = uiState.activeZones
                            )
                        }
                    }
                }

                // Bouton fixe en bas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundGray)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Button(
                        onClick = onNavigateToGroupRide,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CovyDarkGreen)
                    ) {
                        Text(
                            text = "Voir la demande groupée",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBarDashboard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Retour",
            tint = TextSecondary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CovyLightGreen),
            contentAlignment = Alignment.Center
        ) {
            Text("C", color = CovyDarkGreen, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(8.dp))
        Text("Covy", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CovyDarkGreen)

        Spacer(modifier = Modifier.weight(1f))
        Text("26 / 30", fontSize = 14.sp, color = TextTertiary, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun StatusCard(isOnline: Boolean, onToggle: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Point vert (statut)
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(if (isOnline) CovyDarkGreen else TextTertiary, CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "En ligne · ${if (isOnline) "Disponible" else "Indisponible"}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = if (isOnline) "Prêt à recevoir des demandes" else "Vous ne recevez pas de demandes",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Switch(
                checked = isOnline,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = SwitchTrackGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = TextTertiary
                )
            )
        }
    }
}

@Composable
fun StatsRow(uiState: DashboardUiState) {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Carte Revenus
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Revenus du jour", fontSize = 12.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatter.format(uiState.stats.dailyRevenue),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text("FCFA", fontSize = 12.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            }
        }

        // Carte Courses
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Courses du jour", fontSize = 12.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = uiState.stats.completedRides.toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text("courses terminées", fontSize = 12.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun OpportunityCard(
    price: Int,
    seats: Int,
    pricePerSeat: Int,
    route: String,
    time: String
) {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CovyDarkGreen),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Badge
            Box(
                modifier = Modifier
                    .background(OpportunityBadgeBg, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Opportunité groupée",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = OpportunityBadgeText
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${formatter.format(price)} FCFA",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            Text(
                text = "$seats places × ${formatter.format(pricePerSeat)} FCFA",
                fontSize = 13.sp,
                color = TextWhiteAlpha,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = route,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            Text(
                text = time,
                fontSize = 12.sp,
                color = TextWhiteAlpha
            )
        }
    }
}

@Composable
fun NotificationsCard(newRequestsCount: Int, activeZones: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Item 1 : Demandes
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = IconGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Demandes de course", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("$newRequestsCount groupe à consulter", fontSize = 12.sp, color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Item 2 : Zones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    tint = IconGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Zones campus", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(activeZones, fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
fun CovyBottomNavigationBarDashboard() {
    NavigationBar(
        containerColor = SurfaceWhite,
        tonalElevation = 8.dp
    ) {
        // Onglet Accueil (Sélectionné)
        NavigationBarItem(
            icon = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CovyLightGreen)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Accueil",
                        tint = CovyDarkGreen,
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            label = { Text("Accueil", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CovyDarkGreen) },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.DirectionsCar, contentDescription = "Courses", modifier = Modifier.size(22.dp)) },
            label = { Text("Courses", fontSize = 10.sp) },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Wallet, contentDescription = "Revenus", modifier = Modifier.size(22.dp)) },
            label = { Text("Revenus", fontSize = 10.sp) },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Compte", modifier = Modifier.size(22.dp)) },
            label = { Text("Compte", fontSize = 10.sp) },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary
            )
        )
    }
}