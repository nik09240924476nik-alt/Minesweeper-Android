package com.example.minesweeper.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.minesweeper.data.sound.SoundManager
import com.example.minesweeper.domain.game.Cell
import com.example.minesweeper.domain.game.Difficulty
import com.example.minesweeper.domain.game.GameEngine
import com.example.minesweeper.domain.game.GameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Изменяем на AndroidViewModel, чтобы получить контекст для SoundManager
class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val soundManager = SoundManager(application.applicationContext)

    private var difficulty = Difficulty.EASY
    private var engine = createEngine()

    val board = mutableStateOf<List<List<Cell>>>(emptyList())
    val gameState = mutableStateOf(GameState.PLAYING)
    val timer = mutableIntStateOf(0)
    val currentDifficulty = mutableStateOf(difficulty)

    private var timerStarted = false

    // Исправлено: игра считается активной, если таймер был запущен и мы всё еще в процессе игры
    val hasActiveGame: Boolean
        get() = timerStarted && gameState.value == GameState.PLAYING

    init {
        refreshBoard()
    }

    private fun createEngine(): GameEngine {
        return GameEngine(
            rows = difficulty.rows,
            cols = difficulty.cols,
            mines = difficulty.mines
        )
    }

    fun setDifficulty(newDifficulty: Difficulty) {
        difficulty = newDifficulty
        currentDifficulty.value = newDifficulty
        restartGame()
    }

    fun onCellClick(row: Int, col: Int) {
        if (gameState.value != GameState.PLAYING) return

        if (!timerStarted) {
            timerStarted = true
            soundManager.startBackgroundMusic() // Включаем музыку при старте игры
            startTimer()
        }

        engine.openCell(row, col)

        // Проверяем состояние после клика для выбора звука
        val oldState = gameState.value
        refreshBoard()

        when (gameState.value) {
            GameState.WON -> {
                soundManager.stopBackgroundMusic()
                soundManager.playVictory()
            }
            GameState.LOST -> {
                soundManager.stopBackgroundMusic()
                soundManager.playExplosion()
            }
            GameState.PLAYING -> {
                soundManager.playClick() // Обычный клик
            }
        }
    }

    fun onCellLongClick(row: Int, col: Int) {
        if (gameState.value != GameState.PLAYING) return

        engine.toggleFlag(row, col)
        soundManager.playFlag() // Звук флага
        refreshBoard()
    }

    fun restartGame() {
        soundManager.stopBackgroundMusic()
        engine = createEngine()
        timer.intValue = 0
        timerStarted = false
        refreshBoard()
    }

    // Добавлено: метод останавливает только музыку, сохраняя состояние игры для возможности продолжения
    fun pauseGameMusic() {
        soundManager.stopBackgroundMusic()
    }

    private fun refreshBoard() {
        board.value = engine.board.map { row ->
            row.map { cell ->
                cell.copy()
            }
        }
        gameState.value = engine.gameState
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (gameState.value == GameState.PLAYING) {
                delay(1000)
                timer.intValue++
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.stopBackgroundMusic() // Очищаем ресурсы при уничтожении ViewModel
    }
}