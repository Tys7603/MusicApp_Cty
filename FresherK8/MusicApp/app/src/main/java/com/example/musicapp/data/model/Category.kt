package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("category_id")
    val id : Int,
    @SerializedName("category_name")
    val name : String,
    @SerializedName("category_image")
    val image : String,
    @SerializedName("topic_id")
    val topicId : Int
)