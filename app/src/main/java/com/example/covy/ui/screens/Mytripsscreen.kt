package com.example.covy.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyBottomBar
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.components.PassengerTab
import com.example.covy.ui.theme.CovyTheme

data class UpcomingTrip(
    val id: String,
    val statusLabel: String,      // "Confirmé · Vendredi 9 octobre"
    val pickupName: String,
    val pickupDetail: String,
    val pickupTime: String,
    val dropName: String,
    val dropDetail: String,
    val dropTime: String,
    val driverLine: String,       // "Karim Kouassi · 1 place"
    val paymentLine: String       // "2 500 FCFA · Paiement reçu"
)

data class PastTrip(
    val id: String,
    val headline: String,         // "08 oct. · Campus Bingerville"
    val subtitle: String,         // "St-Jean → Campus · Terminé"
    val priceLabel: String        // "2 500 F"
)

// Jaune de la pastille "campus" (estimé depuis la maquette)
private val CampusYellow = Color(0xFFF5C542)

@Composable
fun MyTripsScreen(
    upcoming: List<UpcomingTrip>,
    past: List<PastTrip>,
    onBack: () -> Unit,
    onViewTicket: (UpcomingTrip) -> Unit,
    onManageReservation: (UpcomingTrip) -> Unit,
    onViewReceipt: (PastTrip) -> Unit,
    onTabSelected: (PassengerTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "ESPACE PASSAGER · TRAJETS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Mes trajets", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Vos réservations et vos trajets terminés.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CountChip("À venir · ${upcoming.size}")
                CountChip("Passés · ${past.size}")
            }

            upcoming.forEach { trip ->
                UpcomingTripCard(
                    trip = trip,
                    onViewTicket = { onViewTicket(trip) },
                    onManage = { onManageReservation(trip) }
                )
            }

            if (past.isNotEmpty()) {
                Text("Derniers trajets", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                past.forEach { trip ->
                    PastTripCard(trip = trip, onViewReceipt = { onViewReceipt(trip) })
                }
            }
        }

        CovyBottomBar(selected = PassengerTab.TRIPS, onSelect = onTabSelected)
    }
}

@Composable
private fun CountChip(text: String) {
    Text(
        text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
private fun UpcomingTripCard(
    trip: UpcomingTrip,
    onViewTicket: () -> Unit,
    onManage: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CountChip(trip.statusLabel)

            TripStopRow(
                leading = {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                name = trip.pickupName,
                detail = trip.pickupDetail,
                time = trip.pickupTime
            )
            TripStopRow(
                leading = {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(CampusYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                name = trip.dropName,
                detail = trip.dropDetail,
                time = trip.dropTime
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.DirectionsCar,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(trip.driverLine, fontWeight = FontWeight.SemiBold)
                    Text(
                        trip.paymentLine,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column {
                TextButton(onClick = onViewTicket, contentPadding = PaddingValues(0.dp)) {
                    Text("Voir le billet →", fontWeight = FontWeight.SemiBold)
                }
                TextButton(onClick = onManage, contentPadding = PaddingValues(0.dp)) {
                    Text("Gérer ma réservation", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun TripStopRow(
    leading: @Composable () -> Unit,
    name: String,
    detail: String,
    time: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(28.dp), contentAlignment = Alignment.Center) { leading() }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Bold)
            Text(
                detail,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(time, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun PastTripCard(trip: PastTrip, onViewReceipt: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(trip.headline, fontWeight = FontWeight.Bold)
                    Text(
                        trip.subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    trip.priceLabel,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            TextButton(onClick = onViewReceipt, contentPadding = PaddingValues(0.dp)) {
                Text("Voir le détail et le reçu →", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun MyTripsScreenPreview() {
    CovyTheme {
        MyTripsScreen(
            upcoming = listOf(
                UpcomingTrip(
                    id = "1",
                    statusLabel = "Confirmé · Vendredi 9 octobre",
                    pickupName = "St-Jean, Cocody",
                    pickupDetail = "Devant la pharmacie St-Jean",
                    pickupTime = "07:37",
                    dropName = "Campus Bingerville",
                    dropDetail = "Portail principal",
                    dropTime = "08:05",
                    driverLine = "Karim Kouassi · 1 place",
                    paymentLine = "2 500 FCFA · Paiement reçu"
                )
            ),
            past = listOf(
                PastTrip("a", "08 oct. · Campus Bingerville", "St-Jean → Campus · Terminé", "2 500 F"),
                PastTrip("b", "07 oct. · St-Jean, Cocody", "Campus Bingerville → St-Jean · Terminé", "2 500 F")
            ),
            onBack = {}, onViewTicket = {}, onManageReservation = {},
            onViewReceipt = {}, onTabSelected = {}
        )
    }
}