package com.example.minesweeper.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.minesweeper.data.preferences.SettingsManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }
    val scope = rememberCoroutineScope()

    val sound by settingsManager.soundFlow.collectAsState(initial = true)
    val vibration by settingsManager.vibrationFlow.collectAsState(initial = true)
    val amoled by settingsManager.amoledFlow.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("←") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            SettingSwitch(
                title = "Звуки",
                checked = sound,
                onChecked = { scope.launch { settingsManager.setSound(it) } }
            )

            SettingSwitch(
                title = "Вибрация",
                checked = vibration,
                onChecked = { scope.launch { settingsManager.setVibration(it) } }
            )

            SettingSwitch(
                title = "Тёмная тема",
                checked = amoled,
                onChecked = { scope.launch { settingsManager.setAmoled(it) } }
            )
        }
    }
}

@Composable
fun SettingSwitch(
    title: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit // Исправили "=" на ":"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}