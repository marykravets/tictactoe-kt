package com.example.tiktactoe.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProvider
import com.example.tiktactoe.game.Cell
import com.example.tiktactoe.game.Player
import com.example.tiktactoe.R
import com.example.tiktactoe.databinding.FragmentTictactoeBinding
import com.example.tiktactoe.game.TicTacToeGame
import com.example.tiktactoe.game.TicTacToeGame.Companion.BOARD_DEFAULT_SYMBOL
import com.example.tiktactoe.game.TicTacToeGame.Companion.BOARD_SIZE
import java.util.ArrayList

class TicTacToeFragment : Fragment(), AdapterView.OnItemClickListener, View.OnClickListener {

    private lateinit var viewModel: TicTacToeViewModel
    private lateinit var gameBinding: FragmentTictactoeBinding
    private var defaultSymbolsList: List<String> = ArrayList()
    private var gameWinner: Player? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameBinding =
            inflate(inflater, R.layout.fragment_tictactoe, container, false)

        initViewModel()

        return gameBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defaultSymbolsList = getDefaultSymbols()
        gameBinding.board.adapter = BoardGridAdapter(requireContext(), defaultSymbolsList)
        gameBinding.board.numColumns = BOARD_SIZE

        gameBinding.itemClickListener = this
        gameBinding.newGameButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        gameWinner = null
        gameBinding.currentPlayerState.text = ""
        viewModel.gameReset()

        (gameBinding.board.adapter as BoardGridAdapter).setBoardItemsData(defaultSymbolsList)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(TicTacToeViewModel::class.java)
        viewModel.init()

        viewModel.getWinner()
            .observe(viewLifecycleOwner, { winner: Player? -> onWinnerChanged(winner) })
        viewModel.getState()
            .observe(viewLifecycleOwner, { state: String? -> onStateChanged(state) })
    }

    private fun onWinnerChanged(winner: Player?) {
        if (winner != null) {
            gameWinner = winner
            gameBinding.currentPlayerState.text =
                String.format(resources.getString(R.string.is_winner_text), winner.symbol)
        }
    }

    private fun onStateChanged(state: String?) {
        if (state != null) {
            gameBinding.currentPlayerState.text = state
        }
    }

    private fun getDefaultSymbols(): List<String> {
        val defaultSymbols: MutableList<String> = ArrayList()
        val numCells = viewModel.getBoardCellsNumber()
        for (i in 0..numCells.minus(1)) {
            defaultSymbols.add(BOARD_DEFAULT_SYMBOL)
        }
        return defaultSymbols
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View, index: Int, l: Long) {
        val gridItem = view as TextView
        if (gameWinner != null || gridItem.text != BOARD_DEFAULT_SYMBOL) {
            // not allowing to change grid of the game after game win or when a cell has a symbol
            return
        }
        val game: TicTacToeGame = viewModel.getGame()
        val row: Int = index / BOARD_SIZE
        val column: Int = index % BOARD_SIZE
        if (game.getCells()[row][column] == null) {
            game.getCells()[row][column] = Cell(game.getCurrentPlayer())
            gridItem.text = game.getCurrentPlayer()?.symbol
            game.move(row, column)
        }
    }
}
