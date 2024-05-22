package com.example.musicapp.shared.utils

interface OnChangeListener {
    fun onSongChanged()
    interface FragmentChangeListener {
        fun onFragmentChanged()
    }
}