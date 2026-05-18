package com.example.minesweeper.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minesweeper.domain.game.Difficulty
import com.example.minesweeper.domain.game.GameState
import com.example.minesweeper.ui.viewmodel.GameViewModel

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun GameScreen(

    onBack: () -> Unit,

    vm: GameViewModel = viewModel()
) {

    val board = vm.board.value

    val gameState = vm.gameState.value

    val timer = vm.timer.intValue

    val difficulty =
        vm.currentDifficulty.value

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        text = when(gameState) {

                            GameState.PLAYING ->
                                "Сапёр"

                            GameState.WON ->
                                "🎉 Победа"

                            GameState.LOST ->
                                "💀 Поражение"
                        }
                    )
                },

                navigationIcon = {

                    TextButton(

                        onClick = onBack
                    ) {

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
        ) {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),

                horizontalArrangement =
                    Arrangement.End
            ) {

                Text(
                    text = "⏱ ${timer}с"
                )
            }

            DifficultySelector(vm)

            Button(

                onClick = {

                    vm.restartGame()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                Text("Заново")
            }

            LazyVerticalGrid(

                columns =
                    GridCells.Fixed(
                        difficulty.cols
                    ),

                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {

                items(board.flatten()) { cell ->

                    val interactionSource =
                        remember {
                            MutableInteractionSource()
                        }

                    val scale by
                    animateFloatAsState(

                        targetValue =
                            if (cell.isOpened)
                                0.95f
                            else
                                1f,

                        animationSpec =
                            tween(120),

                        label = ""
                    )

                    val backgroundColor by
                    animateColorAsState(

                        targetValue = when {

                            cell.isOpened &&
                                    cell.isMine ->

                                Color.Red

                            cell.isOpened ->

                                Color.LightGray

                            else ->

                                Color.DarkGray
                        },

                        label = ""
                    )

                    Card(

                        modifier = Modifier
                            .padding(2.dp)
                            .aspectRatio(1f)
                            .scale(scale)
                            .combinedClickable(

                                interactionSource =
                                    interactionSource,

                                indication = null,

                                onClick = {

                                    vm.onCellClick(
                                        cell.row,
                                        cell.col
                                    )
                                },

                                onLongClick = {

                                    vm.onCellLongClick(
                                        cell.row,
                                        cell.col
                                    )
                                }
                            ),

                        shape =
                            RoundedCornerShape(8.dp)
                    ) {

                        Box(

                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    backgroundColor
                                ),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            val text = when {

                                cell.isFlagged ->
                                    "🚩"

                                !cell.isOpened ->
                                    ""

                                cell.isMine ->
                                    "💣"

                                cell.adjacentMines > 0 ->
                                    cell.adjacentMines.toString()

                                else -> ""
                            }

                            Text(

                                text = text,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DifficultySelector(
    vm: GameViewModel
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        horizontalArrangement =
            Arrangement.SpaceEvenly
    ) {

        Difficulty.entries.forEach {

            FilterChip(

                selected =
                    vm.currentDifficulty.value == it,

                onClick = {

                    vm.setDifficulty(it)
                },

                label = {

                    Text(it.title)
                }
            )
        }
    }
}