package UiChauffeur



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// PALETTE OFFICIELLE COVY (CHARTE GRAPHIQUE)
// ==========================================
val CovyForestGreen5 = Color(0xFF084D38)     // Bouton CTA principal, liens d'action
val CovyTextDark5 = Color(0xFF111827)        // Titre "Bon retour !", libellés principaux
val CovyTextMuted5 = Color(0xFF6B7280)       // Libellés secondaires, sous-titres
val CovyLightGreenBg5 = Color(0xFFE8F5EE)    // Fond carte clé & badges
val CovyBorderCard5 = Color(0xFFE5E7EB)      // Bordure des inputs inactifs
val CovyBadgeTextGreen5 = Color(0xFF0F766E)  // Libellé "ACCÈS AU COMPTE"
val CovyInputBg5 = Color(0xFFFFFFFF)
val CovyBackground5 = Color(0xFFFFFFFF)

@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit = {},
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onForgotPasswordClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("+225 07 01 23 45 67") }
    var password by remember { mutableStateOf("••••••••••") }

    Scaffold(
        containerColor = CovyBackground5,
        topBar = {
            ConnexionChauffeur(
                stepText = "02 / 30",
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
                // Bouton principal "Se connecter"
                Button(
                    onClick = { onLoginClick(phoneNumber, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = CovyForestGreen5),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Se connecter",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action secondaire "Nouveau sur Covy ? Créer un compte"
                Row(
                    modifier = Modifier
                        .clickable { onRegisterClick() }
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nouveau sur Covy ? ",
                        fontSize = 14.sp,
                        color = CovyForestGreen5,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Créer un compte",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyForestGreen5
                    )
                }
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

            // 1. Libellé de section "ACCÈS AU COMPTE"
            Text(
                text = "ACCÈS AU COMPTE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CovyBadgeTextGreen5,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Titre principal
            Text(
                text = "Bon retour !",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyTextDark5,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Sous-titre
            Text(
                text = "Retrouvez vos trajets et votre espace Covy.",
                fontSize = 14.sp,
                color = CovyTextMuted5,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Encart informatif "Votre compte, votre trajet."
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CovyLightGreenBg5),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Key,
                        contentDescription = "Clé",
                        tint = CovyForestGreen5,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Votre compte, votre trajet.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyTextDark5
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Utilisez le numéro associé à votre compte, que vous soyez passager ou chauffeur.",
                        fontSize = 13.sp,
                        color = CovyTextMuted5,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // 5. Champ Numéro de téléphone
            Text(
                text = "Numéro de téléphone",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextDark5
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CovyInputBg5,
                    unfocusedContainerColor = CovyInputBg5,
                    focusedBorderColor = CovyForestGreen5,
                    unfocusedBorderColor = CovyBorderCard5,
                    focusedTextColor = CovyTextDark5,
                    unfocusedTextColor = CovyTextDark5
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        tint = CovyTextMuted5,
                        modifier = Modifier.size(20.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 6. Champ Mot de passe
            Text(
                text = "Mot de passe",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextDark5
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CovyInputBg5,
                    unfocusedContainerColor = CovyInputBg5,
                    focusedBorderColor = CovyForestGreen5,
                    unfocusedBorderColor = CovyBorderCard5,
                    focusedTextColor = CovyTextDark5,
                    unfocusedTextColor = CovyTextDark5
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.VisibilityOff,
                        contentDescription = "Afficher/Masquer",
                        tint = CovyTextMuted5,
                        modifier = Modifier.size(20.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 7. Lien "Mot de passe oublié ?"
            Text(
                text = "Mot de passe oublié ?",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyForestGreen5,
                modifier = Modifier
                    .clickable { onForgotPasswordClick() }
                    .padding(vertical = 4.dp)
            )
        }
    }
}

// ==========================================
// COMPOSANT EN-TÊTE COMMUN COVY
// ==========================================
@Composable
fun ConnexionChauffeur(stepText: String, onBackClicked: () -> Unit) {
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
                tint = CovyTextDark5
            )
        }

        // Logo Covy
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CovyLightGreenBg5),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌱", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Covy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CovyForestGreen5
            )
        }

        // Indicateur d'étape (02 / 30)
        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextMuted5,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}
