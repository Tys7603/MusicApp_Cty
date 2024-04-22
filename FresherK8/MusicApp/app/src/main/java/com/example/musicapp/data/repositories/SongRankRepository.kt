package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.SongRank

data class SongRankRepository (
    val status : Int,
    val songRanks : ArrayList<SongRank>
)