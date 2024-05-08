package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.Album

data class AlbumRepository (
    val status : Int,
    val albums: ArrayList<Album>
)