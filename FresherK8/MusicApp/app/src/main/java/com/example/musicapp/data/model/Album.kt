package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    @SerializedName("album_id")
    val albumId: Int,
    @SerializedName("album_name")
    val albumName: String,
    @SerializedName("album_image")
    val albumImage: String,
    @SerializedName("name_artist")
    val nameArtist: String
) : Parcelable