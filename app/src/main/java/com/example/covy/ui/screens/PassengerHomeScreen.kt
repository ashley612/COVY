
package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.*
import com.example.covy.ui.components.PassengerTab
import com.example.covy.ui.components.UpcomingTrip
import com.example.covy.ui.theme.CovyTheme

@Composable
fun PassengerHomeScreen(
    userName: String,
    upcomingTrip: UpcomingTrip?,
    onBack: () -> Unit,
    onFindTrip: (departure: String, destination: String) -> Unit,
    onOpenTicket: () -> Unit,
    onTabSelected: (PassengerTab) -> Unit,
    modifier: Modifier = Modifier
) {
    var departure by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var nowSelected by remember { mutableStateOf(true) }
    var selectedTab by remember { mutableStateOf(PassengerTab.HOME) }

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
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "ESPACE PASSAGER",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Bonjour $userName",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Où allez-vous aujourd'hui ?",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CovyChip(
                    "Maintenant",
                    selected = nowSelected,
                    onClick = { nowSelected = true }
                )

                CovyChip(
                    "Planifier",
                    selected = !nowSelected,
                    onClick = { nowSelected = false }
                )
            }

            CovyTextField(
                label = "Départ",
                value = departure,
                onValueChange = { departure = it },
                placeholder = "Ex. St-Jean, Cocody",
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Lieu de départ"
                    )
                }
            )

            CovyTextField(
                label = "Destination campus",
                value = destination,
                onValueChange = { destination = it },
                placeholder = "Ex. Campus Bingerville",
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = "Campus de destination"
                    )
                }
            )

            if (upcomingTrip != null) {
                CovyTripCard(
                    trip = upcomingTrip,
                    onOpenTicket = onOpenTicket
                )
            }

            CovyInfoCard(
                icon = null,
                title = "Votre campus à portée de trajet",
                text = "Choisissez votre départ, puis comparez les places disponibles."
            )
        }

        Button(
            onClick = {
                onFindTrip(departure.trim(), destination.trim())
            },
            enabled = departure.isNotBlank() && destination.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Trouver un trajet",
                fontWeight = FontWeight.SemiBold
            )
        }

        CovyBottomBar(
            selected = selectedTab,
            onSelect = { tab ->
                selectedTab = tab
                onTabSelected(tab)
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun PassengerHomeScreenPreview() {
    CovyTheme {
        PassengerHomeScreen(
            userName = "Ashley",
            upcomingTrip = UpcomingTrip(
                whenText = "Demain · 07:37",
                route = "St-Jean → Campus Bingerville",
                price = "1 500 F",
                driverInfo = "Karim Kouassi · 1 place"
            ),
            onBack = {},
            onFindTrip = { _, _ -> },
            onOpenTicket = {},
            onTabSelected = {}
        )
    }
}
