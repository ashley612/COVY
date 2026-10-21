package com.example.covy.ui.wallet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.model.CourseHistory
import com.example.covy.model.WalletUiState
import com.example.covy.ui.theme.*
import com.example.covy.ui.theme.CovyColors.BackgroundGray
import com.example.covy.ui.theme.CovyColors.BorderLight
import com.example.covy.ui.theme.CovyColors.CovyDarkGreen
import com.example.covy.ui.theme.CovyColors.CovyGreenText
import com.example.covy.ui.theme.CovyColors.CovyLightGreen
import com.example.covy.ui.theme.CovyColors.SurfaceWhite
import com.example.covy.ui.theme.CovyColors.TextPrimary
import com.example.covy.ui.theme.CovyColors.TextSecondary
import com.example.covy.ui.theme.CovyColors.TextTertiary
import com.example.covy.ui.theme.CovyColors.TextWhite
import com.example.covy.ui.theme.CovyColors.TextWhiteAlpha
import com.example.covy.viewmodel.WalletViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun WalletScreen(viewModel: WalletViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            CovyBottomNavigationBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header (Scrollable)
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item { TopAppBar() }

                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Spacer(modifier = Modifier.height(8.dp))
                        SubtitleText()
                        Spacer(modifier = Modifier.height(16.dp))
                        BalanceCard(uiState)
                        Spacer(modifier = Modifier.height(24.dp))
                        WithdrawalDropdown(
                            options = uiState.withdrawalOptions,
                            selectedOption = uiState.selectedWithdrawalMethod,
                            onOptionSelected = viewModel::onWithdrawalMethodSelected
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        HistoryHeader(total = uiState.totalCoursesAujourdhui)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                items(uiState.coursesDuJour) { course ->
                    CourseItem(course = course)
                }
            }

            // Bottom Button (Fixed above nav bar)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundGray)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = viewModel::effectuerRetrait,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CovyDarkGreen)
                ) {
                    Text(
                        text = "Effectuer un retrait",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Retour",
            tint = TextSecondary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Placeholder pour le logo
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CovyLightGreen),
            contentAlignment = Alignment.Center
        ) {
            Text("C", color = CovyDarkGreen, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Covy",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = CovyDarkGreen
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "30 / 30",
            fontSize = 14.sp,
            color = TextTertiary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SubtitleText() {
    Text(
        text = "ESPACE CHAUFFEUR · REVENUS",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = CovyGreenText,
        letterSpacing = 0.5.sp
    )
    Text(
        text = "Votre portefeuille",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun BalanceCard(uiState: WalletUiState) {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CovyDarkGreen)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Solde disponible",
                fontSize = 14.sp,
                color = TextWhiteAlpha,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "${formatter.format(uiState.soldeDisponible)} ",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "FCFA",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Revenus du jour · ${formatter.format(uiState.revenusDuJour)} FCFA",
                fontSize = 13.sp,
                color = TextWhiteAlpha,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawalDropdown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Retrait Wave / Orange Money",
            fontSize = 12.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = TextSecondary
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(52.dp), // Hauteur réduite
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceWhite,
                    unfocusedContainerColor = SurfaceWhite,
                    focusedBorderColor = BorderLight,
                    unfocusedBorderColor = BorderLight,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(SurfaceWhite)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, fontSize = 14.sp) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryHeader(total: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Historique des courses",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(CovyLightGreen)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Aujourd'hui · $total",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CovyGreenText
            )
        }
    }
}

@Composable
fun CourseItem(course: CourseHistory) {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Pas d'ombre, juste le fond blanc
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icone Voiture
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BackgroundGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = null,
                    tint = CovyDarkGreen,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Infos Trajet
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = course.depart,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = " > ",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = course.arrivee,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${course.heure} · ${course.details}",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            // Prix
            Text(
                text = "+${formatter.format(course.prix).replace("\u00A0", " ")} F",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CovyGreenText
            )
        }
    }
}

@Composable
fun CovyBottomNavigationBar() {
    NavigationBar(
        containerColor = SurfaceWhite,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Accueil", modifier = Modifier.size(22.dp)) },
            label = { Text("Accueil", fontSize = 10.sp) },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary
            )
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Courses", modifier = Modifier.size(22.dp)) },
            label = { Text("Courses", fontSize = 10.sp) },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary
            )
        )
        NavigationBarItem(
            icon = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CovyLightGreen)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Wallet,
                        contentDescription = "Revenus",
                        tint = CovyDarkGreen,
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            label = { Text("Revenus", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CovyDarkGreen) },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent // On gère le fond manuellement via le Box
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Compte", modifier = Modifier.size(22.dp)) },
            label = { Text("Compte", fontSize = 10.sp) },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F8F9)
@Composable
fun WalletScreenPreview() {
    CovyTheme { // Assurez-vous d'avoir un Theme qui englobe ça
        WalletScreen()
    }
}