package com.example.musicapp.data.source.remote

import com.example.musicapp.data.model.reponse.SearchRepository
import com.example.musicapp.data.source.SearchDataSource
import com.example.musicapp.data.source.remote.api.ApiClient
import retrofit2.Response

class SearchRemoteImpl : SearchDataSource {
    override suspend fun getSearchAllName() = ApiClient.apiService.getSearchAllName()
    override suspend fun getSearchKeyWord(keyword: String) = ApiClient.apiService.getSearch(keyword)
}