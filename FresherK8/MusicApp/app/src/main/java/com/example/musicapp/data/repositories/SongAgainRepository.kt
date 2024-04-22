package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.SongAgain

data class SongAgainRepository (
    val status: Int,
    val songAgain: ArrayList<SongAgain>
)