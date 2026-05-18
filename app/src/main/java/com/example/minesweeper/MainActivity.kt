package com.example.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.minesweeper.data.preferences.SettingsManager
import com.example.minesweeper.ui.navigation.AppNav
import com.example.minesweeper.ui.theme.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settingsManager = SettingsManager(this)
            val isDarkTheme by settingsManager.amoledFlow.collectAsState(initial = false)

            val colors = if (isDarkTheme) {
                // 🌑 КОМФОРТНАЯ ТЁМНАЯ ТЕМА
                darkColorScheme(
                    background = DarkGrayBg,
                    surface = DarkGrayBg,
                    primary = PastelMint,
                    secondary = PastelCoral,
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            } else {
                // ☀️ МЯГКАЯ СВЕТЛАЯ ТЕМА
                lightColorScheme(
                    primary = PastelBlue,
                    secondary = PastelBlueLight,
                    background = LightBackground,
                    surface = Color.White,
                    onBackground = Color(0xFF1C1B1F),
                    onSurface = Color(0xFF1C1B1F)
                )
            }

            MaterialTheme(
                colorScheme = colors,
                typography = Typography
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNav()
                }
            }
        }
    }
}