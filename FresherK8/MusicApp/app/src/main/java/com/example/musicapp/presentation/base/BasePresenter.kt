package com.example.musicapp.presentation.base

interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
    fun onDestroy()
}