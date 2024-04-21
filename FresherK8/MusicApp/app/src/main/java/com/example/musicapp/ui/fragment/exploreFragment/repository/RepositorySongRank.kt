package com.example.musicapp.ui.fragment.exploreFragment.repository

import com.example.musicapp.model.SongRank

data class RepositorySongRank (
    val status : Int,
    val songRanks : ArrayList<SongRank>
)