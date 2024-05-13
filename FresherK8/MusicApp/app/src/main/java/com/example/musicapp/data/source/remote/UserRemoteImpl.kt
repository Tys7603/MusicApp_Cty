package com.example.musicapp.data.source.remote

import com.example.musicapp.data.source.UserDataSource
import com.example.musicapp.data.source.remote.api.ApiClient

class UserRemoteImpl : UserDataSource {
    override suspend fun createUser(userId: String) = ApiClient.apiService.createUser(userId)

    override suspend fun getListPlaylistUser(userId: String) =
        ApiClient.apiService.getListPlaylistUser(userId)

    override suspend fun createPlaylistUser(
        userId: String,
        namePlaylist: String
    ) = ApiClient.apiService.createPlaylistUser(userId, namePlaylist)
}