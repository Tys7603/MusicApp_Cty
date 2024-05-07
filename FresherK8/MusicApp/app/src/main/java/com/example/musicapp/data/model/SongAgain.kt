package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class SongAgain (
    @SerializedName("song_id")
    val id: Int,
    @SerializedName("song_name")
    val name: String,
    @SerializedName("song_image")
    val image: String,
    @SerializedName("song_url")
    val url: String
)