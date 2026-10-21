package com.example.covy.UiChauffeur

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.models.ActiveRideUiState
import com.example.covy.ui.theme.*
import com.example.covy.ui.theme.CovyColors.BackgroundGray
import com.example.covy.ui.theme.CovyColors.BorderLight
import com.example.covy.ui.theme.CovyColors.CovyDarkGreen
import com.example.covy.ui.theme.CovyColors.CovyGreenText
import com.example.covy.ui.theme.CovyColors.CovyLightGreen
import com.example.covy.ui.theme.CovyColors.SurfaceWhite
import com.example.covy.ui.theme.CovyColors.TextPrimary
import com.example.covy.ui.theme.CovyColors.TextSecondary
import com.example.covy.ui.theme.CovyColors.TextTertiary
import com.example.covy.ui.theme.CovyColors.TextWhite

@Composable
fun ActiveRideScreen(viewModel: ActiveRideViewModel = viewModel(),
                     onBackClick: () -> Unit) {
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
            TopAppBarActiveRide(onBackClick)

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CovyDarkGreen)
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                HeaderSection(uiState)
                Spacer(modifier = Modifier.height(16.dp))
                NavigationBanner(uiState)
                Spacer(modifier = Modifier.height(16.dp))
                MapSection()
                Spacer(modifier = Modifier.height(16.dp))
                PassengerCard(uiState)
                Spacer(modifier = Modifier.weight(1f))
                BottomActionSection(uiState, viewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TopAppBarActiveRide(onBackClick: () -> Unit) {
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
        Text(
            text = "Covy",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = CovyDarkGreen
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "28 / 30",
            fontSize = 14.sp,
            color = TextTertiary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun HeaderSection(uiState: ActiveRideUiState) {
    Text(
        text = "CHAUFFEUR · COURSE EN COURS",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = CovyGreenText,
        letterSpacing = 0.5.sp
    )
    Text(
        text = "Prochain arrêt : ${uiState.nextStopName}",
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun NavigationBanner(uiState: ActiveRideUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CovyDarkGreen)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icône de direction (flèche qui tourne)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, NavTextLight, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.TrendingFlat,
                    contentDescription = null,
                    tint = TextWhite,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = uiState.navigationInfo.distanceAndStreet,
                    fontSize = 11.sp,
                    color = NavTextLight,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = uiState.navigationInfo.instruction,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }
        }
    }
}

@Composable
fun MapSection() {
    // Simulation visuelle de la carte pour un rendu identique sans assets externes
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MapBgGreen)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Dessin des routes (lignes blanches)
            drawLine(Color.White, start = Offset(0f, size.height * 0.4f), end = Offset(size.width, size.height * 0.4f), strokeWidth = 30f)
            drawLine(Color.White, start = Offset(size.width * 0.4f, 0f), end = Offset(size.width * 0.4f, size.height), strokeWidth = 20f)
            drawLine(Color.White, start = Offset(size.width * 0.7f, 0f), end = Offset(size.width * 0.7f, size.height), strokeWidth = 20f)

            // Dessin de la zone d'eau
            drawCircle(MapWaterBlue, radius = size.width * 0.3f, center = Offset(size.width * 0.1f, size.height * 0.9f))

            // Dessin du tracé vert
            val path = Path().apply {
                moveTo(size.width * 0.1f, size.height * 0.8f)
                lineTo(size.width * 0.4f, size.height * 0.5f)
                lineTo(size.width * 0.5f, size.height * 0.5f)
                lineTo(size.width * 0.7f, size.height * 0.2f)
                lineTo(size.width * 0.85f, size.height * 0.2f)
            }
            drawPath(path = path, color = MapRouteLine, style = Stroke(width = 12f, cap = StrokeCap.Round))
        }

        // Point de départ (St-Jean)
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-30).dp, y = 10.dp)
                .size(12.dp)
                .background(Color(0xFFFFD54F), CircleShape)
        )

        // Voiture
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-80).dp, y = 10.dp)
                .size(32.dp)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = CovyDarkGreen, modifier = Modifier.size(18.dp))
        }

        // Destination
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 24.dp, end = 24.dp)
                .size(14.dp)
                .background(CovyDarkGreen, CircleShape)
        )
        Text(
            text = "Campus Bingerville",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = CovyDarkGreen,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 22.dp, end = 40.dp)
        )

        // Texte St-Jean
        Text(
            text = "St-Jean",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 16.dp, y = 16.dp)
        )

        // Badge Carte illustrative
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text("Carte illustrative · Abidjan", fontSize = 10.sp, color = TextSecondary)
        }
    }
}

@Composable
fun PassengerCard(uiState: ActiveRideUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Badge
            Box(
                modifier = Modifier
                    .background(BadgeBeigeBg, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Récupération · Étape St-Jean",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadgeBrownText
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${uiState.passengerInfo.name} vous attend",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = uiState.passengerInfo.pickupDetails,
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Code passager à valider",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Boîte du code
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderLight, RoundedCornerShape(12.dp))
                    .background(CodeBoxBg, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.passengerInfo.validationCode,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = 8.sp // Espacement pour correspondre à l'image
                )
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Sécurisé",
                    tint = TextTertiary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun BottomActionSection(uiState: ActiveRideUiState, viewModel: ActiveRideViewModel) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Voir les ${uiState.totalPassengers} passagers",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CovyDarkGreen,
                modifier = Modifier.clickable { viewModel.onViewPassengersClick() }
            )
            Text(
                text = "SOS · Urgence",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = SosRed,
                modifier = Modifier.clickable { viewModel.onSosClick() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Terminus : ${uiState.destination} · ${uiState.estimatedArrival}",
            fontSize = 11.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onValidatePickupClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CovyDarkGreen)
        ) {
            Text(
                text = "Valider la montée de ${uiState.passengerInfo.name}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }
    }
}