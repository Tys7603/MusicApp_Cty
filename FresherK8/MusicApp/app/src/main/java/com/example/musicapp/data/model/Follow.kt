package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class Follow (
    @SerializedName("follow_id")
    val followId : Int,
    @SerializedName("user_id")
    val userId : String,
    @SerializedName("artist_id")
    val artistId : Int,
    @SerializedName("artist_name")
    val artistName : String,
    @SerializedName("artist_image")
    val artistImage : String,
    @SerializedName("quantity")
    val quantity : Int,
    @SerializedName("isFollow")
    val isFollow : Int
)