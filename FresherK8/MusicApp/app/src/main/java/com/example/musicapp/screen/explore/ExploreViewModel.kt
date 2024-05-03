package com.example.musicapp.screen.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.repositories.ExploreRepository
import com.example.musicapp.shared.utils.constant.Constant
import kotlinx.coroutines.launch

class ExploreViewModel(private val exploreRepository: ExploreRepository) : ViewModel() {

    private val _playlists = MutableLiveData<ArrayList<Playlist>>()
    val playlist: LiveData<ArrayList<Playlist>> = _playlists

    private val _playlistsMood = MutableLiveData<ArrayList<Playlist>>()
    val playlistsMood: LiveData<ArrayList<Playlist>> = _playlistsMood

    private val _topics = MutableLiveData<ArrayList<Topic>>()
    val topics: LiveData<ArrayList<Topic>> = _topics

    private val _categories = MutableLiveData<ArrayList<Category>>()
    val categories: LiveData<ArrayList<Category>> = _categories

    private val _songAgain = MutableLiveData<ArrayList<SongAgain>>()
    val songAgain: LiveData<ArrayList<SongAgain>> = _songAgain

    private val _albumLove = MutableLiveData<ArrayList<Album>>()
    val albumLove: LiveData<ArrayList<Album>> = _albumLove

    private val _albumNew = MutableLiveData<ArrayList<Album>>()
    val albumNew: LiveData<ArrayList<Album>> = _albumNew

    private val _songRank = MutableLiveData<ArrayList<SongRank>>()
    val songRank: LiveData<ArrayList<SongRank>> = _songRank

    private val _isUserLogin = MutableLiveData<Boolean>()
    var isUserLogin: LiveData<Boolean> = _isUserLogin

    private val _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchAlbumNew()
        fetchCategories()
        fetchAlbumLove()
        fetchPlaylist()
        fetchTopics()
        fetchSongRank()
        fetchPlaylist()
        fetchPlaylistMood()
        checkUserLogin()
    }

    private fun checkUserLogin() {
        _isUserLogin.value = true
        if (_isUserLogin.value == true) {
            fetchSongAgain()
        }
    }

    private fun fetchPlaylist() {
        viewModelScope.launch {
            val playlistApi = exploreRepository.getListPlaylist()
            _playlists.value = playlistApi
        }
    }

    private fun fetchPlaylistMood() {
        viewModelScope.launch {
            val playlistApi = exploreRepository.getListPlaylistMoodToday()
            _playlistsMood.value = playlistApi
        }
    }

    private fun fetchTopics() {
        viewModelScope.launch {
            val topicApi = exploreRepository.getListTopic()
            _topics.value = topicApi
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            val categoriesApi = exploreRepository.getListCategory()
            _categories.value = categoriesApi
        }
    }

    private fun fetchSongAgain() {
        viewModelScope.launch {

            val songAgainstApi = exploreRepository.getListListenAgain(1)

            if (songAgainstApi.size > 0) {
                _message.value = Constant.EMPTY_SONG_MESSAGE
            }
            _songAgain.value = songAgainstApi
        }
    }

    private fun fetchAlbumLove() {
        viewModelScope.launch {
            val albumLove = exploreRepository.getListAlbumLove()
            _albumLove.value = albumLove
        }
    }

    private fun fetchAlbumNew() {
        viewModelScope.launch {
            val albumNew = exploreRepository.getListAlbumNew()
            _albumNew.value = albumNew
        }
    }

    private fun fetchSongRank() {
        viewModelScope.launch {
            val songRank = exploreRepository.getListSongRank()
            _songRank.value = songRank
        }
    }
}