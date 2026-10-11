package UiChauffeur



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// PALETTE OFFICIELLE COVY (IDENTIQUE ÉTAPE 1)
// ==========================================
val CovyForestGreen7: Color= Color(0xFF084D38)     // Bouton CTA principal, barre de progression 100%
val CovyTextDark7 = Color(0xFF111827)        // Titres & textes principaux
val CovyTextMuted7 = Color(0xFF6B7280)       // Libellés secondaires, sous-titres
val CovyLightGreenBg7 = Color(0xFFE8F5EE)    // Fond des cartes documents ajoutés & badges
val CovyBorderGreen7 = Color(0xFFBBE5D0)     // Bordures des cartes de documents
val CovyInputBg7 = Color(0xFFF9FAFB)         // Fond des textfields
val CovyInputBorder7 = Color(0xFFE5E7EB)      // Bordure des textfields inactifs
val CovyBadgeTextGreen7 = Color(0xFF0F766E)  // Texte des badges d'étape
val CovyBackground7 = Color(0xFFFFFFFF)

@Composable
fun VehicleAndDocumentsScreen(
    onNavigateBack: () -> Unit = {},
    onSubmitForReview: () -> Unit = {}
) {
    // États des champs du véhicule
    var carModel by remember { mutableStateOf("Toyota Corolla") }
    var carYear by remember { mutableStateOf("2021") }
    var carColor by remember { mutableStateOf("Blanche") }
    var licensePlate by remember { mutableStateOf("AB 1234 CD") }

    // Noms des fichiers téléversés
    var driverLicenseFile by remember { mutableStateOf("permis-karim.pdf") }
    var insuranceFile by remember { mutableStateOf("assurance-2026.pdf") }
    var technicalInspectionFile by remember { mutableStateOf("visite-technique.pdf") }

    Scaffold(
        containerColor = CovyBackground7,
        topBar = {
            ProgressTopAppBar(
                stepText = "13 / 30",
                onBackClicked = onNavigateBack
            )
        },
        bottomBar = {
            Surface(
                color = CovyBackground7,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Button(
                        onClick = onSubmitForReview,
                        colors = ButtonDefaults.buttonColors(containerColor = CovyForestGreen7),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            text = "Envoyer pour examen",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // 1. Badge "INSCRIPTION · CHAUFFEUR"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(CovyLightGreenBg7)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(CovyBadgeTextGreen7)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INSCRIPTION · CHAUFFEUR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CovyBadgeTextGreen7,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Titre principal
            Text(
                text = "Votre véhicule et ses documents",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyTextDark7,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Indicateur d'étape (Étape 2 sur 2)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Véhicule et justificatifs · Étape 2 sur 2",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = CovyForestGreen7
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Barre de progression 100% (Étape 2 complétée)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFE5E7EB))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .fillMaxHeight()
                        .background(CovyForestGreen7)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Marque et modèle
            FormLabel(text = "Marque et modèle", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            CovyTextField(
                value = carModel,
                onValueChange = { carModel = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Année et Couleur (côte à côte)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    FormLabel(text = "Année", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    CovyTextField(
                        value = carYear,
                        onValueChange = { carYear = it }
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    FormLabel(text = "Couleur", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    CovyTextField(
                        value = carColor,
                        onValueChange = { carColor = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Immatriculation
            FormLabel(text = "Immatriculation", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            CovyTextField(
                value = licensePlate,
                onValueChange = { licensePlate = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 7. Permis de conduire (Fichier PDF)
            FormLabel(text = "Permis de conduire", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = driverLicenseFile,
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 8. Assurance (Fichier PDF)
            FormLabel(text = "Assurance", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = insuranceFile,
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 9. Visite technique (Fichier PDF)
            FormLabel(text = "Visite technique", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = technicalInspectionFile,
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Note d'examen obligatoire
            Text(
                text = "Examen des documents avant activation. * Pièces obligatoires.",
                fontSize = 12.sp,
                color = CovyTextMuted7
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ==========================================
// COMPOSANT CARTE DE DOCUMENT TÉLÉVERSÉ
// ==========================================

@Composable
fun UploadedDocumentCard(
    fileName: String,
    onReplaceClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, CovyBorderGreen7, RoundedCornerShape(12.dp))
            .background(CovyLightGreenBg7.copy(alpha = 0.5f))

            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icône de document
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, CovyBorderGreen7, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                tint = CovyForestGreen7,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Nom du fichier & action de remplacement
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fileName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CovyTextDark7

            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Document ajouté · ",
                    fontSize = 12.sp,
                    color = CovyTextMuted7
                )
                Text(
                    text = "Remplacer",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CovyForestGreen7,
                    modifier = Modifier.clickable { onReplaceClick() }
                )
            }
        }
    }
}

// ==========================================
// COMPOSANTS COMMUNS COVY
// ==========================================

@Composable
fun ProgressTopAppBar(stepText: String, onBackClicked: () -> Unit) {
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
                tint = CovyTextDark7
            )
        }

        // Logo Covy
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CovyLightGreenBg7),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌱", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Covy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CovyForestGreen7
            )
        }

        // Compteur d'étape (ex: 13 / 30)
        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextMuted7,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun FormLabel(text: String, isRequired: Boolean) {
    Row {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = CovyTextDark7
        )
        if (isRequired) {
            Text(
                text = " *",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEF4444)
            )
        }
    }
}

@Composable
fun CovyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CovyInputBg7,
            unfocusedContainerColor = CovyInputBg7,
            focusedBorderColor = CovyForestGreen7,
            unfocusedBorderColor = CovyInputBorder7,
            focusedTextColor = CovyTextDark7,
            unfocusedTextColor = CovyTextDark7
        ),
        singleLine = true
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewVehicleAndDocumentsScreen() {
    VehicleAndDocumentsScreen()
}
