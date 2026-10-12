package com.example.covy.UiChauffeur



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// PALETTE DE COULEURS OFFICIELLE COVY
// ==========================================
val CovyForestGreen1 = Color(0xFF084D38)     // Bouton CTA principal, barre de progression active
val CovyTextDark1 = Color(0xFF111827)        // Titre "Prenez le volant", textes principaux
val CovyTextMuted1 = Color(0xFF6B7280)       // Sous-titres, libellés secondaires
val CovyLightGreenBg1 = Color(0xFFE8F5EE)    // Badge d'en-tête, fond sélectionné CNI
val CovyBorderGreen1 = Color(0xFFBBE5D0)     // Bordures d'encarts de téléversement
val CovyInputBg1 = Color(0xFFF9FAFB)         // Arrière-plan des champs de saisie
val CovyInputBorder1 = Color(0xFFE5E7EB)      // Bordure des inputs inactifs
val CovyBadgeTextGreen1 = Color(0xFF0F766E)  // Texte des badges "Obligatoire" / "Chauffeur"
val CovyBackground1 = Color(0xFFFFFFFF)

enum class DocumentType { CNI, PASSEPORT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranInscriptionChauffeur(
    onNavigateBack: () -> Unit = {},
    onContinueToVehicle: () -> Unit = {}
) {
    var firstName by remember { mutableStateOf("Karim") }
    var lastName by remember { mutableStateOf("Kouassi") }
    var phone by remember { mutableStateOf("07 88 12 34 56") }
    var password by remember { mutableStateOf("1234567890") }
    var selectedDocType by remember { mutableStateOf(DocumentType.CNI) }
    var docNumber by remember { mutableStateOf("CI-00389281-22") }
    var licenseNumber by remember { mutableStateOf("CI-B-204867") }

    // État pour la catégorie de permis
    var licenseCategory by remember { mutableStateOf("B (Véhicules légers / Berlines)") }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    val categoriesList = listOf(
        "B (Véhicules légers / Berlines)",
        "A (Motos & Deux-roues)",
        "C (Poids lourds)",
        "D (Transport en commun / Minibus)",
        "BC (Véhicules utilitaires & Berlines)"
    )


    Scaffold(
        containerColor = CovyBackground1,
        topBar = {
            InscriptionTopAppBar(
                stepText = "12 / 30",
                onBackClicked = onNavigateBack
            )
        },
        bottomBar = {
            Surface(
                color = CovyBackground1,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Button(
                        onClick = onContinueToVehicle,
                        colors = ButtonDefaults.buttonColors(containerColor = CovyForestGreen1),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Continuer vers le véhicule",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
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
                    .background(CovyLightGreenBg1)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(CovyBadgeTextGreen1)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INSCRIPTION · CHAUFFEUR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CovyBadgeTextGreen1,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Titre principal & sous-titre
            Text(
                text = "Prenez le volant",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyTextDark1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Commençons par votre identité et vos documents officiels.",
                fontSize = 13.sp,
                color = CovyTextMuted1,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Indicateur d'étape
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .border(2.dp, CovyForestGreen1, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(CovyForestGreen1)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Identité · Étape 1 sur 2",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyForestGreen1
                    )
                }
                Text(
                    text = "Véhicule ensuite",
                    fontSize = 12.sp,
                    color = CovyTextMuted1
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Ligne de progression (50%)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFE5E7EB))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .background(CovyForestGreen1)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Prénom et Nom côte à côte
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    InscriLabel(text = "Prénom", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    InscriptionTopBar(value = firstName, onValueChange = { firstName = it })
                }
                Column(modifier = Modifier.weight(1f)) {
                    InscriLabel(text = "Nom", isRequired = true)
                    Spacer(modifier = Modifier.height(6.dp))
                    InscriptionTopBar(value = lastName, onValueChange = { lastName = it })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Téléphone (+225)
            InscriLabel(text = "Téléphone", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = defaultTextFieldColors(),
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 12.dp, end = 6.dp)
                    ) {
                        Text(text = "🇨🇮", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "+225",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = CovyTextMuted1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(20.dp)
                                .background(CovyInputBorder1)
                        )
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = CovyTextMuted1,
                        modifier = Modifier.size(18.dp)
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Mot de passe
            InscriLabel(text = "Mot de passe", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = defaultTextFieldColors(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "Afficher/Masquer",
                        tint = CovyTextMuted1,
                        modifier = Modifier.size(20.dp)
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 7. Pièce d'identité officielle (Obligatoire)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InscriLabel(text = "Pièce d'identité officielle", isRequired = true)
                Surface(
                    color = CovyLightGreenBg1,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Obligatoire",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CovyBadgeTextGreen1,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sélecteur CNI / Passeport
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF3F4F6))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DocTypeTabItem(
                    label = "Carte CNI",
                    icon = Icons.Default.Badge,
                    isSelected = selectedDocType == DocumentType.CNI,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedDocType = DocumentType.CNI }
                )
                DocTypeTabItem(
                    label = "Passeport",
                    icon = Icons.Default.Public,
                    isSelected = selectedDocType == DocumentType.PASSEPORT,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedDocType = DocumentType.PASSEPORT }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Numéro de la pièce d'identité
            OutlinedTextField(
                value = docNumber,
                onValueChange = { docNumber = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = defaultTextFieldColors(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = null,
                        tint = CovyTextMuted1,
                        modifier = Modifier.size(18.dp)
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Encart ajout photo justificatif
            UploadBox(
                title = "Photo CNI ou Passeport",
                subtitle = "Recto/Verso lisible ou page passeport",
                onAddClick = { /* Action sélecteur fichier */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 8. Permis de conduire
            InscriLabel(text = "Numéro du permis de conduire", isRequired = true)
            Spacer(modifier = Modifier.height(6.dp))
            InscriptionTopBar(value = licenseNumber, onValueChange = { licenseNumber = it })

            Spacer(modifier = Modifier.height(16.dp))

            // 9. Catégorie du permis
            InscriLabel(text = "Catégorie du permis", isRequired = true)
            Spacer(modifier = Modifier.height(20.dp))
            ExposedDropdownMenuBox(
                expanded = isCategoryDropdownExpanded,
                onExpandedChange = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = licenseCategory,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    shape = RoundedCornerShape(12.dp),
                    colors = defaultTextFieldColors(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryDropdownExpanded)
                    },
                    singleLine = true
                )

                ExposedDropdownMenu(
                    expanded = isCategoryDropdownExpanded,
                    onDismissRequest = { isCategoryDropdownExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    categoriesList.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = category,
                                    fontSize = 14.sp,
                                    fontWeight = if (category == licenseCategory) FontWeight.Bold else FontWeight.Normal,
                                    color = if (category == licenseCategory) CovyForestGreen1 else CovyTextDark1
                                )
                            },
                            onClick = {
                                licenseCategory = category
                                isCategoryDropdownExpanded = false
                            },
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Note informative obligatoire
            Text(
                text = "* Champs obligatoires pour la vérification réglementaire",
                fontSize = 11.sp,
                color = CovyTextMuted1
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}





// ==========================================
// COMPOSANTS RÉUTILISABLES
// ==========================================

@Composable
fun InscriptionTopAppBar(stepText: String, onBackClicked: () -> Unit) {
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
                tint = CovyTextDark1
            )
        }

        // Logo Covy
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CovyLightGreenBg1),
                contentAlignment = Alignment.Center
            ) {
                // Symbole stylisé Covy
                Text(text = "🌱", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Covy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CovyForestGreen1
            )
        }

        // Indicateur d'étape (ex: 12 / 30)
        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextMuted1,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun  InscriLabel(text: String, isRequired: Boolean) {
    Row {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = CovyTextDark1
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
fun  InscriptionTopBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = defaultTextFieldColors(),
        singleLine = true
    )
}

@Composable
fun DocTypeTabItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color.White else Color.Transparent,
        shadowElevation = if (isSelected) 1.dp else 0.dp,
        modifier = modifier.height(38.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) CovyForestGreen1 else CovyTextMuted1,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) CovyForestGreen1 else CovyTextMuted1
            )
        }
    }
}

@Composable
fun UploadBox(
    title: String,
    subtitle: String,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, CovyBorderGreen1, RoundedCornerShape(12.dp))
            .background(CovyLightGreenBg1.copy(alpha = 0.35f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, CovyBorderGreen1, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    tint = CovyForestGreen1,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CovyTextDark1
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = CovyTextMuted1
                )
            }
        }

        OutlinedButton(
            onClick = onAddClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = CovyTextDark1
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.height(34.dp)
        ) {
            Text(
                text = "Ajouter",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun defaultTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = CovyInputBg1,
    unfocusedContainerColor = CovyInputBg1,
    focusedBorderColor = CovyForestGreen1,
    unfocusedBorderColor = CovyInputBorder1,
    focusedTextColor = CovyTextDark1,
    unfocusedTextColor = CovyTextDark1
)

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewEcranInscriptionChauffeur() {
    EcranInscriptionChauffeur()
}
