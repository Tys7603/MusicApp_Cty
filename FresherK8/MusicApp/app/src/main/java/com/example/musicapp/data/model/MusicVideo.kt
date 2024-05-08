package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class MusicVideo(
    @SerializedName("music_video_id")
    val musicVideoId: String,
    @SerializedName("music_video_name")
    val musicVideoName: String,
    @SerializedName("artist_name")
    val artistName: String,
    @SerializedName("artist_image")
    val artistImage: String,
    @SerializedName("category_id")
    val categoryId: Int
)