package com.example.musicapp.ui.fragment.exploreFragment.repository

import com.example.musicapp.model.Song

data class RepositorySong (
    val status: Int,
    val songs: ArrayList<Song>
)