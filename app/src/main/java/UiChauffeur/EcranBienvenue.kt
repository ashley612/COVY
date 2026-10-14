package com.example.covy.UiChauffeur

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.theme.CovyTheme

@Composable
fun WelcomeCampusScreen(
    onCreateAccountClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Scaffold(
        // ✅ MaterialTheme.background
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarWelcomeCovy(
                stepText = "01 / 30"
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Bouton principal CTA
                Button(
                    onClick = onCreateAccountClick,
                    // ✅ primary
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Créer un compte",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        // ✅ onPrimary
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action secondaire
                Row(
                    modifier = Modifier
                        .clickable { onLoginClick() }
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "J'ai déjà un compte · ",
                        fontSize = 14.sp,
                        // ✅ onSurfaceVariant
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Se connecter",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        // ✅ primary
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            // Tag "BIENVENUE · ABIDJAN"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        // ✅ secondary
                        .background(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "BIENVENUE · ABIDJAN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    // ✅ secondary
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Grand titre
            Text(
                text = "Le campus, ensemble.",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                // ✅ onBackground
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Sous-titre
            Text(
                text = "Le covoiturage scolaire et universitaire à Abidjan.",
                fontSize = 14.sp,
                // ✅ onSurfaceVariant
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bannière visuelle
            CampusVisualBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(236.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Badge "Un même trajet, un coût partagé"
            Surface(
                shape = RoundedCornerShape(20.dp),
                // ✅ primaryContainer
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.wrapContentWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        // ✅ primary
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Un même trajet, un coût partagé",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        // ✅ primary
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Proposition de valeur
            Text(
                text = "Moins de frais. Plus de sérénité.",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                // ✅ onBackground
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Étudiants, élèves et chauffeurs : trouvez votre place sur le chemin des cours.",
                fontSize = 13.sp,
                // ✅ onSurfaceVariant
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Carte informative
            Card(
                shape = RoundedCornerShape(20.dp),
                // ✅ primaryContainer
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                // ✅ outlineVariant
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    // ✅ surface
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Badge,
                                    contentDescription = "Carte d'identité",
                                    // ✅ primary
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Pièce d'identité & Profils vérifiés",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                // ✅ onSurface
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }

                        // Badge "100% Vérifié"
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            // ✅ surface
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "100%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    // ✅ primary
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Vérifié",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    // ✅ secondary
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "CNI ou passeport obligatoire pour la sécurité de tous, avec carte scolaire ou permis.",
                        fontSize = 12.sp,
                        // ✅ onSurfaceVariant
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Divider(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                        thickness = 1.dp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            // ✅ primary
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pièce d'identité (CNI / passeport) requise avant tout trajet",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            // ✅ primary
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ==========================================
// BANNIÈRE VISUELLE CAMPUS & ÉTUDIANTS
// ==========================================
@Composable
fun CampusVisualBanner(
    modifier: Modifier = Modifier,
    imageResId: Int? = null
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF3F4F6))
    ) {
        if (imageResId != null) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Élèves et étudiants ivoiriens",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFDEFD8),
                                Color(0xFFE8F0E8),
                                Color(0xFFD4E6DC)
                            )
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color(0xFF2C6B6F).copy(alpha = 0.25f))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    PersonCharacterBadge(
                        avatarEmoji = "👨🏾‍🎓",
                        uniformName = "Uniforme Kaki",
                        subtext = "Élève CI",
                        bgColor = Color(0xFFD8B688),
                        badgeColor = Color(0xFF8D622A)
                    )
                    PersonCharacterBadge(
                        avatarEmoji = "👩🏾‍🎓",
                        uniformName = "Jupe & Chemisier",
                        subtext = "Lycéenne",
                        bgColor = Color(0xFFE0E7FF),
                        badgeColor = Color(0xFF2B478B)
                    )
                    PersonCharacterBadge(
                        avatarEmoji = "👨🏾‍💼",
                        uniformName = "Costume IUA",
                        subtext = "Étudiant / Chauffeur",
                        bgColor = Color(0xFFDCEAFE),
                        badgeColor = Color(0xFF1E3A8A),
                        isFeatured = true
                    )
                    PersonCharacterBadge(
                        avatarEmoji = "👨🏾‍💻",
                        uniformName = "Tenue ESATIC",
                        subtext = "Étudiant",
                        bgColor = Color(0xFFE0F2FE),
                        badgeColor = Color(0xFF0369A1)
                    )
                    PersonCharacterBadge(
                        avatarEmoji = "👩🏾‍💻",
                        uniformName = "Campus Libre",
                        subtext = "Étudiante",
                        bgColor = Color(0xFFFEE2E2),
                        badgeColor = Color(0xFFB91C1C)
                    )
                }
            }
        }

        // Tag flottant
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
            shadowElevation = 2.dp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "CI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        // ✅ primary
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Cocody · Plateau · Yopougon",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    // ✅ onSurface
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

// ==========================================
// COMPOSANT PERSONNAGE
// ==========================================
@Composable
fun PersonCharacterBadge(
    avatarEmoji: String,
    uniformName: String,
    subtext: String,
    bgColor: Color,
    badgeColor: Color,
    isFeatured: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = if (isFeatured) 10.dp else 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (isFeatured) 48.dp else 42.dp)
                .clip(CircleShape)
                .background(bgColor)
                .border(
                    width = if (isFeatured) 2.dp else 1.dp,
                    color = badgeColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = avatarEmoji,
                fontSize = if (isFeatured) 24.sp else 20.sp
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = badgeColor
        ) {
            Text(
                text = subtext,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )
        }
    }
}

// ==========================================
// COMPOSANT EN-TÊTE
// ==========================================
@Composable
fun TopAppBarWelcomeCovy(stepText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    // ✅ primaryContainer
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Group,
                    contentDescription = "Covy Logo Icon",
                    // ✅ primary
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Covy",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                // ✅ primary
                color = MaterialTheme.colorScheme.primary
            )
        }

        Surface(
            // ✅ surfaceVariant
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                // ✅ onSurfaceVariant
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewWelcomeCampusScreen() {
    CovyTheme {
        WelcomeCampusScreen()
    }
}