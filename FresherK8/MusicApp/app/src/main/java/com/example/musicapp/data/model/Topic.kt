package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    @SerializedName("topic_id")
    val id: Int,
    @SerializedName("topic_name")
    val name: String,
    @SerializedName("topic_image")
    val image: String,
    @SerializedName("category_id")
    val categoryId: Int
) : Parcelable