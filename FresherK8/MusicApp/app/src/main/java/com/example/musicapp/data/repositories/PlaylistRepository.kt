package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.Playlist

data class PlaylistRepository (
    val status: Int,
    val playlists: ArrayList<Playlist>
)