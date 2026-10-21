package com.example.covy.UiChauffeur

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covy.models.AccountUiState
import com.example.covy.models.DriverDocument
import com.example.covy.models.DriverProfile
import com.example.covy.models.SubscriptionInfo
import com.example.covy.ui.theme.*
import com.example.covy.ui.theme.CovyColors.BackgroundGray
import com.example.covy.ui.theme.CovyColors.CovyDarkGreen
import com.example.covy.ui.theme.CovyColors.CovyGreenText
import com.example.covy.ui.theme.CovyColors.CovyLightGreen
import com.example.covy.ui.theme.CovyColors.SurfaceWhite
import com.example.covy.ui.theme.CovyColors.TextPrimary
import com.example.covy.ui.theme.CovyColors.TextSecondary
import com.example.covy.ui.theme.CovyColors.TextTertiary
import com.example.covy.viewmodel.AccountViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AccountScreen(
                viewModel: AccountViewModel = viewModel(),

                  onNavigateToDocumentDetail: (String) -> Unit,
                  onNavigateToSubscription: () -> Unit,
                  onNavigateToSupport: () -> Unit,
                  onNavigateToAuth: () -> Unit,
                  onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            CovyBottomNavigationBarAccount()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CovyDarkGreen)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item { TopAppBarAccount() }

                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "ESPACE CHAUFFEUR · COMPTE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CovyGreenText,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Mon compte chauffeur",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // Profil
                            uiState.profile?.let { ProfileCard(it, viewModel::onEditProfileClick) }
                            Spacer(modifier = Modifier.height(16.dp))

                            // Documents
                            DocumentsCard(uiState.documents)
                            Spacer(modifier = Modifier.height(16.dp))

                            // Abonnement
                            uiState.subscription?.let { SubscriptionCard(it, viewModel::onManageSubscriptionClick) }
                            Spacer(modifier = Modifier.height(24.dp))

                            // Liens du bas
                            FooterLinks(
                                onNavigateToAuth = {
                                    viewModel.onLogoutClick()
                                    onNavigateToAuth()
                                }
                            )
                                }

                        }
                    }
                }
            }
        }
}


@Composable
fun TopAppBarAccount() {
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

        // Logo Covy (Placeholder)
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
            text = "29 / 30",
            fontSize = 14.sp,
            color = TextTertiary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileCard(profile: DriverProfile, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar (Placeholder avec icône)
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(BackgroundGray)
                        .border(1.dp, AvatarBorderGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = TextSecondary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = profile.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = profile.carModel,
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${profile.plateNumber} · ",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "★ ${profile.rating}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CovyGreenText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = profile.fullDescription,
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Modifier mon profil →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CovyDarkGreen,
                modifier = Modifier.clickable { onEditClick() }
            )
        }
    }
}

@Composable
fun DocumentsCard(documents: List<DriverDocument>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Documents du véhicule",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            documents.forEach { doc ->
                DocumentItem(doc)
            }
        }
    }
}

@Composable
fun DocumentItem(doc: DriverDocument) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icône du document (générique ou spécifique selon le titre)
        Icon(
            imageVector = when {
                doc.title.contains("Permis", ignoreCase = true) -> Icons.Outlined.AccountBox
                doc.title.contains("Assurance", ignoreCase = true) -> Icons.Default.Security
                else -> Icons.Default.Description
            },
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = doc.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Text(
                text = doc.subtitle,
                fontSize = 11.sp,
                color = TextSecondary
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = CovyGreenText,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = doc.status,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CovyGreenText
            )
        }
    }
}

@Composable
fun SubscriptionCard(subscription: SubscriptionInfo, onManageClick: () -> Unit) {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SubscriptionBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Abonnement actif",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = CovyDarkGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subscription.planName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "${formatter.format(subscription.price)} ",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreenText
                )
                Text(
                    text = "FCFA / mois",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkGreenText,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Renouvellement : ${subscription.renewalDate}",
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Gérer ma formule →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CovyDarkGreen,
                modifier = Modifier.clickable { onManageClick() }
            )
        }
    }
}

@Composable
fun FooterLinks(
                onNavigateToAuth: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Action aide */ }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Aide et assistance chauffeur →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CovyDarkGreen
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToAuth() }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Se déconnecter",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = LogoutRed
            )
        }
    }
}

@Composable
fun CovyBottomNavigationBarAccount() {
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
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Courses", modifier = Modifier.size(22.dp)) },
            label = { Text("Courses", fontSize = 10.sp) },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = TextTertiary,
                unselectedTextColor = TextTertiary
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Wallet, contentDescription = "Revenus", modifier = Modifier.size(22.dp)) },
            label = { Text("Revenus", fontSize = 10.sp) },
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
                        Icons.Default.Person,
                        contentDescription = "Compte",
                        tint = CovyDarkGreen,
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            label = { Text("Compte", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CovyDarkGreen) },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}