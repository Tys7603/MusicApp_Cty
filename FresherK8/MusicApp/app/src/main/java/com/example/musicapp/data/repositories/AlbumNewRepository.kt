package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.AlbumNew

data class AlbumNewRepository(
    val status: Int,
    val albumNews: ArrayList<AlbumNew>
)