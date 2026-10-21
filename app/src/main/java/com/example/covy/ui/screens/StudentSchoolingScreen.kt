package com.example.covy.ui.screens

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Lock
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


private val studyLevels = listOf(
    "BTS / DUT", "Licence 1", "Licence 2", "Licence 3",
    "Master 1", "Master 2", "Doctorat"
)

// Retrouve le nom d'un fichier choisi par l'utilisateur
private fun Context.displayName(uri: Uri): String? =
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (index >= 0 && cursor.moveToFirst()) cursor.getString(index) else null
    }

@Composable
fun StudentSchoolingScreen(
    onBack: () -> Unit,
    onSubmit: (matricule: String, university: String, level: String, documentUri: Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    var matricule by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var documentUri by remember { mutableStateOf<Uri?>(null) }
    var documentName by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    // Dans la Preview, il n'y a pas de sélecteur de fichiers : on ne le crée pas
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
                    "INSCRIPTION · ÉTUDIANT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Votre vie de campus", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Ces informations servent à vérifier votre scolarité.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyStepProgress(label = "Scolarité", currentStep = 3, totalSteps = 3)

            CovyTextField(
                label = "Matricule *",
                value = matricule,
                onValueChange = { matricule = it },
                placeholder = "ETU-2026-08421"
            )

            CovyTextField(
                label = "Nom de l'université / école *",
                value = university,
                onValueChange = { university = it },
                placeholder = "Université de Bingerville",
                trailingIcon = { Icon(Icons.Outlined.AccountBalance, contentDescription = null) }
            )

            CovyDropdownField(
                label = "Niveau d'études",
                options = studyLevels,
                selected = level,
                onSelect = { level = it }
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Carte étudiante / scolaire *", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                CovyFileUploadCard(
                    fileName = documentName,
                    onPick = { launcher?.launch(arrayOf("application/pdf", "image/*")) }
                )
            }

            CovyInfoCard(
                icon = Icons.Outlined.Lock,
                title = "Vos documents restent privés",
                text = "Utilisés pour l'examen du dossier, non affichés aux autres passagers."
            )

            Text(
                "* Matricule, établissement et carte obligatoires.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Button(
            onClick = { documentUri?.let { onSubmit(matricule, university, level, it) } },
            enabled = matricule.isNotBlank() && university.isNotBlank() && documentUri != null,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Envoyer mon dossier étudiant", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun StudentSchoolingScreenPreview() {
    CovyTheme {
        StudentSchoolingScreen(onBack = {}, onSubmit = { _, _, _, _ -> })
    }
}
