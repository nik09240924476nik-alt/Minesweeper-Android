package com.example.minesweeper.ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.minesweeper.data.sound.SoundManager

@Composable
fun MenuScreen(
    hasActiveGame: Boolean,
    soundManager: SoundManager, // Принимаем менеджер звуков
    onPlay: () -> Unit,
    onContinue: () -> Unit,
    onSettings: () -> Unit,
    onTutorial: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Сапёр",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (hasActiveGame) {
            Button(
                onClick = {
                    soundManager.playTic() // Звук клика меню
                    onContinue()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Продолжить")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                soundManager.playTic()
                onPlay()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Новая игра")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                soundManager.playTic()
                onSettings()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Настройки")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                soundManager.playTic()
                onTutorial()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обучение")
        }
    }
}