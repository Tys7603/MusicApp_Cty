package com.example.musicapp.data.source

import com.example.musicapp.data.model.reponse.CategoriesRepository
import com.example.musicapp.data.model.reponse.MusicVideoRepository
import retrofit2.Response

interface MusicVideoDataSource {
    suspend fun getListMusicVideo(): Response<MusicVideoRepository>
    suspend fun getListCategory(): Response<CategoriesRepository>
}