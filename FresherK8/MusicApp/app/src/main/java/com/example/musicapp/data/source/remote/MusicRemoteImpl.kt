package com.example.musicapp.data.source.remote

import com.example.musicapp.data.model.reponse.LyricRepository
import com.example.musicapp.data.model.reponse.SongRepository
import com.example.musicapp.data.source.MusicDataSource
import com.example.musicapp.data.source.remote.api.ApiClient
import retrofit2.Response


class MusicRemoteImpl : MusicDataSource.Remote {

    override suspend fun getListSong() = ApiClient.apiService.getListSong()

    override suspend fun getListSongLove(userId: String) =
        ApiClient.apiService.getListSongLove(userId)

    override suspend fun createSongLove(userId: String, songId: Int) =
        ApiClient.apiService.createSongLove(userId, songId)

    override suspend fun createSongAgain(userId: String, songId: Int) =
        ApiClient.apiService.createSongAgain(userId, songId)

    override suspend fun insertPlaylistIntoPlaylistLove(userId: String, playlistId: Int) =
        ApiClient.apiService.insertPlaylistIntoPlaylistLove(userId, playlistId)

    override suspend fun deleteSongLove(songLoveId: Int) =
        ApiClient.apiService.deleteSongLove(songLoveId)

    override suspend fun getListSongAgain(user: String) =
        ApiClient.apiService.getListSongAgain(user)

    override suspend fun getListSongTopic(id: Int) = ApiClient.apiService.getListSongTopicById(id)

    override suspend fun getListSongPlaylist(id: Int) =
        ApiClient.apiService.getListSongPlaylistById(id)

    override suspend fun getListSongAlbum(id: Int) = ApiClient.apiService.getListSongAlbumById(id)

    override suspend fun getListSongPlaylistUser(playlistUserId: Int) =
        ApiClient.apiService.getListSongPlaylistUserId(playlistUserId)

    override suspend fun getLyricsBySongId(songId: Int) =
        ApiClient.apiService.getLyricsBySongId(songId)

}