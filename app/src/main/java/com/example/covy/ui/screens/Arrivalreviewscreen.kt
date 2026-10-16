package com.example.covy.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme

data class ArrivalInfo(
    val campusName: String,
    val receiptDate: String,
    val paymentMethod: String,
    val receiptRef: String,
    val amountLabel: String,
    val driverName: String,
    val vehicle: String,
    val plate: String,
    val driverRating: Double
)

private fun ratingLabel(rating: Int): String = when (rating) {
    1 -> "Mauvais"
    2 -> "Passable"
    3 -> "Correct"
    4 -> "Bien"
    5 -> "Excellent"
    else -> ""
}

@Composable
fun ArrivalReviewScreen(
    info: ArrivalInfo,
    onBack: () -> Unit,
    onSubmitReview: (rating: Int, comment: String) -> Unit,
    onBackHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }

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
                    "PASSAGER · ARRIVÉE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Vous êtes arrivé !", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Bienvenue au campus de ${info.campusName.removePrefix("Campus ")}.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Remerciement + reçu
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        "Merci d'avoir partagé le trajet.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Receipt,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Votre reçu", fontWeight = FontWeight.Bold)
                            Text(
                                "${info.receiptDate} · ${info.paymentMethod} · ${info.receiptRef}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(info.amountLabel, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Chauffeur
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
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(info.driverName, fontWeight = FontWeight.Bold)
                    Text(
                        info.vehicle,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "${info.plate} · ",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Note",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            " ${info.driverRating}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Notation
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Comment s'est passé le trajet ?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (1..5).forEach { star ->
                        IconButton(
                            onClick = { rating = star },
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                imageVector = if (star <= rating) Icons.Filled.Star
                                else Icons.Outlined.StarBorder,
                                contentDescription = "$star étoile${if (star > 1) "s" else ""}",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
                Text(
                    "$rating / 5 · ${ratingLabel(rating)}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            // Commentaire (CovyTextField est en singleLine, d'où le champ direct)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Votre commentaire (facultatif)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    maxLines = 6,
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text("Chauffeur ponctuel et trajet agréable.") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFE3E9E5),
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onSubmitReview(rating, comment) },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Envoyer mon avis", fontWeight = FontWeight.SemiBold)
            }
            TextButton(onClick = onBackHome) {
                Text("Retour à l'accueil", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun ArrivalReviewScreenPreview() {
    CovyTheme {
        ArrivalReviewScreen(
            info = ArrivalInfo(
                campusName = "Campus Bingerville",
                receiptDate = "09 oct.",
                paymentMethod = "Wave",
                receiptRef = "DRV-8942",
                amountLabel = "1 500 FCFA",
                driverName = "Karim Kouassi",
                vehicle = "Toyota Corolla blanche",
                plate = "AB 1234 CD",
                driverRating = 4.8
            ),
            onBack = {}, onSubmitReview = { _, _ -> }, onBackHome = {}
        )
    }
}