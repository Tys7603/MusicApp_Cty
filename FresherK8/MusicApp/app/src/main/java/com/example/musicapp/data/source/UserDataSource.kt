package com.example.musicapp.data.source

import com.example.musicapp.data.model.Status
import com.example.musicapp.data.model.User
import com.example.musicapp.data.model.reponse.PlaylistUserRepository
import retrofit2.Response

interface UserDataSource {
    suspend fun createUser(userId: String): Response<User>
    suspend fun getListPlaylistUser(userId: String): Response<PlaylistUserRepository>
    suspend fun createPlaylistUser(userId: String, namePlaylist: String): Response<Status>
    suspend fun deletePlaylistUser(playlistUserId: String): Response<Status>
}