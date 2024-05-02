package com.example.musicapp.presentation.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.repositories.ExploreRepository
import kotlinx.coroutines.launch

class ExploreViewModel(private val exploreRepository: ExploreRepository) : ViewModel() {
    private val _playlists = MutableLiveData<ArrayList<Playlist>>()
    val playlist : LiveData<ArrayList<Playlist>> = _playlists

    init {
        fetchPlaylist()
    }

    private fun fetchPlaylist () {
        viewModelScope.launch {
            val playlistApi = exploreRepository.getListPlaylist()
            _playlists.value = playlistApi
        }
    }
}