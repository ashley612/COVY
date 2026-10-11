package UiChauffeur


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// PALETTE OFFICIELLE COVY (CHARTE GRAPHIQUE)
// ==========================================
val CovyForestGreen4 = Color(0xFF084D38)     // Bouton CTA principal, liens d'action
val CovyTextDark4 = Color(0xFF111827)        // Titre "Retrouvez votre compte", libellés
val CovyTextMuted4 = Color(0xFF6B7280)       // Libellés secondaires, sous-titres
val CovyLightGreenBg4 = Color(0xFFE8F5EE)    // Fond encart informatif téléphone
val CovyBorderCard4 = Color(0xFFE5E7EB)      // Bordure des inputs
val CovyBadgeTextGreen4 = Color(0xFF0F766E)  // Libellé "ACCÈS · RÉCUPÉRATION"
val CovyInputBg4 = Color(0xFFFFFFFF)
val CovyBackground4 = Color(0xFFFFFFFF)

@Composable
fun ResetPasswordAccountScreen(
    onNavigateBack: () -> Unit = {},
    onSendSmsCodeClick: (String) -> Unit = {},
    onBackToLoginClick: () -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("+225 07 01 23 45 67") }

    Scaffold(
        containerColor = CovyBackground4,
        topBar = {
            RecuperationCompte(
                stepText = "03 / 30",
                onBackClicked = onNavigateBack
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Bouton principal "Envoyer le code SMS"
                Button(
                    onClick = { onSendSmsCodeClick(phoneNumber) },
                    colors = ButtonDefaults.buttonColors(containerColor = CovyForestGreen4),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Envoyer le code SMS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action secondaire "Retour à la connexion"
                Text(
                    text = "Retour à la connexion",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CovyForestGreen4,
                    modifier = Modifier
                        .clickable { onBackToLoginClick() }
                        .padding(vertical = 6.dp, horizontal = 12.dp)
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

            // 1. Libellé de section "ACCÈS · RÉCUPÉRATION"
            Text(
                text = "ACCÈS · RÉCUPÉRATION",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CovyBadgeTextGreen4,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Titre principal
            Text(
                text = "Retrouvez votre compte",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyTextDark4,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Sous-titre
            Text(
                text = "Nous vous envoyons un code pour vérifier votre numéro.",
                fontSize = 14.sp,
                color = CovyTextMuted4,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Encart informatif "Un code à 6 chiffres"
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CovyLightGreenBg4),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PhoneIphone,
                        contentDescription = "Téléphone",
                        tint = CovyForestGreen4,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Un code à 6 chiffres",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyTextDark4
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Gardez votre téléphone à portée de main. Ne partagez jamais votre code.",
                        fontSize = 13.sp,
                        color = CovyTextMuted4,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // 5. Libellé du champ de téléphone
            Text(
                text = "Téléphone associé au compte",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextDark4
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Champ de saisie du numéro
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CovyInputBg4,
                    unfocusedContainerColor = CovyInputBg4,
                    focusedBorderColor = CovyForestGreen4,
                    unfocusedBorderColor = CovyBorderCard4,
                    focusedTextColor = CovyTextDark4,
                    unfocusedTextColor = CovyTextDark4
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        tint = CovyTextMuted4,
                        modifier = Modifier.size(20.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 6. Note explicative sous le champ
            Text(
                text = "Après vérification, vous pourrez définir un nouveau mot de passe.",
                fontSize = 13.sp,
                color = CovyTextMuted4,
                lineHeight = 18.sp
            )
        }
    }
}


// ==========================================
// COMPOSANT EN-TÊTE COMMUN COVY
// ==========================================
@Composable
fun RecuperationCompte(stepText: String, onBackClicked: () -> Unit) {
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
                tint = CovyTextDark4
            )
        }

        // Logo Covy
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CovyLightGreenBg4),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌱", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Covy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CovyForestGreen4
            )
        }

        // Indicateur d'étape (03 / 30)
        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextMuted4,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewResetPasswordAccountScreen() {
    ResetPasswordAccountScreen()
}
