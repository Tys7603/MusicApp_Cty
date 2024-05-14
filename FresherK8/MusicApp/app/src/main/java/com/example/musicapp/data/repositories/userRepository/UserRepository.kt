package com.example.musicapp.data.repositories.userRepository

import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.data.model.reponse.PlaylistUserRepository
import com.example.musicapp.data.source.remote.api.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult
import retrofit2.Response

interface UserRepository {
    suspend fun createUser(userId: String): DataResult<Boolean>
    suspend fun getListPlaylistUser(userId: String): DataResult<ArrayList<PlaylistUser>>
    suspend fun createPlaylistUser(userId: String, namePlaylist: String): DataResult<Boolean>
    suspend fun insertSongPlaylistUser(playlistUserId: Int, songId: Int): DataResult<Boolean>
    suspend fun deletePlaylistUser(playlistUserId: String): DataResult<Boolean>
}