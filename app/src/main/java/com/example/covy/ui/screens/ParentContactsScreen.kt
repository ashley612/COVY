package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyCheckboxRow
import com.example.covy.ui.components.CovyDropdownField
import com.example.covy.ui.components.CovyStepProgress
import com.example.covy.ui.components.CovyTextField
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class ParentContacts(
    val parentName: String,
    val relation: String,
    val parentPhone: String,
    val emergencyName: String,
    val emergencyPhone: String,
    val minorConsent: Boolean
)

private val relations = listOf("Mère", "Père", "Tuteur / Tutrice", "Frère / Sœur", "Autre")

@Composable
fun ParentContactsScreen(
    onBack: () -> Unit,
    onSubmit: (ParentContacts) -> Unit,
    modifier: Modifier = Modifier
) {
    var parentName by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var parentPhone by remember { mutableStateOf("") }
    var sameAsParent by remember { mutableStateOf(false) }
    var emergencyName by remember { mutableStateOf("") }
    var emergencyPhone by remember { mutableStateOf("") }
    var minorConsent by remember { mutableStateOf(false) }

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
                Text("Vos contacts de confiance", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            CovyStepProgress(label = "Contacts et autorisation", currentStep = 4, totalSteps = 4)

            // --- Parent ou garant ---
            Text("Parent ou garant", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            CovyTextField(
                label = "Nom du parent ou garant",
                value = parentName,
                onValueChange = { parentName = it },
                placeholder = "Aminata Mensah"
            )

            CovyDropdownField(
                label = "Lien avec l'élève",
                options = relations,
                selected = relation,
                onSelect = { relation = it }
            )

            CovyTextField(
                label = "Téléphone parent / garant *",
                value = parentPhone,
                onValueChange = { parentPhone = it },
                placeholder = "+225 07 08 22 41 90",
                keyboardType = KeyboardType.Phone,
                trailingIcon = { Icon(Icons.Outlined.Phone, contentDescription = null) }
            )

            // --- Contact d'urgence ---
            Text(
                "Personne à contacter en urgence",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            CovyCheckboxRow(
                checked = sameAsParent,
                onCheckedChange = { sameAsParent = it },
                text = "Utiliser la même personne que le parent / garant"
            )

            if (!sameAsParent) {
                CovyTextField(
                    label = "Nom du contact d'urgence",
                    value = emergencyName,
                    onValueChange = { emergencyName = it },
                    placeholder = "Yao Mensah"
                )

                CovyTextField(
                    label = "Téléphone du contact d'urgence",
                    value = emergencyPhone,
                    onValueChange = { emergencyPhone = it },
                    placeholder = "+225 05 44 30 18 62",
                    keyboardType = KeyboardType.Phone,
                    trailingIcon = { Icon(Icons.Outlined.Phone, contentDescription = null) }
                )
            }

            // --- Autorisation ---
            CovyCheckboxRow(
                checked = minorConsent,
                onCheckedChange = { minorConsent = it },
                text = "Je suis mineur : mon représentant légal autorise mon inscription et mes trajets Covy."
            )

            Text(
                "* Téléphone parent / garant obligatoire. Ces contacts restent privés.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Button(
            onClick = {
                onSubmit(
                    ParentContacts(
                        parentName = parentName,
                        relation = relation,
                        parentPhone = parentPhone,
                        emergencyName = if (sameAsParent) parentName else emergencyName,
                        emergencyPhone = if (sameAsParent) parentPhone else emergencyPhone,
                        minorConsent = minorConsent
                    )
                )
            },
            enabled = parentPhone.isNotBlank() && minorConsent,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Envoyer mon dossier élève", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun ParentContactsScreenPreview() {
    CovyTheme {
        ParentContactsScreen(onBack = {}, onSubmit = {})
    }
}