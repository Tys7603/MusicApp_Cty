package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lyric(
    @SerializedName("startMs")
    val startTimeMs: Long,
    @SerializedName("lyric_text")
    val text: String
) : Parcelable