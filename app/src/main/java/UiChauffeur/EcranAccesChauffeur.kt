package com.example.covy.UiChauffeur

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ✅ Nouveaux imports pour le ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.viewmodel.DriverProfileViewModel

// ==========================================
// PALETTE OFFICIELLE COVY
// ==========================================
val CovyForestGreen2 = Color(0xFF084D38)
val CovyTextDark2 = Color(0xFF111827)
val CovyTextMuted2 = Color(0xFF6B7280)
val CovyLightGreenBg2 = Color(0xFFE8F5EE)
val CovyBorderCard2 = Color(0xFFE5E7EB)
val CovyBadgeTextGreen2 = Color(0xFF0F766E)
val CovyBackground2 = Color(0xFFFFFFFF)

@Composable
fun DriverProfileSelectionScreen(
    // ✅ Ajout du ViewModel en premier paramètre
    viewModel: DriverProfileViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onRegisterAsDriverClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = CovyBackground2,
        topBar = {
            TopAppBarCovy(
                stepText = "03 / 30",
                onBackClicked = onNavigateBack
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Déjà inscrit ? Se connecter",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CovyForestGreen2,
                    modifier = Modifier
                        // ✅ Utilise le ViewModel pour se connecter
                        .clickable {
                            viewModel.seConnecter {
                                onLoginClick()
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "INSCRIPTION · PROFIL",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = CovyBadgeTextGreen2,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Vous êtes chauffeur Covy ?",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyTextDark2,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ce parcours est réservé aux chauffeurs Covy. Créez votre compte pour transporter des passagers.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = CovyTextMuted2,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                // ✅ Utilise le ViewModel pour confirmer le profil
                onClick = {
                    viewModel.confirmerProfilChauffeur {
                        onRegisterAsDriverClick()
                    }
                },
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, CovyBorderCard2),
                shadowElevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(CovyLightGreenBg2),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = "Chauffeur",
                            tint = CovyForestGreen2,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Chauffeur",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyTextDark2
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Je transporte des passagers vers leurs campus.",
                        fontSize = 14.sp,
                        color = CovyTextMuted2,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "M'inscrire comme chauffeur",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = CovyForestGreen2
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = CovyForestGreen2,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// COMPOSANT EN-TÊTE COMMUN COVY
// ==========================================

@Composable
fun TopAppBarCovy(stepText: String, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Retour",
                tint = CovyTextDark2
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CovyLightGreenBg2),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌱", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Covy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CovyForestGreen2
            )
        }

        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextMuted2,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewDriverProfileSelectionScreen() {
    DriverProfileSelectionScreen()
}