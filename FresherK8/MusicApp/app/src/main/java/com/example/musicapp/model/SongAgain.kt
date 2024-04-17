package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class SongAgain (
    @SerializedName("song_id")
    val int: Int,
    @SerializedName("song_name")
    val name: String,
    @SerializedName("song_image")
    val image: String,
    @SerializedName("song_url")
    val url: String
)