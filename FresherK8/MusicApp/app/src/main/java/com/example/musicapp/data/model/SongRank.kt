package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class SongRank (
    @SerializedName("rank_name")
    val rankName : String,
    val songs : ArrayList<Song>
)