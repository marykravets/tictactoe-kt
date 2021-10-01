package com.example.tiktactoe.game

class Cell(player: Player?) {
    val player: Player? = player
    val isEmpty: Boolean
        get() = player?.symbol == null || player.symbol.isEmpty()
}
