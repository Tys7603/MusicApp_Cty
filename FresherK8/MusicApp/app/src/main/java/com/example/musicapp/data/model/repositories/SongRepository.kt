package com.example.musicapp.data.model.repositories

import com.example.musicapp.data.model.Song

data class SongRepository (
    val status: Int,
    val songs: ArrayList<Song>
)