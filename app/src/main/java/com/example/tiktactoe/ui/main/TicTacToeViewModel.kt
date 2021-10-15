package com.example.tiktactoe.ui.main

import androidx.lifecycle.ViewModel
import com.example.tiktactoe.game.Player
import com.example.tiktactoe.game.TicTacToeGame
import kotlinx.coroutines.flow.StateFlow

class TicTacToeViewModel : ViewModel() {
    private lateinit var game: TicTacToeGame

    fun init() {
        game = TicTacToeGame()
    }

    fun getGame(): TicTacToeGame {
        return game
    }

    fun getBoardCellsNumber(): Int {
        return game.boardCellsNumber
    }

    fun gameReset() {
        game.reset()
    }

    fun getWinner(): StateFlow<Player?> {
        return game.getWinner()
    }

    fun getState(): StateFlow<String?> {
        return game.getState()
    }
}
