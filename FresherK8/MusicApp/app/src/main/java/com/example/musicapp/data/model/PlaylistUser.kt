package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistUser (
    @SerializedName("playlist_user_id")
    var playlistUserId : Int,
    @SerializedName("playlist_user_name")
    var playlistUserName : String,
    @SerializedName("song_count")
    var songQuantity : Int,
    @SerializedName("song_image")
    val songImage : String,
    var isSelected: Boolean = false
) : Parcelable