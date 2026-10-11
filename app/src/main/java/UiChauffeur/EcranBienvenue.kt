package UiChauffeur



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.*
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

// ==========================================
// CHARTE GRAPHIQUE OFFICIELLE COVY (ABIDJAN)
// ==========================================
val CovyForestGreen6 = Color(0xFF084D38)     // Bouton CTA principal, vert foncé Covy
val CovyTextDark6 = Color(0xFF111827)        // Titre "Le campus, ensemble.", textes principaux
val CovyTextMuted6 = Color(0xFF4B5563)       // Textes secondaires, sous-titres
val CovyLightGreenBg6 = Color(0xFFE8F5EE)    // Fond carte de vérification & badges d'accent
val CovyBorderGreen6 = Color(0xFFBBE5D0)     // Bordures subtiles des encarts verts
val CovyBadgeTextGreen6 = Color(0xFF0F766E)  // Libellé "BIENVENUE · ABIDJAN"
val CovyBackground6 = Color(0xFFFFFFFF)

@Composable
fun WelcomeCampusScreen(
    onCreateAccountClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = CovyBackground6,
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
                // Bouton principal CTA "Créer un compte"
                Button(
                    onClick = onCreateAccountClick,
                    colors = ButtonDefaults.buttonColors(containerColor = CovyForestGreen6),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Créer un compte",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action secondaire "J'ai déjà un compte · Se connecter"
                Row(
                    modifier = Modifier
                        .clickable { onLoginClick() }
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "J'ai déjà un compte · ",
                        fontSize = 14.sp,
                        color = CovyTextMuted6,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Se connecter",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyForestGreen6
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
            // 1. Tag de section "• BIENVENUE · ABIDJAN"
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(CovyBadgeTextGreen6)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "BIENVENUE · ABIDJAN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CovyBadgeTextGreen6,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 2. Grand titre principal
            Text(
                text = "Le campus, ensemble.",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyTextDark6,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 3. Sous-titre
            Text(
                text = "Le covoiturage scolaire et universitaire à Abidjan.",
                fontSize = 14.sp,
                color = CovyTextMuted6,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Bannière visuelle principale avec les étudiants et le chauffeur ivoiriens
            CampusVisualBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(236.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 5. Badge "Un même trajet, un coût partagé"
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CovyLightGreenBg6,
                modifier = Modifier.wrapContentWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = CovyForestGreen6,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Un même trajet, un coût partagé",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CovyForestGreen6
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 6. Titre de proposition de valeur
            Text(
                text = "Moins de frais. Plus de sérénité.",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = CovyTextDark6,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Étudiants, élèves et chauffeurs : trouvez votre place sur le chemin des cours.",
                fontSize = 13.sp,
                color = CovyTextMuted6,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 7. Carte informative de vérification des profils
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CovyLightGreenBg6),
                border = androidx.compose.foundation.BorderStroke(1.dp, CovyBorderGreen6),
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
                                    .background(Color.White)
                                    .border(1.dp, CovyBorderGreen6, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Badge,
                                    contentDescription = "Carte d'identité",
                                    tint = CovyForestGreen6,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Pièce d'identité & Profils vérifiés",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = CovyTextDark6,
                                lineHeight = 20.sp
                            )
                        }

                        // Badge "100% Vérifié"
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, CovyBorderGreen6)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "100%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CovyForestGreen6
                                )
                                Text(
                                    text = "Vérifié",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = CovyBadgeTextGreen6
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "CNI ou passeport obligatoire pour la sécurité de tous, avec carte scolaire ou permis.",
                        fontSize = 12.sp,
                        color = CovyTextMuted6,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Divider(
                        color = CovyBorderGreen6.copy(alpha = 0.6f),
                        thickness = 1.dp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Ligne avec coche verte
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = CovyForestGreen6,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pièce d'identité (CNI / passeport) requise avant tout trajet",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = CovyForestGreen6
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
    // Remplacez R.drawable.campus_abidjan_students par votre ressource image réelle
    imageResId: Int? = null
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF3F4F6))
    ) {
        if (imageResId != null) {
            // Affichage direct de l'image de la ressource drawable si présente
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Élèves et étudiants ivoiriens en tenues scolaires devant le véhicule",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Composant Compose illustrant fidèlement la scène avec les personnages en tenues ivoiriennes
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFDEFD8), // Ciel lumineux campus Abidjan
                                Color(0xFFE8F0E8),
                                Color(0xFFD4E6DC)  // Sol / allée du campus
                            )
                        )
                    )
            ) {
                // Arrière-plan stylisé : Véhicule électrique & Campus
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color(0xFF2C6B6F).copy(alpha = 0.25f))
                )

                // Groupe des 5 personnes (Élève Kaki, Lycéenne, Étudiant IUA, Étudiant ESATIC, Étudiante Fac)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // 1. Élève en uniforme Kaki officiel (Collège / Lycée CI)
                    PersonCharacterBadge(
                        avatarEmoji = "👨🏾‍🎓",
                        uniformName = "Uniforme Kaki",
                        subtext = "Élève CI",
                        bgColor = Color(0xFFD8B688),
                        badgeColor = Color(0xFF8D622A)
                    )

                    // 2. Élève fille en jupe plissée bleue & chemisier blanc
                    PersonCharacterBadge(
                        avatarEmoji = "👩🏾‍🎓",
                        uniformName = "Jupe & Chemisier",
                        subtext = "Lycéenne",
                        bgColor = Color(0xFFE0E7FF),
                        badgeColor = Color(0xFF2B478B)
                    )

                    // 3. Étudiant / Chauffeur IUA au centre (Costume & Cravate IUA)
                    PersonCharacterBadge(
                        avatarEmoji = "👨🏾‍💼",
                        uniformName = "Costume IUA",
                        subtext = "Étudiant / Chauffeur",
                        bgColor = Color(0xFFDCEAFE),
                        badgeColor = Color(0xFF1E3A8A),
                        isFeatured = true
                    )

                    // 4. Étudiant ESATIC (Chemise bleue, cravate sombre, pantalon gris)
                    PersonCharacterBadge(
                        avatarEmoji = "👨🏾‍💻",
                        uniformName = "Tenue ESATIC",
                        subtext = "Étudiant",
                        bgColor = Color(0xFFE0F2FE),
                        badgeColor = Color(0xFF0369A1)
                    )

                    // 5. Étudiante tenue libre / universitaire (Sac au dos, livres)
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

        // Tag flottant "CI · Cocody · Plateau · Yopougon"
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White.copy(alpha = 0.94f),
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
                    color = Color(0xFFF1F5F9)
                ) {
                    Text(
                        text = "CI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyForestGreen6,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Cocody · Plateau · Yopougon",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CovyTextDark6
                )
            }
        }
    }
}

// ==========================================
// COMPOSANT PERSONNAGE DE LA PHOTO (PREVIEW)
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
// COMPOSANT EN-TÊTE OFFICIEL COVY
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
        // Logo Covy avec icône groupe d'usagers
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CovyLightGreenBg6),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Group,
                    contentDescription = "Covy Logo Icon",
                    tint = CovyForestGreen6,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Covy",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyForestGreen6
            )
        }

        // Indicateur d'étape (01 / 30)
        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextMuted6,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewWelcomeCampusScreen() {
    WelcomeCampusScreen()
}
