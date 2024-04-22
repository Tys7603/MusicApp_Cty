package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class Song (
    @SerializedName("song_name")
    val name: String,
    @SerializedName("song_image")
    val image: String,
    @SerializedName("song_url")
    val url: String,
    @SerializedName("name_artist")
    val nameArtis : String
)