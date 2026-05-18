package com.example.minesweeper.data.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.minesweeper.R
import com.example.minesweeper.data.preferences.SettingsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SoundManager(private val context: Context) {

    private val settingsManager = SettingsManager(context)
    private val scope = CoroutineScope(Dispatchers.IO)

    private var soundPool: SoundPool
    private var bgPlayer: MediaPlayer? = null

    // Идентификаторы коротких звуков
    private var soundClick = 0
    private var soundFlag = 0
    private var soundExplosion = 0
    private var soundTic = 0
    private var soundVictory = 0

    init {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(attrs)
            .build()

        // Асинхронно загружаем звуки из папки raw
        soundClick = soundPool.load(context, R.raw.click, 1)
        soundFlag = soundPool.load(context, R.raw.flag, 1)
        soundExplosion = soundPool.load(context, R.raw.explosion, 1)
        soundTic = soundPool.load(context, R.raw.tic, 1)
        soundVictory = soundPool.load(context, R.raw.victory, 1)
    }

    // Вспомогательный метод для проверки настроек перед воспроизведением
    private fun playShortSound(soundId: Int) {
        scope.launch {
            val isSoundEnabled = settingsManager.soundFlow.first()
            if (isSoundEnabled && soundId != 0) {
                soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            }
        }
    }

    fun playClick() = playShortSound(soundClick)
    fun playFlag() = playShortSound(soundFlag)
    fun playExplosion() = playShortSound(soundExplosion)
    fun playTic() = playShortSound(soundTic)
    fun playVictory() = playShortSound(soundVictory)

    // Управление фоновой музыкой
    fun startBackgroundMusic() {
        scope.launch {
            val isSoundEnabled = settingsManager.soundFlow.first()
            if (!isSoundEnabled) return@launch

            if (bgPlayer == null) {
                bgPlayer = MediaPlayer.create(context, R.raw.background_music).apply {
                    isLooping = true // Зацикливание песни
                    setVolume(0.5f, 0.5f) // Делаем музыку чуть тише эффектов
                }
            }
            if (bgPlayer?.isPlaying == false) {
                bgPlayer?.start()
            }
        }
    }

    fun stopBackgroundMusic() {
        if (bgPlayer?.isPlaying == true) {
            bgPlayer?.stop()
            bgPlayer?.release()
            bgPlayer = null
        }
    }
}