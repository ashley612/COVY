package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovySelectableCard
import com.example.covy.ui.components.CovyStepProgress
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

enum class SchoolingType { ETUDIANT, ELEVE }

@Composable
fun SchoolingTypeScreen(
    onBack: () -> Unit,
    onContinue: (SchoolingType) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(SchoolingType.ETUDIANT) }

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
                    "INSCRIPTION · PASSAGER",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Votre scolarité", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Un espace passager commun, un dossier adapté à votre situation.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyStepProgress(label = "Profil passager", currentStep = 1, totalSteps = 4)

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CovySelectableCard(
                    icon = Icons.Outlined.School,
                    title = "Étudiant",
                    subtitle = "Université ou enseignement supérieur",
                    description = "Matricule et carte étudiante nécessaires.",
                    selected = selected == SchoolingType.ETUDIANT,
                    onSelect = { selected = SchoolingType.ETUDIANT }
                )
                CovySelectableCard(
                    icon = Icons.Outlined.Apartment,
                    title = "Élève",
                    subtitle = "Collège ou lycée",
                    description = "Carte scolaire, parent ou garant et contact d'urgence nécessaires.",
                    selected = selected == SchoolingType.ELEVE,
                    onSelect = { selected = SchoolingType.ELEVE }
                )
            }

            Text(
                "Votre choix détermine les prochaines étapes d'inscription.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Button(
            onClick = { onContinue(selected) },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Continuer", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 860)
@Composable
private fun SchoolingTypeScreenPreview() {
    CovyTheme {
        SchoolingTypeScreen(onBack = {}, onContinue = {})
    }
}
