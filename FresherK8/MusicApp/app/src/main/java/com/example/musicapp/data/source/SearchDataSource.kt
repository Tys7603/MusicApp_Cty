package com.example.musicapp.data.source

import com.example.musicapp.data.model.reponse.SearchAllRepository
import com.example.musicapp.data.model.reponse.SearchRepository
import retrofit2.Response

interface SearchDataSource {
    suspend fun getSearchAllName() : Response<SearchAllRepository>
    suspend fun getSearchKeyWord(keyword : String) : Response<SearchRepository>
}