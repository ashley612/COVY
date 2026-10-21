package com.example.covy.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class TripTicket(
    val reference: String,
    val dateLabel: String,
    val pickupName: String,
    val pickupDetail: String,
    val pickupTime: String,
    val dropName: String,
    val dropDetail: String,
    val dropTime: String,
    val securityCode: String,
    val driverName: String,
    val vehicle: String,
    val plate: String,
    val rating: Double,
    val paymentTitle: String,
    val paymentDetail: String
)

@Composable
fun TicketScreen(
    ticket: TripTicket,
    onBack: () -> Unit,
    onTrackDriver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 36.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "PASSAGER · BILLET",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Votre place est confirmée", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Tout est prêt pour votre prochain départ.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Carte billet : référence, horaires, code de sécurité
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ReservationChip("Réservation ${ticket.reference}")

                    Text(ticket.dateLabel, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        StopRow(
                            name = ticket.pickupName,
                            detail = ticket.pickupDetail,
                            time = ticket.pickupTime,
                            filled = true
                        )
                        StopRow(
                            name = ticket.dropName,
                            detail = ticket.dropDetail,
                            time = ticket.dropTime,
                            filled = false
                        )
                    }

                    HorizontalDivider()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "CODE DE SÉCURITÉ À PRÉSENTER À BORD",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            ticket.securityCode.toList().joinToString("  "),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Communiquez-le au chauffeur à la montée.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Chauffeur
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(ticket.driverName, fontWeight = FontWeight.Bold)
                        Text(
                            ticket.vehicle,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "${ticket.plate} · ",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "Note",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                " ${ticket.rating}",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Paiement reçu
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        ticket.paymentTitle,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        ticket.paymentDetail,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Button(
            onClick = onTrackDriver,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Suivre mon chauffeur", fontWeight = FontWeight.SemiBold)
        }
    }
}

// Pastille verte "Réservation ..." (CovyBadge est aux couleurs "en attente", jaune)
@Composable
private fun ReservationChip(text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Outlined.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun StopRow(
    name: String,
    detail: String,
    time: String,
    filled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Point plein = départ, point creux = arrivée
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            if (!filled) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Bold)
            Text(
                detail,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(time, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun TicketScreenPreview() {
    CovyTheme {
        TicketScreen(
            ticket = TripTicket(
                reference = "OXV-3045",
                dateLabel = "Vendredi 9 octobre · 07:37",
                pickupName = "St-Jean, Cocody",
                pickupDetail = "Devant la pharmacie de St-Jean",
                pickupTime = "07:37",
                dropName = "Campus Bingerville",
                dropDetail = "Portail principal",
                dropTime = "08:09",
                securityCode = "2310",
                driverName = "Karim Kouassi",
                vehicle = "Toyota Corolla blanche",
                plate = "AB 1234 CD",
                rating = 4.8,
                paymentTitle = "Paiement reçu · 1 500 FCFA",
                paymentDetail = "Wave · 1 place"
            ),
            onBack = {},
            onTrackDriver = {}
        )
    }
}