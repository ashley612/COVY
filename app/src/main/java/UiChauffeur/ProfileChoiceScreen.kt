package com.example.covy.UiChauffeur
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covy.R
import com.example.covy.ui.theme.CovyTheme

// ==========================================
// Ã‰CRAN : "Qui Ãªtes-vous ?" (premier Ã©cran de l'appli)
// ==========================================

@Composable
fun ProfileChoiceScreen(
    onPassagerClick: () -> Unit = {},
    onConducteurClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { CovyBrandHeader() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Qui Ãªtes-vous ?",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Choisissez votre profil pour commencer votre expÃ©rience Covy.",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Les deux cartes cÃ´te Ã  cÃ´te, de mÃªme hauteur
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileCard(
                    icon = Icons.Default.Person,
                    title = "Passager",
                    description = "Je rejoins mon campus",
                    tag = "Ã‰lÃ¨ve ou Ã©tudiant",
                    onClick = onPassagerClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                ProfileCard(
                    icon = Icons.Default.DirectionsCar,
                    title = "Conducteur",
                    description = "Je transporte des passagers",
                    tag = "Conducteur",
                    onClick = onConducteurClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    }
}

// ==========================================
// CARTE DE PROFIL
// ==========================================

@Composable
private fun ProfileCard(
    icon: ImageVector,
    title: String,
    description: String,
    tag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // IcÃ´ne dans un carrÃ© vert clair
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Petite pastille en bas de la carte
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = tag,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// ==========================================
// EN-TÃŠTE : logo + nom (sans bouton retour)
// ==========================================

@Composable
private fun CovyBrandHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.covy_logo),
            contentDescription = "Logo Covy",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Covy",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewProfileChoiceScreen() {
    CovyTheme {
        ProfileChoiceScreen()
    }
}
