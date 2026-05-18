package com.example.minesweeper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minesweeper.data.sound.SoundManager
import com.example.minesweeper.ui.menu.MenuScreen
import com.example.minesweeper.ui.screen.GameScreen
import com.example.minesweeper.ui.screen.DifficultyScreen
import com.example.minesweeper.ui.screen.TutorialScreen
import com.example.minesweeper.ui.settings.SettingsScreen
import com.example.minesweeper.ui.viewmodel.GameViewModel

object Routes {
    const val MENU = "menu"
    const val GAME = "game"
    const val SETTINGS = "settings"
    const val DIFFICULTY = "difficulty"
    const val TUTORIAL = "tutorial"
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()
    val context = LocalContext.current

    // Создаем SoundManager для обработки звуков интерфейса навигации
    val soundManager = SoundManager(context)

    NavHost(
        navController = navController,
        startDestination = Routes.MENU
    ) {
        composable(Routes.MENU) {
            MenuScreen(
                hasActiveGame = gameViewModel.hasActiveGame,
                soundManager = soundManager,
                onPlay = { navController.navigate(Routes.DIFFICULTY) },
                onContinue = { navController.navigate(Routes.GAME) },
                onSettings = { navController.navigate(Routes.SETTINGS) },
                onTutorial = { navController.navigate(Routes.TUTORIAL) }
            )
        }

        composable(Routes.DIFFICULTY) {
            DifficultyScreen(
                onSelect = { difficulty ->
                    gameViewModel.setDifficulty(difficulty)
                    navController.navigate(Routes.GAME) {
                        popUpTo(Routes.MENU)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.GAME) {
            GameScreen(
                vm = gameViewModel,
                onBack = {
                    // ИСПРАВЛЕНО: Вместо restartGame() вызываем pauseGameMusic().
                    // Теперь игра не сбрасывается, кнопка "Продолжить" работает,
                    // а контекст приложения и темная тема не ломаются!
                    gameViewModel.pauseGameMusic()
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.MENU) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.TUTORIAL) {
            TutorialScreen(onBack = { navController.popBackStack() })
        }
    }
}