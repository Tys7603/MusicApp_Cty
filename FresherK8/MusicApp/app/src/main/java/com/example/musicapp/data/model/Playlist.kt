package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlist (
    @SerializedName("playlist_id")
    var id : Int,
    @SerializedName("playlist_name")
    var name : String,
    @SerializedName("playlist_image")
    var image : String,
    @SerializedName("name_artist")
    var nameArtist : String,
    @SerializedName("playlist_user_love_id")
    var playlistUserLoveId : Int,
    @SerializedName("song_count")
    var songCount : Int,
    var isSelected : Boolean
) : Parcelable