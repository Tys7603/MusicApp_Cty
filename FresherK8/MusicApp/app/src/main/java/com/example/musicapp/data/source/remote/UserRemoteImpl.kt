package com.example.musicapp.data.source.remote

import com.example.musicapp.data.model.Status
import com.example.musicapp.data.model.reponse.PlaylistRepository
import com.example.musicapp.data.source.UserDataSource
import com.example.musicapp.data.source.remote.api.ApiClient
import retrofit2.Response

class UserRemoteImpl : UserDataSource {
    override suspend fun createUser(userId: String) = ApiClient.apiService.createUser(userId)

    override suspend fun getListPlaylistUser(userId: String) =
        ApiClient.apiService.getListPlaylistUser(userId)

    override suspend fun getListPlaylistLove(userId: String): Response<PlaylistRepository> =
        ApiClient.apiService.getListPlaylistLove(userId)

    override suspend fun createPlaylistUser(
        userId: String,
        namePlaylist: String
    ) = ApiClient.apiService.createPlaylistUser(userId, namePlaylist)

    override suspend fun insertSongPlaylistUser(
        playlistUserId: Int,
        songId: Int
    ) = ApiClient.apiService.insertSongPlaylistUser(playlistUserId, songId)

    override suspend fun deletePlaylistUser(playlistUserId: String) =
        ApiClient.apiService.deletePlaylistUser(playlistUserId)

    override suspend fun deletePlaylistLove(playlistLoveId: String) =
        ApiClient.apiService.deletePlaylistLove(playlistLoveId)
}