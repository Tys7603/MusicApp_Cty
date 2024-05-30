package com.example.musicapp.data.repositories.topicRepository

import com.example.musicapp.data.model.Topic
import com.example.musicapp.shared.utils.scheduler.DataResult

interface TopicRepository {
    suspend fun getListTopicByIdCategory(id: Int) : DataResult<ArrayList<Topic>>
}