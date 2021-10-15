package com.example.tiktactoe.game

import androidx.lifecycle.MutableLiveData
import java.util.*

class TicTacToeGame {

    private val playerQueue = LinkedList<Player>()
    private var currentPlayer: Player? = null
    private lateinit var cells: Array<Array<Cell?>>
    private val winner = MutableLiveData<Player?>()
    private val state = MutableLiveData<String>()

    private fun init() {
        cells = Array(BOARD_SIZE) {
            arrayOfNulls(
                BOARD_SIZE
            )
        }

        addPlayers()

        val firstPlayer = playerQueue.poll()
        currentPlayer = firstPlayer
        playerQueue.offer(firstPlayer)
    }

    fun getCells(): Array<Array<Cell?>> {
        return cells
    }

    fun getCurrentPlayer(): Player? {
        return currentPlayer
    }

    fun getWinner(): MutableLiveData<Player?> {
        return winner
    }

    fun getState(): MutableLiveData<String> {
        return state
    }

    private fun addPlayers() {
        for (i in 0 until playersNumber) {
            playerQueue.offer(
                Player(
                    playerSymbols[i]
                )
            )
        }
    }

    fun move(row: Int, column: Int) {
        if (isEnd(row, column)) {
            reset()
        } else {
            switchPlayer()
        }
    }

    private fun isEnd(row: Int, col: Int): Boolean {
        if (isEqualHorizontal(row) || isEqualVertical(col) || isEqualDiagonal) {
            winner.value = currentPlayer
            return true
        }
        if (isBoardFull) {
            winner.value = null
            return true
        }
        return false
    }

    private fun isEqualHorizontal(row: Int): Boolean {
        val list = arrayOfNulls<Cell>(BOARD_SIZE)
        for (i in 0 until BOARD_SIZE) {
            list[i] = cells[row][i]
        }
        return cellsEqual(list)
    }

    private fun isEqualVertical(col: Int): Boolean {
        val list = arrayOfNulls<Cell>(BOARD_SIZE)
        for (i in 0 until BOARD_SIZE) {
            list[i] = cells[i][col]
        }
        return cellsEqual(list)
    }

    private val isEqualDiagonal: Boolean
        get() {
            val diagonal1 =
                arrayOfNulls<Cell>(BOARD_SIZE)
            for (i in 0 until BOARD_SIZE) {
                diagonal1[i] = cells[i][i]
            }

            val diagonal2 =
                arrayOfNulls<Cell>(BOARD_SIZE)
            var j: Int = BOARD_SIZE - 1
            for (i in 0 until BOARD_SIZE) {
                diagonal2[i] = cells[i][j]
                j--
            }
            return cellsEqual(diagonal1) || cellsEqual(diagonal2)
        }

    private fun cellsEqual(cells: Array<Cell?>?): Boolean {
        if (cells == null || cells.isEmpty()) {
            return false
        }
        for (cell in cells) {
            if (cell == null || cell.player?.symbol == null) {
                return false
            }
        }
        val first = cells[0]
        for (i in 1 until cells.size) {
            if (first?.player?.symbol != cells[i]?.player?.symbol) {
                return false
            }
        }
        return true
    }

    private val isBoardFull: Boolean
        get() {
            for (row in cells) {
                for (cell in row) {
                    if (cell == null || cell.isEmpty) {
                        return false
                    }
                }
            }
            return true
        }

    private fun switchPlayer() {
        val lastPlayer = playerQueue.poll()
        currentPlayer = lastPlayer
        playerQueue.offer(lastPlayer)
        state.value = currentPlayer?.symbol
    }

    fun reset() {
        winner.value = null
        playerQueue.clear()
        currentPlayer = null
        init()
    }

    val boardCellsNumber: Int
        get() = BOARD_SIZE * BOARD_SIZE


    companion object {
        const val BOARD_DEFAULT_SYMBOL = " "
        const val BOARD_SIZE = 5
        private const val playersNumber = 3
        private val playerSymbols = arrayOf("X", "O", "@")
    }

    init {
        init()
    }
}
