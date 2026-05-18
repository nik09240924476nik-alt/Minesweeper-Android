package com.example.minesweeper.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Оставляем дефолтные схемы, если они понадобятся компонентам Preview
val DarkColorScheme = darkColorScheme(
    primary = PastelMint,
    secondary = PastelCoral,
    background = DarkGrayBg,
    surface = DarkGrayBg
)

val LightColorScheme = lightColorScheme(
    primary = PastelBlue,
    secondary = PastelBlueLight,
    background = LightBackground,
    surface = Color.White
)