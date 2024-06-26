package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongAgain (
    @SerializedName("song_id")
    val id: Int,
    @SerializedName("song_name")
    val name: String,
    @SerializedName("song_image")
    val image: String,
    @SerializedName("name_artist")
    val nameArtist: String,
    @SerializedName("song_url")
    val url: String
) : Parcelable