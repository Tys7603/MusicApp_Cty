package com.example.musicapp.data.model.reponse

import com.example.musicapp.data.model.SearchAll

data class SearchAllRepository(
    val status : Int,
    val search : List<SearchAll>
)