package com.example.musicapp.data.source.remote

import com.example.musicapp.data.model.reponse.CategoriesRepository
import com.example.musicapp.data.model.reponse.MusicVideoRepository
import com.example.musicapp.data.source.MusicVideoDataSource
import com.example.musicapp.data.source.remote.api.ApiClient
import retrofit2.Response

class MusicVideoRemoteImpl : MusicVideoDataSource {
    override suspend fun getListMusicVideo() = ApiClient.apiService.getListMusicVideo()

    override suspend fun getListCategory() = ApiClient.apiService.getListCategory()
}