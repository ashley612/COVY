package com.example.covy.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme
import java.util.Locale

enum class PaymentMethod(val label: String, val subtitle: String, val initial: String) {
    WAVE("Wave", "Compte Mobile Money", "W"),
    ORANGE_MONEY("Orange Money", "Compte Mobile Money", "O"),
    CASH("Espèce", "Payez à la rencontre", "E")
}

// 1500 -> "1 500 FCFA" (séparateur fine insécable)
private fun formatFcfa(amount: Int): String =
    String.format(Locale.FRENCH, "%,d FCFA", amount)

@Composable
fun PaymentScreen(
    tripLabel: String,
    pricePerSeat: Int,
    passengers: Int,
    onBack: () -> Unit,
    onConfirm: (PaymentMethod) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(PaymentMethod.WAVE) }
    val total = pricePerSeat * passengers

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 36.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CovyTopBar(onBack = onBack)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "PASSAGER · PAIEMENT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Votre place, votre paiement", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(tripLabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // Récapitulatif du prix
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Prix par place", fontWeight = FontWeight.SemiBold)
                            Text(
                                if (passengers > 1) "$passengers passagers" else "1 passager",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(formatFcfa(pricePerSeat), fontWeight = FontWeight.SemiBold)
                    }
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total à payer", fontWeight = FontWeight.SemiBold)
                        Text(
                            formatFcfa(total),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Moyens de paiement
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Choisissez un moyen de paiement",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                PaymentMethod.entries.forEach { method ->
                    PaymentOptionRow(
                        method = method,
                        selected = method == selected,
                        onSelect = { selected = method }
                    )
                }
            }

            Text(
                "La confirmation est envoyée selon le moyen de paiement choisi. " +
                        "Vérifiez le montant avant de valider.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Button(
            onClick = { onConfirm(selected) },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Confirmer · ${formatFcfa(total)}", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun PaymentOptionRow(
    method: PaymentMethod,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(20.dp)

    Surface(
        shape = shape,
        color = if (selected) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .selectable(selected = selected, onClick = onSelect, role = Role.RadioButton)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pastille avec l'initiale (à remplacer par les logos Wave / Orange Money)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    method.initial,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(method.label, fontWeight = FontWeight.Bold)
                Text(
                    method.subtitle,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            RadioButton(selected = selected, onClick = null)
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 860)
@Composable
private fun PaymentScreenPreview() {
    CovyTheme {
        PaymentScreen(
            tripLabel = "St-Jean → Campus Bingerville · 14 oct.",
            pricePerSeat = 1500,
            passengers = 1,
            onBack = {},
            onConfirm = {}
        )
    }
}