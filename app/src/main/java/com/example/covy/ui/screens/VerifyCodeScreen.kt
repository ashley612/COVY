package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyInfoCard
import com.example.covy.ui.components.CovyOtpField
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

@Composable
fun VerifyCodeScreen(
    maskedPhone: String,
    onBack: () -> Unit,
    onVerify: (code: String) -> Unit,
    onResend: () -> Unit,
    onChangeNumber: () -> Unit,
    modifier: Modifier = Modifier
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 36.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "ACCÈS · VÉRIFICATION",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Vérifiez votre numéro", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Code envoyé au $maskedPhone.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyInfoCard(
                icon = Icons.Outlined.ChatBubbleOutline,
                title = "Consultez vos SMS",
                text = "Saisissez les 6 chiffres reçus pour continuer."
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Code de vérification", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                CovyOtpField(code = code, onCodeChange = { code = it })
            }

            Column {
                Text(
                    "Vous n'avez pas reçu de code ?",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(onClick = onResend, contentPadding = PaddingValues(0.dp)) {
                    Text("Renvoyer le code", fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onVerify(code) },
                enabled = code.length == 6,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Vérifier le code", fontWeight = FontWeight.SemiBold)
            }
            TextButton(onClick = onChangeNumber) {
                Text("Modifier le numéro", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 860)
@Composable
private fun VerifyCodeScreenPreview() {
    CovyTheme {
        VerifyCodeScreen(
            maskedPhone = "+225 07 •• •• •• 67",
            onBack = {}, onVerify = {}, onResend = {}, onChangeNumber = {}
        )
    }
}
