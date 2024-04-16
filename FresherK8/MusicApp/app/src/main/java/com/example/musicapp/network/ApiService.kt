package com.example.musicapp.network

import com.example.musicapp.model.Playlist
import com.example.musicapp.network.ManagerUrl.GET_PLAYLIST
import com.example.musicapp.network.ManagerUrl.GET_TOPICS_CATEGORIES
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryPlaylist
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryTopicAndCategory
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(GET_PLAYLIST)
    fun getListPlaylist() : Call<RepositoryPlaylist>

    @GET(GET_TOPICS_CATEGORIES)
    fun getListTopicAndCategory() : Call<RepositoryTopicAndCategory>
}