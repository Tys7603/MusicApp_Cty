package com.example.musicapp.data.source.remote

import com.example.musicapp.data.source.MusicDataSource
import com.example.musicapp.data.source.remote.api.ApiClient


class MusicRemoteImpl : MusicDataSource.Remote{

    override suspend fun getListSong() = ApiClient.apiService.getListSong()

    override suspend fun getListSongLove(userId: String) = ApiClient.apiService.getListSongLove(userId)

    override suspend fun createSongLove(userId: String, songId: Int) = ApiClient.apiService.createSongLove(userId, songId)

    override suspend fun deleteSongLove(songLoveId: Int) = ApiClient.apiService.deleteSongLove(songLoveId)

    override suspend fun getListSongAgain(user: String) = ApiClient.apiService.getListSongAgain(user)

}