package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.source.remote.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

class TopicRepository {

    suspend fun getListTopicByIdCategory(id: Int) : DataResult<ArrayList<Topic>> {
        return try {
            val response = ApiClient.apiService.getListTopicByIdCategory(id)
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