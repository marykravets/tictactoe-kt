package com.example.tiktactoe.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tiktactoe.R

class BoardGridAdapter(context: Context, private var boardItemsData: List<String>) :
    ArrayAdapter<String?>(context, 0, boardItemsData) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var cellView = view
        if (cellView == null) {
            cellView =
                LayoutInflater.from(context).inflate(R.layout.board_cell, parent, false) as TextView
        }
        if (cellView is TextView) {
            cellView.text = boardItemsData[position]
        }
        return cellView
    }

    fun setBoardItemsData(list: List<String>) {
        boardItemsData = list
        notifyDataSetChanged()
    }
}
