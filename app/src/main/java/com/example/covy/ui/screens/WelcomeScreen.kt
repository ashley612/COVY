package com.example.covy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.R
import com.example.covy.ui.theme.CovyTheme

@Composable
fun WelcomeScreen(
    onCreateAccount: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp, bottom = 36.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // En-tête : logo + nom
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.covy_logo),
                contentDescription = "Logo Covy",
                modifier = Modifier.size(36.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text("Covy", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        // Accroche + image
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "BIENVENUE · ABIDJAN",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text("Le campus, ensemble.", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(
                "Le covoiturage scolaire et universitaire à Abidjan.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Image(
                painter = painterResource(R.drawable.covyimage),
                contentDescription = "Étudiants devant une voiture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
        }

        // Message principal
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "Un même trajet, un coût partagé",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
            Text("Moins de frais.\nPlus de sérénité.", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(
                "Étudiants, élèves et chauffeurs : trouvez votre place sur le chemin des cours.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Shield, contentDescription = null)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Un dossier pour chaque profil", fontWeight = FontWeight.SemiBold)
                        Text(
                            "Carte scolaire ou documents chauffeur",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Actions
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onCreateAccount,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Créer un compte", fontWeight = FontWeight.SemiBold)
            }
            TextButton(onClick = onLogin) {
                Text("J'ai déjà un compte · Se connecter", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun WelcomeScreenPreview() {
    CovyTheme { WelcomeScreen(onCreateAccount = {}, onLogin = {}) }
}
