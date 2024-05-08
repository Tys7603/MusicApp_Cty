package com.example.musicapp.data.repositories.exploreReposotory


import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.source.remote.api.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult


interface ExploreRepository {

    suspend fun getListPlaylist(): DataResult<ArrayList<Playlist>>

    suspend fun getListPlaylistMoodToday(): DataResult<ArrayList<Playlist>>

    suspend fun getListTopic(): DataResult<ArrayList<Topic>>

    suspend fun getListCategory(): DataResult<ArrayList<Category>>

    suspend fun getListListenAgain(userID: String): DataResult<ArrayList<SongAgain>>

    suspend fun getListAlbumLove(): DataResult<ArrayList<Album>>

    suspend fun getListAlbumNew(): DataResult<ArrayList<Album>>

    suspend fun getListSongRank(): DataResult<ArrayList<SongRank>>
}