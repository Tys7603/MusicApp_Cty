package com.example.musicapp.data.model.repositories

import com.example.musicapp.data.model.Playlist

data class PlaylistRepository (
    val status: Int,
    val playlists: ArrayList<Playlist>
)