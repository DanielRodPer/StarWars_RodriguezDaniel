package com.dam.liststarwars.ui.common

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

data class OutlinedTextFieldStyle(
    val singleLine: Boolean = true,
    val iconSize: Dp = 24.dp,
    val fillMaxWidth: Boolean = true,
    val iconTint: Color = Color.Gray
)

data class Dimensions(
    val tiny: Dp = 1.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val standard: Dp = 12.dp,
    val large: Dp = 16.dp,
    val big: Dp = 18.dp,
    val extraLarge: Dp = 24.dp,
    val extraBig: Dp = 48.dp,
    val huge: Dp = 60.dp,
    val bigIcon: Dp = 120.dp,
    val header: Dp = 150.dp,
)

val LocalDimensions = staticCompositionLocalOf { Dimensions() }

val DefaultOutlinedTextFieldStyle = OutlinedTextFieldStyle()

val LocalOutlinedTextFieldStyle = staticCompositionLocalOf { DefaultOutlinedTextFieldStyle }
