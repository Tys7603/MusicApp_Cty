package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.Follow

data class FollowRepository (
    val status : Int,
    val follows : List<Follow>
)