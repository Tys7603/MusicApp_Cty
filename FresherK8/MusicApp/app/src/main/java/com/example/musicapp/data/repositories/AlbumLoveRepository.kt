package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.AlbumLove

data class AlbumLoveRepository (
    val status : Int,
    val albumLoves: ArrayList<AlbumLove>
)