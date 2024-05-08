package com.example.musicapp.data.repositories.topicRepository

import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.source.TopicDataSource
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

class TopicRepositoryImpl (private val dataSource: TopicDataSource) : TopicRepository {

    override suspend fun getListTopicByIdCategory(id: Int) : DataResult<ArrayList<Topic>> {
        return try {
            val response = dataSource.getListTopicByIdCategory(id)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.topics)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

}