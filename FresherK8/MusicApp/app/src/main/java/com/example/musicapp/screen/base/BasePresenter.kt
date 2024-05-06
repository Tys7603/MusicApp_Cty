package com.example.musicapp.screen.base

interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
    fun onDestroy()
}