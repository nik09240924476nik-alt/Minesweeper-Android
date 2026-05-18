package com.example.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minesweeper.data.preferences.SettingsManager
import com.example.minesweeper.ui.navigation.AppNav
import com.example.minesweeper.ui.theme.*
import com.example.minesweeper.ui.viewmodel.GameViewModel

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
                    // --- ДОБАВЛЕННЫЙ БЛОК ДЛЯ ВЫКЛЮЧЕНИЯ МУЗЫКИ ---
                    val gameViewModel: GameViewModel = viewModel()
                    val lifecycleOwner = LocalLifecycleOwner.current

                    DisposableEffect(lifecycleOwner) {
                        val observer = LifecycleEventObserver { _, event ->
                            // Когда приложение сворачивают (ON_STOP) — тушим музыку
                            if (event == Lifecycle.Event.ON_STOP) {
                                gameViewModel.pauseGameMusic()
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }
                    // ----------------------------------------------

                    AppNav()
                }
            }
        }
    }
}