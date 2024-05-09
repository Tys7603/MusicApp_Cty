package com.example.musicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicVideo(
    @SerializedName("music_video_id")
    val musicVideoId: String,
    @SerializedName("music_video_name")
    val musicVideoName: String,
    @SerializedName("music_video_image")
    val musicVideoImage: String,
    @SerializedName("music_video_time")
    val musicVideoTime: String,
    @SerializedName("music_video_proposal_new")
    val musicVideoProposalNew: Int,
    @SerializedName("artist_name")
    val artistName: String,
    @SerializedName("artist_image")
    val artistImage: String,
    @SerializedName("topic_id")
    val topicId: Int
) : Parcelable