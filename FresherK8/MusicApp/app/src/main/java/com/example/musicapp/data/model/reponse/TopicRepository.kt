package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.Topic

data class TopicRepository (
    val status : Int,
    val topics : ArrayList<Topic>,
)