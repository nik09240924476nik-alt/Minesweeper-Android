package com.example.minesweeper.domain.game

import kotlin.random.Random

class GameEngine(
    private val rows: Int,
    private val cols: Int,
    private val mines: Int
) {

    val board = MutableList(rows) { r ->
        MutableList(cols) { c ->
            Cell(r, c)
        }
    }

    var gameState = GameState.PLAYING

    init {
        placeMines()
        calculateNumbers()
    }

    private fun placeMines() {

        var placed = 0

        while (placed < mines) {

            val r = Random.nextInt(rows)
            val c = Random.nextInt(cols)

            val cell = board[r][c]

            if (!cell.isMine) {

                cell.isMine = true
                placed++
            }
        }
    }

    private fun calculateNumbers() {

        for (r in 0 until rows) {

            for (c in 0 until cols) {

                val cell = board[r][c]

                if (cell.isMine) continue

                var count = 0

                for (dr in -1..1) {

                    for (dc in -1..1) {

                        val nr = r + dr
                        val nc = c + dc

                        if (
                            nr in 0 until rows &&
                            nc in 0 until cols &&
                            board[nr][nc].isMine
                        ) {
                            count++
                        }
                    }
                }

                cell.adjacentMines = count
            }
        }
    }

    fun openCell(row: Int, col: Int) {

        if (gameState != GameState.PLAYING) {
            return
        }

        val cell = board[row][col]

        if (cell.isOpened || cell.isFlagged) {
            return
        }

        cell.isOpened = true

        if (cell.isMine) {

            gameState = GameState.LOST

            revealAllMines()

            return
        }

        if (cell.adjacentMines == 0) {

            for (dr in -1..1) {

                for (dc in -1..1) {

                    val nr = row + dr
                    val nc = col + dc

                    if (
                        nr in 0 until rows &&
                        nc in 0 until cols
                    ) {
                        openCell(nr, nc)
                    }
                }
            }
        }

        checkWin()
    }

    fun toggleFlag(row: Int, col: Int) {

        if (gameState != GameState.PLAYING) {
            return
        }

        val cell = board[row][col]

        if (!cell.isOpened) {

            cell.isFlagged = !cell.isFlagged
        }
    }

    private fun revealAllMines() {

        for (row in board) {

            for (cell in row) {

                if (cell.isMine) {

                    cell.isOpened = true
                }
            }
        }
    }

    private fun checkWin() {

        for (row in board) {

            for (cell in row) {

                if (
                    !cell.isMine &&
                    !cell.isOpened
                ) {
                    return
                }
            }
        }

        gameState = GameState.WON
    }
}