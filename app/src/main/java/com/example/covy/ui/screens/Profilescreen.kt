package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyPassengerBottomBar
import com.example.covy.ui.components.CovySummaryRow
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.components.PassengerTab
import com.example.covy.ui.theme.CovyTheme

data class ProfileRow(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

data class PassengerProfile(
    val fullName: String,
    val maskedPhone: String,
    val profileType: String,          // "Élève" ou "Étudiant"
    val schoolRows: List<ProfileRow>,
    val contactRows: List<ProfileRow> // vide pour un étudiant : section masquée
)

@Composable
fun ProfileScreen(
    profile: PassengerProfile,
    onBack: () -> Unit,
    onEditInfo: () -> Unit,
    onEditSchooling: () -> Unit,
    onEditContacts: () -> Unit,
    onHelp: () -> Unit,
    onLogout: () -> Unit,
    onTabSelected: (PassengerTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "ESPACE PASSAGER · PROFIL ${profile.profileType.uppercase()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Mon profil", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            // Identité
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(profile.fullName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(
                                profile.maskedPhone,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            profile.profileType,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    ActionLink("Modifier mes informations →", onEditInfo)
                }
            }

            ProfileSection(
                title = "Scolarité",
                rows = profile.schoolRows,
                actionLabel = "Modifier ma scolarité →",
                onAction = onEditSchooling
            )

            if (profile.contactRows.isNotEmpty()) {
                ProfileSection(
                    title = "Contacts privés et sécurité",
                    rows = profile.contactRows,
                    actionLabel = "Modifier mes contacts →",
                    onAction = onEditContacts,
                    tinted = true
                )
            }

            // Aide et déconnexion
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onHelp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = null)
                        Spacer(Modifier.width(12.dp))
                        Text("Aide et assistance", fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "Se déconnecter",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.clickable(onClick = onLogout)
                    )
                }
            }
        }

        CovyPassengerBottomBar(selected = PassengerTab.PROFILE, onSelect = onTabSelected)
    }
}

@Composable
private fun ProfileSection(
    title: String,
    rows: List<ProfileRow>,
    actionLabel: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
    tinted: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (tinted) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            rows.forEach { row ->
                CovySummaryRow(icon = row.icon, title = row.title, subtitle = row.subtitle)
            }
            ActionLink(actionLabel, onAction)
        }
    }
}

@Composable
private fun ActionLink(label: String, onClick: () -> Unit) {
    Text(
        label,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun ProfileScreenPreview() {
    CovyTheme {
        ProfileScreen(
            profile = PassengerProfile(
                fullName = "Ashley Mensah",
                maskedPhone = "+225 07 •• •• •• 67",
                profileType = "Élève",
                schoolRows = listOf(
                    ProfileRow(
                        Icons.Outlined.AccountBalance,
                        "Lycée moderne de Bingerville",
                        "Première D · Matricule ELV-2026-01738"
                    ),
                    ProfileRow(
                        Icons.Outlined.VerifiedUser,
                        "Carte scolaire",
                        "Vérifiée · Visible uniquement par vous"
                    )
                ),
                contactRows = listOf(
                    ProfileRow(
                        Icons.Outlined.Group,
                        "Parent / garant · Aminata Mensah",
                        "Mère · +225 07 •• •• •• 90"
                    ),
                    ProfileRow(
                        Icons.Outlined.Phone,
                        "Urgence · Yao Mensah",
                        "+225 05 •• •• •• 62"
                    )
                )
            ),
            onBack = {}, onEditInfo = {}, onEditSchooling = {}, onEditContacts = {},
            onHelp = {}, onLogout = {}, onTabSelected = {}
        )
    }
}