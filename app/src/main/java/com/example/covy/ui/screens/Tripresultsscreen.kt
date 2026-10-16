package com.example.covy.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyBadge
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class TripOffer(
    val id: String,
    val departureTime: String,   // "07:37"
    val arrivalTime: String,     // "08:05"
    val price: String,           // "1 500 FCFA"
    val routeLabel: String,      // "St-Jean · Pharmacie → Campus Bingerville"
    val driverName: String,      // "Karim Kouassi"
    val seatsAvailable: Int,
    val recommended: Boolean = false
)

@Composable
fun TripResultsScreen(
    offers: List<TripOffer>,
    routeSummary: String,   // "St-Jean → Campus Bingerville"
    dateSummary: String,    // "Vendredi 9 octobre · 1 place"
    onBack: () -> Unit,
    onEditSearch: () -> Unit,
    onChooseTrip: (TripOffer) -> Unit,
    onOpenDetail: (TripOffer) -> Unit,
    modifier: Modifier = Modifier
) {
    val chipColor = MaterialTheme.colorScheme.secondaryContainer
    val chipTextColor = MaterialTheme.colorScheme.primary

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { CovyTopBar(onBack = onBack) }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "PASSAGER · RECHERCHE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = when (offers.size) {
                        0 -> "Aucun départ trouvé"
                        1 -> "1 départ pour vous"
                        else -> "${offers.size} départs pour vous"
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Column {
                    Text(routeSummary, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(dateSummary, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CovyBadge(
                    text = "Modifier la recherche",
                    containerColor = chipColor,
                    contentColor = chipTextColor,
                    modifier = Modifier.clickable(onClick = onEditSearch)
                )
                CovyBadge(
                    text = "Prix par passager",
                    containerColor = chipColor,
                    contentColor = chipTextColor
                )
            }
        }

        items(offers, key = { it.id }) { offer ->
            TripOfferCard(
                offer = offer,
                onClick = { if (offer.recommended) onChooseTrip(offer) else onOpenDetail(offer) }
            )
        }
    }
}

@Composable
private fun TripOfferCard(offer: TripOffer, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = if (offer.recommended) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${offer.departureTime} → ${offer.arrivalTime}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    offer.price,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                offer.routeLabel,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.DirectionsCar, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(offer.driverName, fontWeight = FontWeight.Bold)
                    Text(
                        if (offer.seatsAvailable == 1) "1 place disponible"
                        else "${offer.seatsAvailable} places disponibles",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                if (offer.recommended) "Choisir ce trajet →" else "Voir le détail →",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun TripResultsScreenPreview() {
    CovyTheme {
        TripResultsScreen(
            offers = listOf(
                TripOffer("1", "07:37", "08:05", "1 500 FCFA",
                    "St-Jean · Pharmacie → Campus Bingerville", "Karim Kouassi", 1, recommended = true),
                TripOffer("2", "07:50", "08:25", "1 500 FCFA",
                    "St-Jean · Pharmacie → Campus Bingerville", "Koffi A.", 2),
                TripOffer("3", "08:10", "08:45", "1 500 FCFA",
                    "St-Jean · Pharmacie → Campus Bingerville", "Kouamé Éric", 3)
            ),
            routeSummary = "St-Jean → Campus Bingerville",
            dateSummary = "Vendredi 9 octobre · 1 place",
            onBack = {}, onEditSearch = {}, onChooseTrip = {}, onOpenDetail = {}
        )
    }
}