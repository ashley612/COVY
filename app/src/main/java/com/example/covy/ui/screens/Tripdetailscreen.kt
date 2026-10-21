package com.example.covy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyBadge
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class TripDetail(
    val dateLabel: String,        // "Vendredi 9 octobre 2026"
    val campusName: String,       // "Bingerville"
    val pickupName: String,       // "St-Jean, Cocody"
    val pickupHint: String,       // "Devant la pharmacie St-Jean"
    val departureTime: String,    // "07:37"
    val dropoffName: String,      // "Campus Bingerville"
    val dropoffHint: String,      // "Portail principal"
    val arrivalTime: String,      // "08:05"
    val driverName: String,       // "Karim Kouassi"
    val vehicle: String,          // "Toyota Corolla blanche"
    val plate: String,            // "AB 1234 CD"
    val rating: String,           // "4,8"
    val groupInfo: String,        // "4 places dans le groupe · Via Angré 8e Tranche"
    val seatsLabel: String,       // "1 place"
    val price: String             // "1 500 FCFA"
)

// Couleurs estimées depuis la maquette : à remplacer par les codes Figma
private val BorderGray = Color(0xFFE3E9E5)
private val MapBackground = Color(0xFFE9EEEA)
private val MapWater = Color(0xFFD6E6EE)
private val MapCampus = Color(0xFFD5E5D6)
private val CampusAmber = Color(0xFFF2B33D)
private val OrangeDot = Color(0xFFF2A93B)

@Composable
fun TripDetailScreen(
    detail: TripDetail,
    onBack: () -> Unit,
    onReserve: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "PASSAGER · RÉSERVER",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Cap sur ${detail.campusName}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(detail.dateLabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            IllustrativeMap(
                pickupLabel = detail.pickupName.substringBefore(","),
                campusLabel = detail.dropoffName,
                areaLabel = detail.pickupName.substringAfter(", ", "").uppercase()
            )

            // Départ et arrivée
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    CovyBadge(
                        text = "Votre départ · ${detail.departureTime}",
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                    StopRow(
                        title = detail.pickupName,
                        hint = detail.pickupHint,
                        time = detail.departureTime,
                        pickup = true
                    )
                    StopRow(
                        title = detail.dropoffName,
                        hint = detail.dropoffHint,
                        time = detail.arrivalTime,
                        pickup = false
                    )
                }
            }

            // Chauffeur
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Pas de photo pour l'instant : initiales
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                detail.driverName.split(" ")
                                    .filter { it.isNotBlank() }
                                    .take(2)
                                    .joinToString("") { it.first().uppercase() },
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(Modifier.width(14.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(detail.driverName, fontWeight = FontWeight.Bold)
                            Text(
                                detail.vehicle,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "${detail.plate} · ★ ${detail.rating}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Text(
                        detail.groupInfo,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Prix par passager
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.AccountBalanceWallet, contentDescription = null)
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Prix par passager", fontWeight = FontWeight.Bold)
                        Text(
                            "${detail.seatsLabel} · Paiement à l'étape suivante",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        detail.price,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onReserve,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Réserver ma place", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun StopRow(title: String, hint: String, time: String, pickup: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .then(
                    if (pickup) Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    else Modifier.background(CampusAmber)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (pickup) Icons.Outlined.LocationOn else Icons.Outlined.School,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(
                hint,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(time, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}

/** Carte dessinée à la main : elle ne montre pas une vraie carte, seulement l'idée du trajet. */
@Composable
private fun IllustrativeMap(pickupLabel: String, campusLabel: String, areaLabel: String) {
    val routeColor = MaterialTheme.colorScheme.primary

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MapBackground)
    ) {
        val w = maxWidth
        val h = maxHeight

        Canvas(modifier = Modifier.fillMaxSize()) {
            val cw = size.width
            val ch = size.height

            // Zones : eau en bas à gauche, campus en haut à droite
            drawOval(MapWater, topLeft = Offset(-cw * 0.05f, ch * 0.78f),
                size = androidx.compose.ui.geometry.Size(cw * 0.35f, ch * 0.45f))
            drawOval(MapCampus, topLeft = Offset(cw * 0.72f, -ch * 0.15f),
                size = androidx.compose.ui.geometry.Size(cw * 0.4f, ch * 0.7f))

            // Routes blanches
            val road = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            drawLine(Color.White, Offset(0f, ch * 0.62f), Offset(cw, ch * 0.45f),
                strokeWidth = road.width, cap = StrokeCap.Round)
            drawLine(Color.White, Offset(cw * 0.30f, 0f), Offset(cw * 0.45f, ch),
                strokeWidth = road.width, cap = StrokeCap.Round)
            drawLine(Color.White, Offset(cw * 0.62f, ch), Offset(cw * 0.80f, 0f),
                strokeWidth = road.width, cap = StrokeCap.Round)

            // Trajet en vert foncé
            val route = Path().apply {
                moveTo(cw * 0.17f, ch * 0.85f)
                lineTo(cw * 0.38f, ch * 0.52f)
                lineTo(cw * 0.55f, ch * 0.52f)
                lineTo(cw * 0.68f, ch * 0.32f)
                lineTo(cw * 0.87f, ch * 0.32f)
            }
            drawPath(
                route, routeColor,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            )

            // Points : départ (orange) et campus (vert foncé)
            drawCircle(Color.White, 8.dp.toPx(), Offset(cw * 0.55f, ch * 0.52f))
            drawCircle(OrangeDot, 6.dp.toPx(), Offset(cw * 0.55f, ch * 0.52f))
            drawCircle(routeColor, 6.dp.toPx(), Offset(cw * 0.87f, ch * 0.32f))
        }

        if (areaLabel.isNotBlank()) {
            Text(
                areaLabel,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 16.dp, top = 14.dp)
            )
        }
        Text(
            pickupLabel,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.offset(x = w * 0.55f - 16.dp, y = h * 0.52f + 12.dp)
        )
        Text(
            campusLabel,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.offset(x = w * 0.87f - 100.dp, y = h * 0.32f - 26.dp)
        )

        Text(
            "Carte illustrative · Abidjan",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 1000)
@Composable
private fun TripDetailScreenPreview() {
    CovyTheme {
        TripDetailScreen(
            detail = TripDetail(
                dateLabel = "Vendredi 9 octobre 2026",
                campusName = "Bingerville",
                pickupName = "St-Jean, Cocody",
                pickupHint = "Devant la pharmacie St-Jean",
                departureTime = "07:37",
                dropoffName = "Campus Bingerville",
                dropoffHint = "Portail principal",
                arrivalTime = "08:05",
                driverName = "Karim Kouassi",
                vehicle = "Toyota Corolla blanche",
                plate = "AB 1234 CD",
                rating = "4,8",
                groupInfo = "4 places dans le groupe · Via Angré 8e Tranche",
                seatsLabel = "1 place",
                price = "1 500 FCFA"
            ),
            onBack = {}, onReserve = {}
        )
    }
}