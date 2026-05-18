package com.example.minesweeper.domain.game

enum class Difficulty(
    val rows: Int,
    val cols: Int,
    val mines: Int,
    val title: String
) {

    EASY(
        rows = 9,
        cols = 9,
        mines = 10,
        title = "Легко"
    ),

    MEDIUM(
        rows = 18,
        cols = 12,
        mines = 35,
        title = "Средне"
    ),

    HARD(
        rows = 24,
        cols = 14,
        mines = 75,
        title = "Сложно"
    )
}