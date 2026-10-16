package com.example.covy.ui.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
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
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class LiveTracking(
    val driverName: String,
    val vehicle: String,
    val plate: String,
    val rating: Double,
    val etaMinutes: Int,
    val stopName: String,
    val stopDetail: String,
    val destinationName: String,
    val securityCode: String
)

// Couleurs de la carte illustrative (estimées depuis la maquette)
private val MapBackground = Color(0xFFE9EFEA)
private val MapRoad = Color.White
private val MapStop = Color(0xFFF5A623)

@Composable
fun TrackingScreen(
    tracking: LiveTracking,
    onBack: () -> Unit,
    onShareTrip: () -> Unit,
    onAssistance: () -> Unit,
    onCallDriver: () -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "PASSAGER · SUIVI EN DIRECT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "${tracking.driverName.substringBefore(' ')} est en approche",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IllustrativeMap(destinationName = tracking.destinationName, stopName = tracking.stopName)

            // Pastille ETA
            Text(
                "Arrivée dans ${tracking.etaMinutes} min",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )

            // Chauffeur + arrêt
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                        Column {
                            Text(tracking.driverName, fontWeight = FontWeight.Bold)
                            Text(
                                tracking.vehicle,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "${tracking.plate} · ",
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
                                    " ${tracking.rating}",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Votre arrêt : ${tracking.stopName}", fontWeight = FontWeight.Bold)
                            Text(
                                tracking.stopDetail,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Code de sécurité
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Code de sécurité", fontWeight = FontWeight.Bold)
                        Text(
                            "À donner uniquement à la montée",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        tracking.securityCode,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onShareTrip, contentPadding = PaddingValues(0.dp)) {
                    Text("Partager mon trajet", fontWeight = FontWeight.SemiBold)
                }
                TextButton(
                    onClick = onAssistance,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Assistance", fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Button(
            onClick = onCallDriver,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Appeler le chauffeur", fontWeight = FontWeight.SemiBold)
        }
    }
}

// Carte illustrative dessinée en Canvas. À remplacer par une vraie carte
// (Google Maps Compose, Mapbox...) quand le suivi GPS sera branché.
@Composable
private fun IllustrativeMap(destinationName: String, stopName: String) {
    val routeColor = MaterialTheme.colorScheme.primary
    val muted = MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MapBackground)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height

            // Quelques routes de fond
            val roads = listOf(
                Offset(0f, h * 0.25f) to Offset(w, h * 0.35f),
                Offset(w * 0.3f, 0f) to Offset(w * 0.4f, h),
                Offset(0f, h * 0.9f) to Offset(w, h * 0.7f)
            )
            roads.forEach { (a, b) ->
                drawLine(MapRoad, a, b, strokeWidth = 14.dp.toPx(), cap = StrokeCap.Round)
            }

            // Itinéraire : voiture -> arrêt -> destination
            val car = Offset(w * 0.25f, h * 0.78f)
            val stop = Offset(w * 0.45f, h * 0.78f)
            val turn = Offset(w * 0.45f, h * 0.45f)
            val corner = Offset(w * 0.78f, h * 0.45f)
            val dest = Offset(w * 0.78f, h * 0.22f)

            val route = Path().apply {
                moveTo(car.x, car.y)
                lineTo(stop.x, stop.y)
                lineTo(turn.x, turn.y)
                lineTo(corner.x, corner.y)
                lineTo(dest.x, dest.y)
            }
            drawPath(
                route,
                color = routeColor,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            )

            drawCircle(MapStop, radius = 7.dp.toPx(), center = stop)
            drawCircle(Color.White, radius = 7.dp.toPx(), center = stop, style = Stroke(2.dp.toPx()))
            drawCircle(routeColor, radius = 8.dp.toPx(), center = dest)
        }

        Text(
            destinationName,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = routeColor,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 28.dp, end = 24.dp)
        )
        Text(
            stopName,
            fontSize = 11.sp,
            color = muted,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 36.dp, start = 60.dp)
        )

        // Voiture
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 60.dp, bottom = 48.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.DirectionsCar,
                contentDescription = "Position du chauffeur",
                tint = routeColor,
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            "Carte illustrative · Abidjan",
            fontSize = 10.sp,
            color = muted,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun TrackingScreenPreview() {
    CovyTheme {
        TrackingScreen(
            tracking = LiveTracking(
                driverName = "Karim Kouassi",
                vehicle = "Toyota Corolla blanche",
                plate = "AB 1234 CD",
                rating = 4.8,
                etaMinutes = 7,
                stopName = "St-Jean",
                stopDetail = "Devant la pharmacie · Campus Bingerville",
                destinationName = "Campus Bingerville",
                securityCode = "4821"
            ),
            onBack = {}, onShareTrip = {}, onAssistance = {}, onCallDriver = {}
        )
    }
}