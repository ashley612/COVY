package UiChauffeur



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// PALETTE OFFICIELLE COVY (CHARTE GRAPHIQUE)
// ==========================================
val CovyForestGreen3 = Color(0xFF084D38)     // Bouton CTA principal, liens d'action
val CovyTextDark3 = Color(0xFF111827)        // Titre "Vérifiez votre numéro", chiffres OTP
val CovyTextMuted3 = Color(0xFF6B7280)       // Libellés secondaires, sous-titres
val CovyLightGreenBg3 = Color(0xFFE8F5EE)    // Fond carte SMS & icônes
val CovyBorderCard3 = Color(0xFFE5E7EB)      // Bordure des cases OTP
val CovyBadgeTextGreen3= Color(0xFF0F766E)  // Libellé "ACCÈS · VÉRIFICATION"
val CovyBackground3 = Color(0xFFFFFFFF)

@Composable
fun VerifyNumberOtpScreen(
    phoneNumberMasked: String = "+225 07 •• •• •• 67.",
    onNavigateBack: () -> Unit = {},
    onVerifyCodeClick: (String) -> Unit = {},
    onResendCodeClick: () -> Unit = {},
    onEditNumberClick: () -> Unit = {}
) {
    var otpCode by remember { mutableStateOf("382916") }

    Scaffold(
        containerColor = CovyBackground3,
        topBar = {
            SmsChauffeurCovy(
                stepText = "04 / 30",
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
                // Bouton principal "Vérifier le code"
                Button(
                    onClick = { onVerifyCodeClick(otpCode) },
                    colors = ButtonDefaults.buttonColors(containerColor = CovyForestGreen4),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Vérifier le code",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action secondaire "Modifier le numéro"
                Text(
                    text = "Modifier le numéro",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CovyForestGreen4,
                    modifier = Modifier
                        .clickable { onEditNumberClick() }
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

            // 1. Libellé de section "ACCÈS · VÉRIFICATION"
            Text(
                text = "ACCÈS · VÉRIFICATION",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CovyBadgeTextGreen3,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Titre principal
            Text(
                text = "Vérifiez votre numéro",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CovyTextDark3,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Sous-titre avec numéro masqué
            Text(
                text = "Code envoyé au $phoneNumberMasked",
                fontSize = 14.sp,
                color = CovyTextMuted3,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Encart informatif "Consultez vos SMS"
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CovyLightGreenBg3),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "SMS",
                        tint = CovyBadgeTextGreen3,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Consultez vos SMS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CovyTextDark3
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Saisissez les 6 chiffres reçus pour continuer.",
                        fontSize = 13.sp,
                        color = CovyTextMuted3
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 5. Code de vérification (Libellé + 6 cellules de saisie)
            Text(
                text = "Code de vérification",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CovyTextDark3
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Grille des 6 cases pour le code OTP
            OtpCodeInputBoxes(
                code = otpCode,
                onCodeChange = { if (it.length <= 6) otpCode = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 6. Renvoyer le code
            Text(
                text = "Vous n'avez pas reçu de code ?",
                fontSize = 13.sp,
                color = CovyTextMuted3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Renvoyer le code",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CovyForestGreen4,
                modifier = Modifier
                    .clickable { onResendCodeClick() }
                    .padding(vertical = 4.dp)
            )
        }
    }
}


// ==========================================
// COMPOSANT DES CELLULES DE CODE OTP
// ==========================================
@Composable
fun OtpCodeInputBoxes(
    code: String,
    onCodeChange: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Rangée visuelle des 6 cases
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (index in 0 until 6) {
                val digit = code.getOrNull(index)?.toString() ?: ""
                val isFocused = code.length == index

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = if (isFocused) CovyForestGreen4 else CovyBorderCard3
                    ),
                    modifier = Modifier
                        .size(width = 48.dp, height = 58.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = digit,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = CovyTextDark3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Champ invisible pour gérer la saisie clavier native
        BasicTextField(
            value = code,
            onValueChange = { input ->
                if (input.all { it.isDigit() } && input.length <= 6) {
                    onCodeChange(input)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 4.dp),
            decorationBox = { /* Transparent / invisible par dessus les cases */ }
        )
    }
}

// ==========================================
// COMPOSANT EN-TÊTE COMMUN COVY
// ==========================================
@Composable
fun  SmsChauffeurCovy(stepText: String, onBackClicked: () -> Unit) {
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
                tint = CovyTextDark3
            )
        }

        // Logo Covy
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CovyLightGreenBg3),
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

        // Indicateur d'étape (ex: 04 / 30)
        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stepText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CovyTextMuted3,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewVerifyNumberOtpScreen() {
    VerifyNumberOtpScreen()
}
