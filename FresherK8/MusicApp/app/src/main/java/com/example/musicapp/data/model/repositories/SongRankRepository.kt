package com.example.musicapp.data.model.repositories

import com.example.musicapp.data.model.SongRank

data class SongRankRepository (
    val status : Int,
    val songRanks : ArrayList<SongRank>
)