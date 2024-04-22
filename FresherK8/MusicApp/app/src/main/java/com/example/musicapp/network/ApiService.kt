package com.example.musicapp.network

import com.example.musicapp.network.ManagerUrl.GET_ALBUM_LOVE
import com.example.musicapp.network.ManagerUrl.GET_ALBUM_NEW
import com.example.musicapp.network.ManagerUrl.GET_CATEGORIES
import com.example.musicapp.network.ManagerUrl.GET_PLAYLIST
import com.example.musicapp.network.ManagerUrl.GET_PLAYLIST_MODE_TODAY
import com.example.musicapp.network.ManagerUrl.GET_SONG
import com.example.musicapp.network.ManagerUrl.GET_SONG_AGAIN
import com.example.musicapp.network.ManagerUrl.GET_SONG_RANK
import com.example.musicapp.network.ManagerUrl.GET_TOPICS
import com.example.musicapp.data.repositories.AlbumLoveRepository
import com.example.musicapp.data.repositories.AlbumNewRepository
import com.example.musicapp.data.repositories.CategoriesRepository
import com.example.musicapp.data.repositories.PlaylistRepository
import com.example.musicapp.data.repositories.SongRepository
import com.example.musicapp.data.repositories.SongAgainRepository
import com.example.musicapp.data.repositories.SongRankRepository
import com.example.musicapp.data.repositories.TopicRepository
import com.example.musicapp.network.ManagerUrl.GET_SONG_BY_PLAYLIST_ID
import retrofit2.Call
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

    //song
    @GET(GET_SONG_AGAIN)
    fun getListSongAgain(@Path("userID") user: Int): Call<SongAgainRepository>

    @GET(GET_ALBUM_LOVE)
    fun getListAlbumLove(): Call<AlbumLoveRepository>

    @GET(GET_ALBUM_NEW)
    fun getListAlbumNew(): Call<AlbumNewRepository>

    @GET(GET_SONG)
    fun getListSong(): Call<SongRepository>

    @GET(GET_SONG_RANK)
    fun getListSongRank(): Call<SongRankRepository>

    @GET(GET_SONG_BY_PLAYLIST_ID)
    fun getListSongPlaylistById(@Path("playlistId") playlistId: Int): Call<SongRepository>
}