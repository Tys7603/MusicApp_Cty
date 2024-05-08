package com.example.musicapp.data.source

import com.example.musicapp.data.model.User
import retrofit2.Response

interface UserDataSource {
    suspend fun createUser(userId: String): Response<User>
}