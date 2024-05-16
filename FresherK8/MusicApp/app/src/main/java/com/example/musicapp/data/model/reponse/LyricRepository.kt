package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Lyric

data class LyricRepository (
    val status : Int,
    val lyrics: ArrayList<Lyric>
)