package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.PlaylistUser

data class PlaylistUserRepository (
    val status: Int,
    val playlists: ArrayList<PlaylistUser>
)