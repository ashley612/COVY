package com.example.covy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CovyColors = lightColorScheme(
    primary = CovyGreen,
    onPrimary = Color.White,
    background = CovyBackground,
    onBackground = CovyInk,
    surface = Color.White,
    onSurface = CovyInk,
    onSurfaceVariant = CovyMuted,
    secondaryContainer = CovyChip,
    onSecondaryContainer = CovyGreen,
)

@Composable
fun CovyTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = CovyColors, content = content)
}