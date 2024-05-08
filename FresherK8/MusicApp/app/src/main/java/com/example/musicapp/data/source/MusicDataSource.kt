package com.example.musicapp.data.source

import com.example.musicapp.data.repositories.TopicRepositoryImpl
import retrofit2.Response

interface MusicDataSource {
    suspend fun getListTopicByIdCategory(id: Int) : Response<TopicRepositoryImpl>
}