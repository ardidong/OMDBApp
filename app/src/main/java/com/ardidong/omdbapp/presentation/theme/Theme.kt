package com.ardidong.omdbapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00796B), // Teal
    onPrimary = Color.White,
    primaryContainer = Color(0xFF004D40), // Dark Teal
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFFFC107), // Amber
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFF8E1), // Light Amber
    onSecondaryContainer = Color.Black,
    background = Color(0xFFFFFFFF), // White
    onBackground = Color(0xFF212121), // Dark Gray
    surface = Color.White, // Light Gray
    onSurface = Color(0xFF212121), // Dark Gray
    error = Color(0xFFD32F2F), // Red
    errorContainer = Color(0xFFD32F2F), // Red
    onError = Color.White,
    surfaceTint = Color.White
    // You can specify more colors if needed
)

@Composable
fun OMDBAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}