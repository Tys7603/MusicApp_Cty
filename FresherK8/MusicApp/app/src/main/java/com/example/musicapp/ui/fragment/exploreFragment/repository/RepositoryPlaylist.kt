package com.example.musicapp.ui.fragment.exploreFragment.repository

import com.example.musicapp.model.Playlist

data class RepositoryPlaylist (
    val status: Int,
    val playlists: ArrayList<Playlist>
)