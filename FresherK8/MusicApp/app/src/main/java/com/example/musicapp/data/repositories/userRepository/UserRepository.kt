package com.example.musicapp.data.repositories.userRepository

import com.example.musicapp.data.source.remote.api.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

interface UserRepository {
    suspend fun createUser(userId : String) : DataResult<Boolean>
}