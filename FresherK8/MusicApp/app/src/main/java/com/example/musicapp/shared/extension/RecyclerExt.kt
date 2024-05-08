package com.example.musicapp.shared.extension

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setAdapterLinearHorizontal(adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    this.adapter = adapter
}

fun RecyclerView.setAdapterLinearVertical(adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    this.adapter = adapter
}

fun RecyclerView.setAdapterGrid(adapter: RecyclerView.Adapter<*>) {
    this.layoutManager =  GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
    this.adapter = adapter
}

fun RecyclerView.setAdapterGridVertical(adapter: RecyclerView.Adapter<*>) {
    this.layoutManager =  GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    this.adapter = adapter
}