package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Search
import com.example.musicapp.data.model.Song
import com.google.gson.annotations.SerializedName

data class SearchRepository(
    val status : Int,
    val search: Search
)