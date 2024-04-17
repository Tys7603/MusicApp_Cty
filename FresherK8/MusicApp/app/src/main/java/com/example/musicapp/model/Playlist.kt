package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class Playlist (
    @SerializedName("playlist_id")
    var id : Int,
    @SerializedName("playlist_name")
    var name : String,
    @SerializedName("playlist_image")
    var image : String
)