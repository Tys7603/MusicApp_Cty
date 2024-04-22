package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class Topic (
    @SerializedName("topic_id")
    val id : Int,
    @SerializedName("topic_name")
    val name : String,
    @SerializedName("topic_image")
    val image : String
)