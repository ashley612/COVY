package com.example.covy.UiChauffeur

// ✅ Nouveaux imports pour le ViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.viewmodel.VehiculeViewModel

// ==========================================
// PALETTE OFFICIELLE COVY
// ==========================================
val CovyForestGreen7: Color = Color(0xFF084D38)
val CovyTextDark7 = Color(0xFF111827)
val CovyTextMuted7 = Color(0xFF6B7280)
val CovyLightGreenBg7 = Color(0xFFE8F5EE)
val CovyBorderGreen7 = Color(0xFFBBE5D0)
val CovyInputBg7 = Color(0xFFF9FAFB)
val CovyInputBorder7 = Color(0xFFE5E7EB)
val CovyBadgeTextGreen7 = Color(0xFF0F766E)
val CovyBackground7 = Color(0xFFFFFFFF)

@Composable
fun VehicleAndDocumentsScreen(
    // ✅ Ajout du ViewModel
    viewModel: VehiculeViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onSubmitForReview: () -> Unit = {}
) {
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
                        // ✅ Utilise le ViewModel pour valider et envoyer
                        onClick = {
                            viewModel.envoyerPourExamen {
                                onSubmitForReview()
                            }
                        },
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

            // Barre de progression 100%
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
                value = viewModel.carModel,                             // ✅ ViewModel
                onValueChange = { viewModel.onCarModelChange(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Année et Couleur
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    FormLabel(text = "Année", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    CovyTextField(
                        value = viewModel.carYear,                       // ✅ ViewModel
                        onValueChange = { viewModel.onCarYearChange(it) }
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    FormLabel(text = "Couleur", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    CovyTextField(
                        value = viewModel.carColor,                      // ✅ ViewModel
                        onValueChange = { viewModel.onCarColorChange(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Immatriculation
            FormLabel(text = "Immatriculation", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            CovyTextField(
                value = viewModel.licensePlate,                          // ✅ ViewModel
                onValueChange = { viewModel.onLicensePlateChange(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 7. Permis de conduire
            FormLabel(text = "Permis de conduire", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = viewModel.driverLicenseFile ?: "Aucun fichier",  // ✅ ViewModel
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 8. Assurance
            FormLabel(text = "Assurance", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = viewModel.insuranceFile ?: "Aucun fichier",     // ✅ ViewModel
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 9. Visite technique
            FormLabel(text = "Visite technique", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = viewModel.technicalInspectionFile ?: "Aucun fichier",  // ✅ ViewModel
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Message d'erreur éventuel
            viewModel.errorMessage?.let { error ->
                Text(
                    text = error,
                    fontSize = 13.sp,
                    color = Color(0xFFEF4444),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

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

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fileName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CovyTextDark7
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
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