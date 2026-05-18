package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Обучение") },
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
                .verticalScroll(rememberScrollState())
        ) {
            Text("🎯 Главная цель", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Вам нужно открыть все клетки на поле, в которых нет мин. Если вы откроете клетку с миной — игра тут же завершится поражением.")

            Spacer(Modifier.height(16.dp))

            Text("🕹️ Управление", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("• Обычное нажатие (Клик): открывает выбранную клетку.")
            Text("• Долгое нажатие: ставит или убирает флажок 🚩. Используйте его, если уверены, что там спрятана мина. Флажки защищают вас от случайного нажатия.")

            Spacer(Modifier.height(16.dp))

            Text("🔢 Что значат цифры?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Когда вы открываете пустую клетку, в ней появляется цифра (от 1 до 8). Она показывает, сколько мин находится в соседних клетках вокруг текущей (включая диагонали).")
            Text("Если вокруг открытой клетки вообще нет мин, автоматически откроются и все соседние пустые ячейки.")

            Spacer(Modifier.height(16.dp))

            Text("💡 Совет для победы", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Логически сопоставляйте цифры на соседних клетках. Например, если у клетки стоит цифра '1' и вокруг неё осталась всего одна закрытая ячейка — там гарантированно мина! Пометьте её флажком.")
        }
    }
}