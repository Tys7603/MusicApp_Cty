package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    @SerializedName("song_love_id")
    val songLoveId: Int,
    @SerializedName("song_id")
    val id: Int,
    @SerializedName("song_name")
    val name: String,
    @SerializedName("song_image")
    val image: String,
    @SerializedName("song_url")
    val url: String,
    @SerializedName("name_artist")
    val nameArtis: String,
    @SerializedName("download")
    var download: Int
) : Parcelable