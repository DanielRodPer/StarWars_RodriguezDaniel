package com.dam.liststarwars.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF5722),
    secondary = Color(0xFFFFD7CD),
    tertiary = Color(0xFFFF9800),
    background = Color(0xFFFFF7F2),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFFFEECE7),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFF212121),
    onSurface = Color(0xFF212121),
    outline = Color(0xFFBDBDBD)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFCF1111),
    secondary = Color(0xFF424242),
    tertiary = Color(0xFF9C27B0),
    background = Color(0xFF000000),
    surface = Color(0xFF121212),
    onPrimary = Color(0xFF141414),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFFECEFF1),
    onSurface = Color(0xFFECEFF1),
    outline = Color(0xFF455A64)
)


@Composable
fun ListStarWarsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}