package com.example.musicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lyric(
    val startTimeMs: Long,
    val text: String
) : Parcelable