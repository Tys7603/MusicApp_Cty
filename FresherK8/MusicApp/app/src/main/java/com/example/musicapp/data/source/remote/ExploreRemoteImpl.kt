package com.example.musicapp.data.source.remote

import com.example.musicapp.data.source.ExploreDataSource
import com.example.musicapp.data.source.remote.api.ApiClient

class ExploreRemoteImpl : ExploreDataSource{
    override suspend fun getListPlaylist() = ApiClient.apiService.getListPlaylist()

    override suspend fun getListPlaylistMoodToday() = ApiClient.apiService.getListPlaylistMoodToday()

    override suspend fun getListTopic() = ApiClient.apiService.getListTopic()

    override suspend fun getListCategory() = ApiClient.apiService.getListCategory()

    override suspend fun getListSongAgain(user: String) = ApiClient.apiService.getListSongAgain(user)

    override suspend fun getListAlbumLove() = ApiClient.apiService.getListAlbumLove()

    override suspend fun getListAlbumNew() = ApiClient.apiService.getListAlbumNew()

    override suspend fun getListSongRank() = ApiClient.apiService.getListSongRank()
}