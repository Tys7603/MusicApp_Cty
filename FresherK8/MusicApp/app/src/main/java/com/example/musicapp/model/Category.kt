package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class Category(
    val id : Int,
    val name : String,
    val image : String,
    @SerializedName("topic_id")
    val topicId : Int
)