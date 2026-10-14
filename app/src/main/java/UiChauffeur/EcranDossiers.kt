package com.example.covy.UiChauffeur


import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.covy.ui.theme.CovyTheme
import com.example.covy.viewmodel.VehiculeViewModel
import com.example.covy.R
@Composable
fun VehicleAndDocumentsScreen(
    viewModel: VehiculeViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onSubmitForReview: () -> Unit = {}
) {
    Scaffold(
        // ✅ MaterialTheme.background
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ProgressTopAppBar(
                stepText = "13 / 30",
                onBackClicked = onNavigateBack
            )
        },
        bottomBar = {
            Surface(
                // ✅ surface
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.envoyerPourExamen {
                                onSubmitForReview()
                            }
                        },
                        // ✅ primary
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            text = "Envoyer pour examen",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            // ✅ onPrimary
                            color = MaterialTheme.colorScheme.onPrimary
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
            // Badge "INSCRIPTION · CHAUFFEUR"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    // ✅ primaryContainer
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        // ✅ secondary
                        .background(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INSCRIPTION · CHAUFFEUR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    // ✅ secondary
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Titre
            Text(
                text = "Votre véhicule et ses documents",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                // ✅ onBackground
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Indicateur d'étape
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Véhicule et justificatifs · Étape 2 sur 2",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    // ✅ primary
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Barre de progression
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    // ✅ outlineVariant
                    .background(MaterialTheme.colorScheme.outlineVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .fillMaxHeight()
                        // ✅ primary
                        .background(MaterialTheme.colorScheme.primary)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Marque et modèle
            FormLabel(text = "Marque et modèle", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            CovyTextField(
                value = viewModel.carModel,
                onValueChange = { viewModel.onCarModelChange(it) },
                placeholder = "Ex: Toyota Corolla"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Année & Couleur
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    FormLabel(text = "Année", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    CovyTextField(
                        value = viewModel.carYear,
                        onValueChange = { viewModel.onCarYearChange(it) },
                        placeholder = "Ex: 2021"
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    FormLabel(text = "Couleur", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    CovyTextField(
                        value = viewModel.carColor,
                        onValueChange = { viewModel.onCarColorChange(it) },
                        placeholder = "Ex: Blanche"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Immatriculation
            FormLabel(text = "Immatriculation", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            CovyTextField(
                value = viewModel.licensePlate,
                onValueChange = { viewModel.onLicensePlateChange(it) },
                placeholder = "Ex: AB 1234 CD"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 7. Permis de conduire
            FormLabel(text = "Permis de conduire", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = viewModel.driverLicenseFile ?: "Aucun fichier",
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 8. Assurance
            FormLabel(text = "Assurance", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = viewModel.insuranceFile ?: "Aucun fichier",
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 9. Visite technique
            FormLabel(text = "Visite technique", isRequired = true)
            Spacer(modifier = Modifier.height(8.dp))
            UploadedDocumentCard(
                fileName = viewModel.technicalInspectionFile ?: "Aucun fichier",
                onReplaceClick = { /* Action pour changer le document */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Message d'erreur
            viewModel.errorMessage?.let { error ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = error,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = "Examen des documents avant activation. * Pièces obligatoires.",
                fontSize = 12.sp,
                // ✅ onSurfaceVariant
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ==========================================
// COMPOSANT CARTE DE DOCUMENT
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
            // ✅ outlineVariant
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            // ✅ primaryContainer avec alpha
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                // ✅ surface
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                // ✅ primary
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fileName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                // ✅ onSurface
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Document ajouté · ",
                    fontSize = 12.sp,
                    // ✅ onSurfaceVariant
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Remplacer",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    // ✅ primary
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onReplaceClick() }
                )
            }
        }
    }
}

// ==========================================
// COMPOSANTS COMMUNS
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
                // ✅ onBackground
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

@Composable
fun FormLabel(text: String, isRequired: Boolean) {
    Row {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            // ✅ onBackground
            color = MaterialTheme.colorScheme.onBackground
        )
        if (isRequired) {
            Text(
                text = " *",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                // ✅ error
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun CovyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        // ✅ MaterialTheme colors
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
            }
        },
        singleLine = true
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewVehicleAndDocumentsScreen() {
    CovyTheme {
        VehicleAndDocumentsScreen()
    }
}