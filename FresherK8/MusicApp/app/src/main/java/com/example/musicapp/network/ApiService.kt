package com.example.musicapp.network

import com.example.musicapp.network.ManagerUrl.GET_ALBUM_LOVE
import com.example.musicapp.network.ManagerUrl.GET_ALBUM_NEW
import com.example.musicapp.network.ManagerUrl.GET_CATEGORIES
import com.example.musicapp.network.ManagerUrl.GET_PLAYLIST
import com.example.musicapp.network.ManagerUrl.GET_PLAYLIST_MODE_TODAY
import com.example.musicapp.network.ManagerUrl.GET_SONG
import com.example.musicapp.network.ManagerUrl.GET_SONG_AGAIN
import com.example.musicapp.network.ManagerUrl.GET_TOPICS
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryAlbumLove
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryAlbumNew
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryCategories
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryPlaylist
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositorySong
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositorySongAgain
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryTopic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // Playlist
    @GET(GET_PLAYLIST)
    fun getListPlaylist(): Call<RepositoryPlaylist>
    @GET(GET_PLAYLIST_MODE_TODAY)
    fun getListPlaylistMoodToday(): Call<RepositoryPlaylist>

    // Categories and topic
    @GET(GET_TOPICS)
    fun getListTopic(): Call<RepositoryTopic>

    @GET(GET_CATEGORIES)
    fun getListCategory(): Call<RepositoryCategories>

    //song again
    @GET(GET_SONG_AGAIN)
    fun getListSongAgain(@Path("userID") user: Int): Call<RepositorySongAgain>

    //song love
    @GET(GET_ALBUM_LOVE)
    fun getListAlbumLove(): Call<RepositoryAlbumLove>

    //song new
    @GET(GET_ALBUM_NEW)
    fun getListAlbumNew(): Call<RepositoryAlbumNew>

    //song
    @GET(GET_SONG)
    fun getListSong(): Call<RepositorySong>
}