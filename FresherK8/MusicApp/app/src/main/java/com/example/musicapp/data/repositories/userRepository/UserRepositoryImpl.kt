package com.example.musicapp.data.repositories.userRepository

import com.example.musicapp.data.source.UserDataSource
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

class UserRepositoryImpl (private val dataSource: UserDataSource) : UserRepository {

    override suspend fun createUser(userId : String) : DataResult<Boolean>{
        return try {
            val response = dataSource.createUser(userId)
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