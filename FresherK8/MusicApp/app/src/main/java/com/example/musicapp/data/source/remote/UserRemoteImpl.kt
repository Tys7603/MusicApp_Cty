package com.example.musicapp.data.source.remote

import com.example.musicapp.data.model.User
import com.example.musicapp.data.source.UserDataSource
import com.example.musicapp.data.source.remote.api.ApiClient
import retrofit2.Response

class UserRemoteImpl : UserDataSource {
    override suspend fun createUser(userId: String) = ApiClient.apiService.createUser(userId)
}