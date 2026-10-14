package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyChoiceCard
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

@Composable
fun StudentProfileScreen(
    onBack: () -> Unit,
    onChooseStudent: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
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
                    "INSCRIPTION · PROFIL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Votre parcours étudiant", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Étudiant ou élève, créez votre compte pour rejoindre votre école ou votre université.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyChoiceCard(
                icon = Icons.Outlined.School,
                title = "Étudiant / Élève",
                text = "Je rejoins mon école ou mon université en covoiturage.",
                actionLabel = "M'inscrire comme passager →",
                onClick = onChooseStudent
            )
        }

        TextButton(
            onClick = onLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Déjà inscrit ? Se connecter", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 860)
@Composable
private fun StudentProfileScreenPreview() {
    CovyTheme {
        StudentProfileScreen(onBack = {}, onChooseStudent = {}, onLogin = {})
    }
}
