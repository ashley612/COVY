package com.example.covy.UiChauffeur

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.R
import com.example.covy.model.DocumentType
import com.example.covy.ui.theme.CovyTheme
import com.example.covy.viewmodel.InscriptionViewModel

// ==========================================
// PALETTE CLAIRE COVY (forcée : pas de mode sombre sur cet écran)
// ==========================================

val CovyLightColors = lightColorScheme(
    primary = Color(0xFF0F6B4F),               // vert Covy
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDDF3E6),      // menthe
    onPrimaryContainer = Color(0xFF0A4C38),
    secondary = Color(0xFF8A5A00),             // texte sur jaune (lisible)
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFDF1D6),    // jaune doux du logo
    onSecondaryContainer = Color(0xFF5C3C00),
    background = Color(0xFFF7F9F8),
    onBackground = Color(0xFF14211C),
    surface = Color.White,
    onSurface = Color(0xFF14211C),
    surfaceVariant = Color(0xFFEDF3EF),
    onSurfaceVariant = Color(0xFF5F6B66),
    outline = Color(0xFFCBD6D0),
    outlineVariant = Color(0xFFE3EAE6),
    error = Color(0xFFB3261E)
)

private const val TOTAL_STEPS = 3

@Composable
fun EcranInscriptionChauffeur(
    viewModel: InscriptionViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onContinueToVehicle: () -> Unit = {}
) {
    // On force le thème clair pour tout l'écran
    MaterialTheme(
        colorScheme = CovyLightColors,
        typography = MaterialTheme.typography
    ) {
        EcranInscriptionChauffeurContent(
            viewModel = viewModel,
            onNavigateBack = onNavigateBack,
            onContinueToVehicle = onContinueToVehicle
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EcranInscriptionChauffeurContent(
    viewModel: InscriptionViewModel,
    onNavigateBack: () -> Unit,
    onContinueToVehicle: () -> Unit
) {
    // Étape 1 = Identité, étape 2 = Documents, (étape 3 = Véhicule, autre écran)
    var step by rememberSaveable { mutableIntStateOf(1) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    // TODO : à déplacer dans InscriptionViewModel (birthDate, photoUri)
    var birthDate by rememberSaveable { mutableStateOf("") }          // 8 chiffres : JJMMAAAA
    var photoUri by rememberSaveable { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()
    LaunchedEffect(step) { scrollState.scrollTo(0) }

    val photoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> photoUri = uri?.toString() }

    val categoriesList = listOf(
        "B (Véhicules légers / Berlines)",
        "A (Motos & Deux-roues)",
        "C (Poids lourds)",
        "D (Transport en commun / Minibus)",
        "BC (Véhicules utilitaires & Berlines)"
    )

    val goBack: () -> Unit = {
        if (step > 1) step -= 1 else onNavigateBack()
    }
    BackHandler(enabled = step > 1) { step -= 1 }

    val onNext: () -> Unit = {
        if (step == 1) {
            localError = when {
                viewModel.firstName.isBlank() || viewModel.lastName.isBlank() ->
                    "Renseignez votre prénom et votre nom."
                birthDate.length < 8 ->
                    "Entrez votre date de naissance (JJ / MM / AAAA)."
                viewModel.phone.length < 8 ->
                    "Entrez un numéro de téléphone valide."
                viewModel.password.length < 6 ->
                    "Le mot de passe doit contenir au moins 6 caractères."
                else -> null
            }
            if (localError == null) step = 2
        } else {
            viewModel.continuerVersVehicule { onContinueToVehicle() }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            InscriptionTopAppBar(
                stepText = "$step / $TOTAL_STEPS",
                onBackClicked = goBack
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Button(
                        onClick = onNext,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(
                            text = if (step == 1) "Suivant" else "Continuer vers le véhicule",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(18.dp)
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
                .imePadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {


            Spacer(modifier = Modifier.height(12.dp))

            // Titre & sous-titre
            Text(
                text = if (step == 1) "Prenez le volant" else "Vos documents",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (step == 1)
                    "Commençons par votre identité et votre photo."
                else
                    "Votre pièce d'identité et votre permis, pour la vérification.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            StepIndicator(
                current = step,
                total = TOTAL_STEPS,
                label = if (step == 1) "Identité" else "Documents"
            )

            Spacer(modifier = Modifier.height(22.dp))

            if (step == 1) {
                // ───────── ÉTAPE 1 : IDENTITÉ ─────────

                // Photo
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        onClick = {
                            photoLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        border = androidx.compose.foundation.BorderStroke(
                            1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                        ),
                        modifier = Modifier.size(84.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (photoUri == null) Icons.Default.Add else Icons.Default.Check,
                                contentDescription = "Ajouter une photo personnelle",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Photo personnelle",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = if (photoUri == null)
                                "Visage bien visible, bonne lumière"
                            else
                                "Photo ajoutée · touchez pour modifier",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Prénom & Nom
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        InscriLabel(text = "Prénom", isRequired = true)
                        Spacer(modifier = Modifier.height(6.dp))
                        InscriptionTopBar(
                            value = viewModel.firstName,
                            onValueChange = { viewModel.onFirstNameChange(it) },
                            placeholder = "Karim"
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        InscriLabel(text = "Nom", isRequired = true)
                        Spacer(modifier = Modifier.height(6.dp))
                        InscriptionTopBar(
                            value = viewModel.lastName,
                            onValueChange = { viewModel.onLastNameChange(it) },
                            placeholder = "Kouassi"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Date de naissance
                InscriLabel(text = "Date de naissance", isRequired = true)
                Spacer(modifier = Modifier.height(6.dp))
                InscriptionTopBar(
                    value = birthDate,
                    onValueChange = { input -> birthDate = input.filter { it.isDigit() }.take(8) },
                    placeholder = "JJ / MM / AAAA",
                    keyboardType = KeyboardType.Number,
                    visualTransformation = DateVisualTransformation(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Téléphone
                InscriLabel(text = "Téléphone", isRequired = true)
                Spacer(modifier = Modifier.height(6.dp))
                InscriptionTopBar(
                    value = viewModel.phone,
                    onValueChange = { viewModel.onPhoneChange(it) },
                    placeholder = "07 88 12 34 56",
                    keyboardType = KeyboardType.Phone,
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 14.dp, end = 8.dp)
                        ) {
                            Text(
                                text = "+225",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(22.dp)
                                    .background(MaterialTheme.colorScheme.outline)
                            )
                        }
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mot de passe
                InscriLabel(text = "Mot de passe", isRequired = true)
                Spacer(modifier = Modifier.height(6.dp))
                InscriptionTopBar(
                    value = viewModel.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = "6 caractères minimum",
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if (isPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisible)
                                    "Masquer le mot de passe"
                                else
                                    "Afficher le mot de passe",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                )
            } else {
                // ───────── ÉTAPE 2 : DOCUMENTS ─────────

                // Pièce d'identité
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InscriLabel(text = "Pièce d'identité officielle", isRequired = true)
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Obligatoire",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sélecteur CNI / Passeport
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    DocTypeTabItem(
                        label = "Carte CNI",
                        icon = Icons.Default.Badge,
                        isSelected = viewModel.selectedDocType == DocumentType.CNI,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.onDocTypeChange(DocumentType.CNI) }
                    )
                    DocTypeTabItem(
                        label = "Passeport",
                        icon = Icons.Default.Public,
                        isSelected = viewModel.selectedDocType == DocumentType.PASSEPORT,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.onDocTypeChange(DocumentType.PASSEPORT) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Numéro de la pièce
                InscriptionTopBar(
                    value = viewModel.docNumber,
                    onValueChange = { viewModel.onDocNumberChange(it) },
                    placeholder = "Ex: CI-00389281-22",
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.CreditCard,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                UploadBox(
                    title = "Photo CNI ou Passeport",
                    subtitle = "Recto/Verso lisible ou page passeport",
                    onAddClick = { /* Action sélecteur fichier */ }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Permis de conduire
                InscriLabel(text = "Numéro du permis de conduire", isRequired = true)
                Spacer(modifier = Modifier.height(6.dp))
                InscriptionTopBar(
                    value = viewModel.licenseNumber,
                    onValueChange = { viewModel.onLicenseNumberChange(it) },
                    placeholder = "Ex: CI-B-204867"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Catégorie du permis
                InscriLabel(text = "Catégorie du permis", isRequired = true)
                Spacer(modifier = Modifier.height(6.dp)) // (20.dp dans l'ancien code : trop grand)
                ExposedDropdownMenuBox(
                    expanded = isCategoryDropdownExpanded,
                    onExpandedChange = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = viewModel.licenseCategory,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                        shape = RoundedCornerShape(14.dp),
                        colors = defaultTextFieldColors(),
                        placeholder = {
                            Text(
                                "Choisir la catégorie",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryDropdownExpanded)
                        },
                        singleLine = true
                    )

                    ExposedDropdownMenu(
                        expanded = isCategoryDropdownExpanded,
                        onDismissRequest = { isCategoryDropdownExpanded = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        categoriesList.forEach { category ->
                            val selected = category == viewModel.licenseCategory
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = category,
                                        fontSize = 14.sp,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    viewModel.onLicenseCategoryChange(category)
                                    isCategoryDropdownExpanded = false
                                },
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Message d'erreur (local à l'étape 1, ViewModel à l'étape 2)
            val error = if (step == 1) localError else viewModel.errorMessage
            error?.let {
                ErrorBanner(text = it)
                Spacer(modifier = Modifier.height(10.dp))
            }

            Text(
                text = "* Champs obligatoires pour la vérification réglementaire",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ==========================================
// COMPOSANTS
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
            modifier = Modifier.size(44.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Retour",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.covy_logo),
                contentDescription = "Logo Covy",
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Covy",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

/** Indicateur "Identité · Étape 1 sur 3" + 3 segments de progression. */
@Composable
fun StepIndicator(current: Int, total: Int, label: String) {
    Column {
        Text(
            text = "$label · Étape $current sur $total",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(total) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            if (index < current) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        )
                )
            }
        }
    }
}

@Composable
fun InscriLabel(text: String, isRequired: Boolean) {
    Row {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (isRequired) {
            Text(
                text = " *",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

/**
 * Champ texte standard de l'inscription (nom conservé pour ne rien casser ailleurs).
 * Les nouveaux paramètres ont des valeurs par défaut : les anciens appels fonctionnent toujours.
 */
@Composable
fun InscriptionTopBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = defaultTextFieldColors(),
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 15.sp)
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
        shadowElevation = if (isSelected) 1.dp else 0.dp,
        modifier = modifier.height(44.dp) // 44dp = cible tactile confortable
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
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
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
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
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        OutlinedButton(
            onClick = onAddClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = "Ajouter",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ErrorBanner(text: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.error.copy(alpha = 0.08f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun defaultTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    focusedTextColor = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    cursorColor = MaterialTheme.colorScheme.primary
)

/** Affiche 12062001 sous la forme 12/06/2001 pendant la saisie (le texte stocké reste 8 chiffres). */
class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.take(8)
        val out = buildString {
            digits.forEachIndexed { i, c ->
                append(c)
                if ((i == 1 || i == 3) && i != digits.lastIndex) append('/')
            }
        }
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val t = when {
                    offset <= 1 -> offset
                    offset <= 3 -> offset + 1
                    else -> offset + 2
                }
                return t.coerceAtMost(out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val o = when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    else -> offset - 2
                }
                return o.coerceIn(0, digits.length)
            }
        }
        return TransformedText(AnnotatedString(out), mapping)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewEcranInscriptionChauffeur() {
    CovyTheme {
        EcranInscriptionChauffeur()
    }
}