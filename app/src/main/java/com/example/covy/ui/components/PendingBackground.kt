package com.example.covy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Couleurs estimées depuis la maquette : à remplacer par les codes Figma
private val PendingBackground = Color(0xFFFFF0C9)
private val PendingText = Color(0xFF7A5200)

@Composable
fun CovyBadge(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = PendingBackground,
    contentColor: Color = PendingText
) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = contentColor,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(containerColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}