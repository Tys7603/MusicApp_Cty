package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class Search (
    val songs : List<Song>,
    val playlists : List<Playlist>,
    @SerializedName("music_videos")
    val musicVideos : List<MusicVideo>,
    val albums : List<Album>
)