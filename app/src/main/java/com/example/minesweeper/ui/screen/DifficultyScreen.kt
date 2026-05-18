package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.minesweeper.domain.game.Difficulty

@OptIn(ExperimentalMaterial3Api::class) // Разрешаем использование экспериментального TopAppBar
@Composable
fun DifficultyScreen(
    onSelect: (Difficulty) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Выбор сложности")
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Difficulty.entries.forEach {
                Button(
                    onClick = {
                        onSelect(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(it.title)
                }
            }
        }
    }
}