package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.User
import com.example.musicapp.data.source.remote.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

class UserRepository {
    suspend fun createUser(userId : String) : DataResult<Boolean>{
        return try {
            val response = ApiClient.apiService.createUser(userId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(true)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}