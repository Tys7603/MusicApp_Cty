package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class AlbumNew (
    @SerializedName("album_id")
    val albumId: Int,
    @SerializedName("album_name")
    val albumName: String,
    @SerializedName("album_image")
    val albumImage: String,
    @SerializedName("name_artist")
    val nameArtist: String
)