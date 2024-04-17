package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class AlbumLove (
    @SerializedName("album_id")
    val albumId : Int,
    @SerializedName("album_name")
    val albumName : String,
    @SerializedName("album_image")
    val albumImage : String,
    @SerializedName("total_likes")
    val totalLikes: Int
)