package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyBottomBar
import com.example.covy.ui.components.CovyNotification
import com.example.covy.ui.components.CovyNotificationCard
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.components.PassengerTab
import com.example.covy.ui.theme.CovyTheme

@Composable
fun NotificationsScreen(
    notifications: List<CovyNotification>,
    onBack: () -> Unit,
    onNotificationAction: (CovyNotification) -> Unit,
    onMarkAllRead: () -> Unit,
    onTabSelected: (PassengerTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val unreadCount = notifications.count { it.unread }

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
                    "ESPACE PASSAGER · ALERTES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Vos notifications", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "L'essentiel de votre activité Covy.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (unreadCount > 0) {
                    Text(
                        text = if (unreadCount == 1) "1 nouvelle" else "$unreadCount nouvelles",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            notifications.forEach { notification ->
                CovyNotificationCard(
                    notification = notification,
                    onAction = { onNotificationAction(notification) }
                )
            }

            if (unreadCount > 0) {
                Text(
                    "Tout marquer comme lu",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable(onClick = onMarkAllRead)
                        .padding(vertical = 8.dp)
                )
            }
        }

        CovyBottomBar(selected = PassengerTab.ALERTS, onSelect = onTabSelected)
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun NotificationsScreenPreview() {
    CovyTheme {
        NotificationsScreen(
            notifications = listOf(
                CovyNotification(
                    id = "1",
                    icon = Icons.Outlined.DirectionsCar,
                    title = "Karim est en approche",
                    meta = "Aujourd'hui · 07:30",
                    body = "Arrivée dans 7 min à St-Jean. Préparez votre code de sécurité 4821.",
                    actionLabel = "Suivre mon chauffeur →",
                    unread = true
                ),
                CovyNotification(
                    id = "2",
                    icon = Icons.Outlined.ConfirmationNumber,
                    title = "Réservation confirmée",
                    meta = "09 oct. · Départ à 07:37",
                    body = "St-Jean → Campus Bingerville · 1 place",
                    actionLabel = "Voir mon billet →",
                    unread = true
                ),
                CovyNotification(
                    id = "3",
                    icon = Icons.Outlined.Payments,
                    title = "Paiement Wave reçu",
                    meta = "2 500 FCFA · Transaction DRV-8942",
                    actionLabel = "Consulter le reçu →",
                    unread = true
                ),
                CovyNotification(
                    id = "4",
                    icon = Icons.Outlined.VerifiedUser,
                    title = "Carte étudiante validée",
                    meta = "Votre dossier passager est validé.",
                    body = "Vous pouvez rechercher et réserver vos trajets."
                )
            ),
            onBack = {},
            onNotificationAction = {},
            onMarkAllRead = {},
            onTabSelected = {}
        )
    }
}