package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.PendingActions
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyInfoCard
import com.example.covy.ui.components.CovySummaryRow
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class DossierSummary(
    val fullName: String,
    val maskedPhone: String,
    val profile: String,
    val school: String,
    val matricule: String,
    val documentName: String
)

@Composable
fun DossierReceivedScreen(
    summary: DossierSummary,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onBackToLogin: () -> Unit,
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
                    "INSCRIPTION · VÉRIFICATION",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Dossier bien reçu", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Votre carte est en attente de vérification.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyInfoCard(
                icon = Icons.Outlined.PendingActions,
                badge = "En attente d'examen",
                title = "Nous examinons votre dossier.",
                text = "L'accès à la recherche et aux réservations reste restreint jusqu'à validation."
            )

            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Votre récapitulatif", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    CovySummaryRow(
                        icon = Icons.Outlined.Person,
                        title = summary.fullName,
                        subtitle = summary.maskedPhone
                    )
                    CovySummaryRow(
                        icon = Icons.Outlined.School,
                        title = summary.profile,
                        subtitle = summary.school
                    )
                    CovySummaryRow(
                        icon = Icons.Outlined.Description,
                        title = "Matricule ${summary.matricule}",
                        subtitle = "${summary.documentName} · Document reçu"
                    )
                }
            }

            Text(
                "Une erreur dans vos informations ? Corrigez votre dossier avant sa validation.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onEdit,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Corriger mon dossier", fontWeight = FontWeight.SemiBold)
            }
            TextButton(onClick = onBackToLogin) {
                Text("Retour à la connexion", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun DossierReceivedScreenPreview() {
    CovyTheme {
        DossierReceivedScreen(
            summary = DossierSummary(
                fullName = "Ashley Mensah",
                maskedPhone = "+225 07 •• •• •• 67",
                profile = "Étudiant · Licence 2",
                school = "Université de Bingerville",
                matricule = "ETU-2026-08421",
                documentName = "carte-etudiante.pdf"
            ),
            onBack = {}, onEdit = {}, onBackToLogin = {}
        )
    }
}