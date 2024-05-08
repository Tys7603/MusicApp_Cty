package com.example.musicapp.data.source

import com.example.musicapp.data.model.reponse.AlbumRepository
import com.example.musicapp.data.model.reponse.CategoriesRepository
import com.example.musicapp.data.model.reponse.PlaylistRepository
import com.example.musicapp.data.model.reponse.SongAgainRepository
import com.example.musicapp.data.model.reponse.SongRankRepository
import com.example.musicapp.data.model.reponse.TopicRepository
import retrofit2.Response

interface ExploreDataSource {
    suspend fun getListPlaylist(): Response<PlaylistRepository>
    suspend fun getListPlaylistMoodToday(): Response<PlaylistRepository>
    suspend fun getListTopic(): Response<TopicRepository>
    suspend fun getListCategory(): Response<CategoriesRepository>
    suspend fun getListSongAgain(user: String): Response<SongAgainRepository>
    suspend fun getListAlbumLove(): Response<AlbumRepository>
    suspend fun getListAlbumNew(): Response<AlbumRepository>
    suspend fun getListSongRank(): Response<SongRankRepository>
}