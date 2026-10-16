package com.example.covy.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyBadge
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class GroupPassenger(
    val name: String,       // "Ashley M."
    val route: String,      // "St-Jean → Campus Bingerville"
    val isYou: Boolean = false
)

private val numberWords = listOf(
    "zéro", "une", "deux", "trois", "quatre", "cinq", "six", "sept", "huit"
)

@Composable
fun GroupScreen(
    passengers: List<GroupPassenger>,
    capacity: Int,                 // nombre de places de la voiture
    campusName: String,            // "Bingerville"
    itinerary: String,             // "Riviera 2 → St-Jean → Angré → Bingerville"
    departureTime: String,         // "07:30"
    arrivalTime: String,           // "08:05"
    totalPrice: String,            // "6 000 FCFA"
    sharePrice: String,            // "1 500 FCFA"
    onBack: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val placesWord = numberWords.getOrNull(capacity) ?: capacity.toString()
    val isFull = passengers.size >= capacity

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "PASSAGER · GROUPE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Un campus, $placesWord places",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Le groupe rejoint le campus de $campusName.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyBadge(
                text = if (isFull) "${passengers.size} passagers · Groupe complet"
                else "${passengers.size} passagers sur $capacity",
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )

            // Itinéraire
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(itinerary, fontWeight = FontWeight.Bold)
                    Text(
                        "Départ $departureTime · Arrivée estimée $arrivalTime",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Liste des passagers
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    passengers.forEach { PassengerRow(it) }
                }
            }

            // Prix partagé
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "PRIX PARTAGÉ · $totalPrice ÷ ${passengers.size}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                    Text(
                        sharePrice,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "Votre participation pour une place",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Continuer vers le trajet", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun PassengerRow(passenger: GroupPassenger) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Pas de photo pour l'instant : on affiche les initiales
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                initialsOf(passenger.name),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                if (passenger.isYou) "${passenger.name} · Vous" else passenger.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                passenger.route,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun initialsOf(name: String): String =
    name.split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun GroupScreenPreview() {
    CovyTheme {
        GroupScreen(
            passengers = listOf(
                GroupPassenger("Ashley M.", "St-Jean → Campus Bingerville", isYou = true),
                GroupPassenger("Jean K.", "St-Jean → Campus Bingerville"),
                GroupPassenger("Mariam T.", "Angré Be Tranche → Campus Bingerville"),
                GroupPassenger("Kevin B.", "Riviera 2 → Campus Bingerville")
            ),
            capacity = 4,
            campusName = "Bingerville",
            itinerary = "Riviera 2 → St-Jean → Angré → Bingerville",
            departureTime = "07:30",
            arrivalTime = "08:05",
            totalPrice = "6 000 FCFA",
            sharePrice = "1 500 FCFA",
            onBack = {}, onContinue = {}
        )
    }
}