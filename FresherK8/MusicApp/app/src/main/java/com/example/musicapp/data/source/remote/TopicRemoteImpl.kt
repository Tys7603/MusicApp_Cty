package com.example.musicapp.data.source.remote

import com.example.musicapp.data.source.TopicDataSource
import com.example.musicapp.data.source.remote.api.ApiClient

class TopicRemoteImpl : TopicDataSource {
    override suspend fun getListTopicByIdCategory(id: Int) = ApiClient.apiService.getListTopicByIdCategory(id)
}