package com.example.covy.UiChauffeur

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.R
import com.example.covy.ui.theme.CovyTheme
import com.example.covy.viewmodel.DriverProfileViewModel

@Composable
fun DriverProfileSelectionScreen(
    viewModel: DriverProfileViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onRegisterAsDriverClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Scaffold(
        // ✅ MaterialTheme au lieu de CovyBackground2
        containerColor = MaterialTheme.colorScheme.background,
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
                    // ✅ MaterialTheme.primary au lieu de CovyForestGreen2
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
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

            // Badge "INSCRIPTION · PROFIL"
            Text(
                text = "INSCRIPTION · PROFIL",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                // ✅ secondary au lieu de CovyBadgeTextGreen2
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Titre principal
            Text(
                text = "Vous êtes chauffeur Covy ?",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                // ✅ onBackground au lieu de CovyTextDark2
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Sous-titre
            Text(
                text = "Ce parcours est réservé aux chauffeurs Covy. Créez votre compte pour transporter des passagers.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                // ✅ onSurfaceVariant au lieu de CovyTextMuted2
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Carte "Chauffeur"
            Surface(
                onClick = {
                    viewModel.confirmerProfilChauffeur {
                        onRegisterAsDriverClick()
                    }
                },
                shape = RoundedCornerShape(24.dp),
                // ✅ surface au lieu de Color.White
                color = MaterialTheme.colorScheme.surface,
                // ✅ outline au lieu de CovyBorderCard2
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shadowElevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Icône dans un carré vert clair
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            // ✅ primaryContainer au lieu de CovyLightGreenBg2
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = "Chauffeur",
                            // ✅ primary au lieu de CovyForestGreen2
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Chauffeur",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        // ✅ onSurface au lieu de CovyTextDark2
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Je transporte des passagers vers leurs campus.",
                        fontSize = 14.sp,
                        // ✅ onSurfaceVariant au lieu de CovyTextMuted2
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "M'inscrire comme chauffeur",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,

                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,

                            tint = MaterialTheme.colorScheme.primary,
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
                // ✅ onBackground au lieu de CovyTextDark2
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.covy_logo),
                    contentDescription = "Logo Covy",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Covy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                // ✅ primary au lieu de CovyForestGreen2
                color = MaterialTheme.colorScheme.primary
            )
        }

        Surface(
            // ✅ surfaceVariant au lieu de Color(0xFFF3F4F6)
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                // ✅ onSurfaceVariant au lieu de CovyTextMuted2
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewDriverProfileSelectionScreen() {
    CovyTheme {
        DriverProfileSelectionScreen()
    }
}