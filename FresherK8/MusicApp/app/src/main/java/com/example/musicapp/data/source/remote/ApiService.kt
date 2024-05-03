package com.example.musicapp.data.source.remote

import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_ALBUM_LOVE
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_ALBUM_NEW
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_CATEGORIES
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_PLAYLIST
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_PLAYLIST_MODE_TODAY
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_AGAIN
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_RANK
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_TOPICS
import com.example.musicapp.data.model.repositories.AlbumRepository
import com.example.musicapp.data.model.repositories.CategoriesRepository
import com.example.musicapp.data.model.repositories.PlaylistRepository
import com.example.musicapp.data.model.repositories.SongRepository
import com.example.musicapp.data.model.repositories.SongAgainRepository
import com.example.musicapp.data.model.repositories.SongRankRepository
import com.example.musicapp.data.model.repositories.TopicRepository
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_BY_PLAYLIST_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_BY_TOPIC_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_TOPIC_BY_CATEGORY_ID
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Playlist
    @GET(GET_PLAYLIST)
    fun getListPlaylist(): Call<PlaylistRepository>
    @GET(GET_PLAYLIST_MODE_TODAY)
    fun getListPlaylistMoodToday(): Call<PlaylistRepository>

    // Categories and topic
    @GET(GET_TOPICS)
    fun getListTopic(): Call<TopicRepository>

    @GET(GET_CATEGORIES)
    fun getListCategory(): Call<CategoriesRepository>

    @GET(GET_TOPIC_BY_CATEGORY_ID)
    fun getListTopicByIdCategory(@Path("categoryId") categoryId: Int): Call<TopicRepository>

    //song
    @GET(GET_SONG_AGAIN)
    fun getListSongAgain(@Path("userID") user: Int): Call<SongAgainRepository>

    @GET(GET_SONG)
    fun getListSong(): Response<SongRepository>

    @GET(GET_SONG_RANK)
    fun getListSongRank(): Call<SongRankRepository>

    @GET(GET_SONG_BY_PLAYLIST_ID)
    fun getListSongPlaylistById(@Path("playlistId") playlistId: Int): Call<SongRepository>

    @GET(GET_SONG_BY_TOPIC_ID)
    fun getListSongTopicById(@Path("topicId") topicId: Int): Call<SongRepository>

    // album
    @GET(GET_ALBUM_LOVE)
    fun getListAlbumLove(): Call<AlbumRepository>

    @GET(GET_ALBUM_NEW)
    fun getListAlbumNew(): Call<AlbumRepository>
}