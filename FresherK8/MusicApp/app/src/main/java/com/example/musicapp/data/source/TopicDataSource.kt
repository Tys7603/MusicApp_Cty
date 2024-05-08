package com.example.musicapp.data.source

import com.example.musicapp.data.model.reponse.TopicRepository
import retrofit2.Response

interface TopicDataSource {
    suspend fun getListTopicByIdCategory(id: Int) : Response<TopicRepository>
}