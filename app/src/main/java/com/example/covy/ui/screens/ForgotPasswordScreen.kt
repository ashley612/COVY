package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyInfoCard
import com.example.covy.ui.components.CovyTextField
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onSendCode: (phone: String) -> Unit,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var phone by remember { mutableStateOf("") }

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
                    "ACCÈS · RÉCUPÉRATION",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Retrouvez votre compte", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Nous vous envoyons un code pour vérifier votre numéro.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyInfoCard(
                icon = Icons.Outlined.PhoneAndroid,
                title = "Un code à 6 chiffres",
                text = "Gardez votre téléphone à portée de main. Ne partagez jamais ce code."
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CovyTextField(
                    label = "Téléphone associé au compte",
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = "+225 07 01 23 45 67",
                    keyboardType = KeyboardType.Phone,
                    trailingIcon = { Icon(Icons.Outlined.Phone, contentDescription = null) }
                )
                Text(
                    "Après vérification, vous pourrez définir un nouveau mot de passe.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onSendCode(phone) },
                enabled = phone.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Envoyer le code SMS", fontWeight = FontWeight.SemiBold)
            }
            TextButton(onClick = onBackToLogin) {
                Text("Retour à la connexion", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 860)
@Composable
private fun ForgotPasswordScreenPreview() {
    CovyTheme {
        ForgotPasswordScreen(onBack = {}, onSendCode = {}, onBackToLogin = {})
    }
}
