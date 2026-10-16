package com.example.covy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.ui.components.CovyBadge
import com.example.covy.ui.components.CovyDropdownField
import com.example.covy.ui.components.CovyInfoCard
import com.example.covy.ui.components.CovyTextField
import com.example.covy.ui.components.CovyTopBar
import com.example.covy.ui.theme.CovyTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private val BorderGray = Color(0xFFE3E9E5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTripScreen(
    onBack: () -> Unit,
    onSearch: (
        departure: String,
        destination: String,
        dateMillis: Long,
        hour: Int,
        minute: Int,
        seats: Int
    ) -> Unit,
    modifier: Modifier = Modifier,
    initialDeparture: String = "",
    initialDestination: String = "",
    meetingPointInfo: String? = null
) {
    var departure by remember { mutableStateOf(initialDeparture) }
    var destination by remember { mutableStateOf(initialDestination) }
    var dateMillis by remember { mutableStateOf<Long?>(null) }
    var hour by remember { mutableStateOf<Int?>(null) }
    var minute by remember { mutableStateOf<Int?>(null) }
    var seats by remember { mutableIntStateOf(1) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val seatOptions = remember { (1..4).map { if (it == 1) "1 passager" else "$it passagers" } }

    val dateText = dateMillis?.let {
        SimpleDateFormat("dd MMM yyyy", Locale.FRENCH)
            .apply { timeZone = TimeZone.getTimeZone("UTC") }
            .format(Date(it))
    } ?: ""
    val timeText = if (hour != null && minute != null) "%02d:%02d".format(hour, minute) else ""

    val canSearch = departure.isNotBlank() && destination.isNotBlank() &&
            dateMillis != null && hour != null && minute != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
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
                    "PASSAGER · PLANIFIER",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Votre prochain départ", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Choisissez votre arrêt et votre horaire.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CovyBadge(
                text = "Trajet planifié",
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )

            CovyTextField(
                label = "Départ",
                value = departure,
                onValueChange = { departure = it },
                placeholder = "Ex. St-Jean, Cocody",
                trailingIcon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) }
            )
            CovyTextField(
                label = "Destination",
                value = destination,
                onValueChange = { destination = it },
                placeholder = "Ex. Campus Bingerville",
                trailingIcon = { Icon(Icons.Outlined.School, contentDescription = null) }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PickerField(
                    label = "Date",
                    value = dateText,
                    placeholder = "Choisir",
                    icon = Icons.Outlined.CalendarMonth,
                    onClick = { showDatePicker = true },
                    modifier = Modifier.weight(1f)
                )
                PickerField(
                    label = "Heure",
                    value = timeText,
                    placeholder = "Choisir",
                    icon = Icons.Outlined.Schedule,
                    onClick = { showTimePicker = true },
                    modifier = Modifier.weight(1f)
                )
            }

            CovyDropdownField(
                label = "Nombre de places",
                options = seatOptions,
                selected = seatOptions[seats - 1],
                onSelect = { seats = seatOptions.indexOf(it) + 1 }
            )

            if (meetingPointInfo != null) {
                CovyInfoCard(
                    icon = Icons.Outlined.LocationOn,
                    title = "Un point de rencontre précis",
                    text = meetingPointInfo
                )
            }

            Text(
                "Les prix affichés seront indiqués par passager.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                onSearch(departure, destination, dateMillis!!, hour!!, minute!!, seats)
            },
            enabled = canSearch,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Rechercher des trajets", fontWeight = FontWeight.SemiBold)
        }
    }

    if (showDatePicker) {
        val state = rememberDatePickerState(initialSelectedDateMillis = dateMillis)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dateMillis = state.selectedDateMillis
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Annuler") }
            }
        ) {
            DatePicker(state = state)
        }
    }

    if (showTimePicker) {
        val state = rememberTimePickerState(
            initialHour = hour ?: 7,
            initialMinute = minute ?: 0,
            is24Hour = true
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    hour = state.hour
                    minute = state.minute
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Annuler") }
            },
            text = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TimePicker(state = state)
                }
            }
        )
    }
}

/** Champ qui ressemble à un champ de texte, mais ouvre un sélecteur au clic. */
@Composable
private fun PickerField(
    label: String,
    value: String,
    placeholder: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Box {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(placeholder) },
                trailingIcon = { Icon(icon, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = BorderGray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
            // Couche transparente qui reçoit le clic
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(onClick = onClick)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 900)
@Composable
private fun SearchTripScreenPreview() {
    CovyTheme {
        SearchTripScreen(
            onBack = {},
            onSearch = { _, _, _, _, _, _ -> },
            initialDeparture = "St-Jean, Cocody",
            initialDestination = "Campus Bingerville",
            meetingPointInfo = "Départ devant la pharmacie St-Jean."
        )
    }
}