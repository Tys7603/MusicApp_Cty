package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.MusicVideo

data class MusicVideoRepository (
    val status : Int,
    val musicVideos : ArrayList<MusicVideo>
)