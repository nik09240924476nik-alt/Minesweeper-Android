package com.example.minesweeper.domain.game

data class Cell(

    val row: Int,
    val col: Int,

    var isMine: Boolean = false,

    var isOpened: Boolean = false,

    var isFlagged: Boolean = false,

    var adjacentMines: Int = 0
)