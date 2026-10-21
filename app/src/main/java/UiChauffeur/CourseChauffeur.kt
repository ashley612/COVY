package com.example.covy.UiChauffeur

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.models.GroupRideUiState
import com.example.covy.models.StopInfo
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
import com.example.covy.viewmodel.GroupRideViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GroupRideScreen(
    viewModel: GroupRideViewModel = viewModel(),
    onBackClick: () -> Unit,
    onAcceptRide: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = BackgroundGray,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopAppBarGroupRide(onBackClick)

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CovyDarkGreen)
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))

                // ✅ APPELS CORRIGÉS AVEC LES BONS NOMS
                GroupRideHeaderSection(uiState)
                Spacer(modifier = Modifier.height(16.dp))
                MapSectionGroup()
                Spacer(modifier = Modifier.height(16.dp))
                GroupRideFareCard(uiState)
                Spacer(modifier = Modifier.height(16.dp))
                GroupRideStopsCard(uiState)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = uiState.terminusInfo,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                GroupRideBottomAction(uiState, viewModel, onAcceptRide)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TopAppBarGroupRide(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Retour",
            tint = TextSecondary,
            modifier = Modifier.size(24.dp).clickable { onBackClick() }
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
        Text("27 / 30", fontSize = 14.sp, color = TextTertiary, fontWeight = FontWeight.Medium)
    }
}

// ✅ Renommé pour éviter le conflit
@Composable
fun GroupRideHeaderSection(uiState: GroupRideUiState) {
    Text(
        text = "CHAUFFEUR · DEMANDE GROUPÉE",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = CovyGreenText,
        letterSpacing = 0.5.sp
    )
    Text(
        text = uiState.title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun MapSectionGroup() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MapBgGreen)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(Color.White, start = Offset(0f, size.height * 0.6f), end = Offset(size.width, size.height * 0.3f), strokeWidth = 25f)
            drawLine(Color.White, start = Offset(size.width * 0.3f, 0f), end = Offset(size.width * 0.3f, size.height), strokeWidth = 15f)
            drawLine(Color.White, start = Offset(size.width * 0.8f, 0f), end = Offset(size.width * 0.8f, size.height), strokeWidth = 15f)
            drawCircle(MapWaterBlue, radius = size.width * 0.25f, center = Offset(size.width * 0.15f, size.height * 0.9f))

            val path = Path().apply {
                moveTo(size.width * 0.2f, size.height * 0.7f)
                lineTo(size.width * 0.4f, size.height * 0.6f)
                lineTo(size.width * 0.55f, size.height * 0.35f)
                lineTo(size.width * 0.8f, size.height * 0.35f)
            }
            drawPath(path = path, color = MapRouteLine, style = Stroke(width = 10f, cap = StrokeCap.Round))
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-10).dp, y = 20.dp)
                .size(10.dp)
                .background(StopPinYellow, CircleShape)
        )
        Text(
            text = "St-Jean",
            fontSize = 10.sp,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.Center).offset(x = 24.dp, y = 20.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 24.dp)
                .size(12.dp)
                .background(CovyDarkGreen, CircleShape)
        )
        Text(
            text = "Campus Bingerville",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = CovyDarkGreen,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 30.dp, end = 40.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text("Carte illustrative · Abidjan", fontSize = 9.sp, color = TextSecondary)
        }
    }
}

// ✅ Renommé pour éviter le conflit
@Composable
fun GroupRideFareCard(uiState: GroupRideUiState) {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CovyDarkGreen)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "MONTANT DE LA COURSE",
                fontSize = 11.sp,
                color = NavTextLight,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "${formatter.format(uiState.fareAmount)} ",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "FCFA",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${uiState.seats} places × ${formatter.format(uiState.pricePerSeat)} FCFA",
                fontSize = 13.sp,
                color = TextWhiteAlpha
            )
        }
    }
}

// ✅ Renommé pour éviter le conflit
@Composable
fun GroupRideStopsCard(uiState: GroupRideUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .background(PillGreenBg, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${uiState.estimatedTime} · ${uiState.totalPassengers} passagers",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PillGreenText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            uiState.stops.forEachIndexed { index, stop ->
                StopTimelineItem(
                    stop = stop,
                    isLast = index == uiState.stops.lastIndex
                )
            }
        }
    }
}

@Composable
fun StopTimelineItem(stop: StopInfo, isLast: Boolean) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = if (stop.isDestination) StopPinYellow else CovyGreenText,
                modifier = Modifier.size(16.dp)
            )

            if (!isLast) {
                Canvas(modifier = Modifier.weight(1f).width(1.dp)) {
                    drawLine(
                        color = TimelineDottedLine,
                        start = Offset(size.width / 2, 0f),
                        end = Offset(size.width / 2, size.height),
                        strokeWidth = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f).padding(bottom = if (isLast) 0.dp else 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stop.locationName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = stop.time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
            }

            stop.passengers.forEach { passenger ->
                val detailText = if (passenger.info.isNotEmpty()) {
                    "${passenger.name} · ${passenger.info}"
                } else {
                    passenger.name
                }

                if (detailText.isNotBlank()) {
                    Text(
                        text = detailText.trim('·', ' '),
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

// ✅ Renommé et corrigé pour recevoir onAcceptRide
@Composable
fun GroupRideBottomAction(
    uiState: GroupRideUiState,
    viewModel: GroupRideViewModel,
    onAcceptRide: () -> Unit
) {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)
    Column {
        Button(
            onClick = {
                viewModel.onAcceptRideClick()
                onAcceptRide() // Maintenant ça fonctionne
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CovyDarkGreen)
        ) {
            Text(
                text = "Accepter · ${formatter.format(uiState.fareAmount)} FCFA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { viewModel.onDeclineRideClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Refuser cette demande",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CovyDarkGreen
            )
        }
    }
}