package com.example.tiktactoe.game

class Cell(val player: Player?) {
    val isEmpty: Boolean
        get() = player?.symbol == null || player.symbol.isEmpty()
}
