package com.example.covy.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyDropdownField
import com.example.covy.ui.components.CovyFileUploadCard
import com.example.covy.ui.components.CovyInfoCard
import com.example.covy.ui.components.CovyStepProgress
import com.example.covy.ui.components.CovyTextField
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme
import com.example.covy.ui.util.displayName

private val pupilClasses = listOf(
    "6e", "5e", "4e", "3e", "Seconde",
    "Première A", "Première C", "Première D",
    "Terminale A", "Terminale C", "Terminale D"
)

@Composable
fun PupilSchoolingScreen(
    onBack: () -> Unit,
    onContinue: (matricule: String, school: String, schoolClass: String, documentUri: Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    var matricule by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var schoolClass by remember { mutableStateOf("") }
    var documentUri by remember { mutableStateOf<Uri?>(null) }
    var documentName by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val launcher = if (LocalInspectionMode.current) null else
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                documentUri = uri
                documentName = context.displayName(uri) ?: "document"
            }
        }

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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "INSCRIPTION · ÉLÈVE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Votre école", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Renseignez votre établissement et ajoutez votre carte.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyStepProgress(label = "Scolarité", currentStep = 3, totalSteps = 4)

            CovyTextField(
                label = "Matricule *",
                value = matricule,
                onValueChange = { matricule = it },
                placeholder = "ELV-2026-01738"
            )

            CovyTextField(
                label = "Nom de l'école *",
                value = school,
                onValueChange = { school = it },
                placeholder = "Lycée moderne de Bingerville",
                trailingIcon = { Icon(Icons.Outlined.AccountBalance, contentDescription = null) }
            )

            CovyDropdownField(
                label = "Classe",
                options = pupilClasses,
                selected = schoolClass,
                onSelect = { schoolClass = it }
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Carte scolaire *", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                CovyFileUploadCard(
                    fileName = documentName,
                    onPick = { launcher?.launch(arrayOf("application/pdf", "image/*")) }
                )
            }

            CovyInfoCard(
                icon = Icons.Outlined.Group,
                title = "Prochaine étape : vos contacts",
                text = "Parent ou garant et personne à contacter en urgence."
            )

            Text(
                "Votre carte reste privée. * Champs obligatoires.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Button(
            onClick = { documentUri?.let { onContinue(matricule, school, schoolClass, it) } },
            enabled = matricule.isNotBlank() && school.isNotBlank() && documentUri != null,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Continuer vers les contacts", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun PupilSchoolingScreenPreview() {
    CovyTheme {
        PupilSchoolingScreen(onBack = {}, onContinue = { _, _, _, _ -> })
    }
}