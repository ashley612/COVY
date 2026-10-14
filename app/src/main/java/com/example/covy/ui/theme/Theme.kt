package com.example.covy.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
// ==========================================
// COLOR SCHEME COVY - MODE CLAIR
// ==========================================
private val CovyLightColorScheme = lightColorScheme(
    primary = CovyForestGreen,           // Couleur principale (boutons, liens)
    onPrimary = Color.White,             // Texte sur primary
    primaryContainer = CovyLightGreenBg, // Fond vert clair
    onPrimaryContainer = CovyForestGreen,

    secondary = CovyBadgeTextGreen,      // Couleur secondaire (badges)
    onSecondary = Color.White,

    background = CovyBackground,         // Fond général
    onBackground = CovyTextDark,         // Texte sur fond

    surface = Color.White,               // Cartes, surfaces
    onSurface = CovyTextDark,            // Texte sur surface
    surfaceVariant = CovyInputBg,        // Fond des inputs
    onSurfaceVariant = CovyTextMuted,    // Texte secondaire

    outline = CovyBorderCard,            // Bordures
    outlineVariant = CovyBorderGreen,

    error = CovyErrorRed,                // Erreur
    onError = Color.White
)

// ==========================================
// COLOR SCHEME COVY - MODE SOMBRE
// ==========================================
private val CovyDarkColorScheme = darkColorScheme(
    primary = CovyForestGreenDark,
    onPrimary = Color.White,
    primaryContainer = CovyForestGreen,
    onPrimaryContainer = Color.White,

    secondary = CovyBadgeTextGreen,
    onSecondary = Color.White,

    background = CovyBackgroundDark,
    onBackground = CovyTextDarkDark,

    surface = Color(0xFF1F2937),
    onSurface = CovyTextDarkDark,
    surfaceVariant = Color(0xFF374151),
    onSurfaceVariant = CovyTextMutedDark,

    outline = CovyBorderCard,
    outlineVariant = CovyBorderGreen,

    error = CovyErrorRed,
    onError = Color.White
)

// ==========================================
// COVY THEME
// ==========================================
@Composable
fun CovyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color (Material You) — Android 12+
    dynamicColor: Boolean = false,  // ← false par défaut pour garder les couleurs Covy
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Si tu veux activer Material You, mets dynamicColor = true
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> CovyDarkColorScheme
        else -> CovyLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}