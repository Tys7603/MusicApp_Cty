package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class Song (
    val name: String,
    val image: String,
    val url: String,
    @SerializedName("name_artist")
    val nameArtis : String
)