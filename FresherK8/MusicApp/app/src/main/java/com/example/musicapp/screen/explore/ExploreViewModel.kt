package com.example.musicapp.screen.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.repositories.ExploreRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult
import com.google.firebase.auth.FirebaseAuth

class ExploreViewModel(private val exploreRepository: ExploreRepository) : BaseViewModel() {
    private val user = FirebaseAuth.getInstance().currentUser

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

    private val _isMessageSelect = MutableLiveData<Boolean>()
    var isMessageSelect: LiveData<Boolean> = _isMessageSelect

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
        fetchPlaylistMood()
        checkUserLogin()
    }

    private fun checkUserLogin() {
        _isUserLogin.value = user != null

        _isMessageSelect.value = false
        if (_isUserLogin.value == true) {
            fetchSongAgain()
        }
    }

    private fun fetchPlaylist() = fetchDataWithSync(
        { exploreRepository.getListPlaylist() },
        { _playlists.value = it }
    )

    private fun fetchPlaylistMood() = fetchDataWithSync(
        { exploreRepository.getListPlaylistMoodToday() },
        { _playlistsMood.value = it }
    )

    private fun fetchTopics() = fetchDataWithSync(
        { exploreRepository.getListTopic() },
        { _topics.value = it }
    )

    private fun fetchCategories() = fetchDataWithSync(
        { exploreRepository.getListCategory() },
        { _categories.value = it }
    )


    private fun fetchSongAgain() = fetchDataWithSync(
        { exploreRepository.getListListenAgain(user!!.uid) },
        { songs ->
            _songAgain.value = songs
            if (songs.size == 0) {
                _message.value = Constant.EMPTY_SONG_MESSAGE
                _isMessageSelect.value = true
            }
        }
    )


    private fun fetchAlbumLove() = fetchDataWithSync(
        { exploreRepository.getListAlbumLove() },
        { _albumLove.value = it }
    )

    private fun fetchAlbumNew() = fetchDataWithSync(
        { exploreRepository.getListAlbumNew() },
        { _albumNew.value = it }
    )

    private fun fetchSongRank() = fetchDataWithSync(
        { exploreRepository.getListSongRank() },
        { _songRank.value = it }
    )

    private inline fun <T> fetchDataWithSync(
        crossinline onRequest: suspend () -> DataResult<T>,
        crossinline onSuccess: (T) -> Unit
    ) {
        launchTaskSync(
            onRequest = { onRequest() },
            onSuccess = { onSuccess(it) },
            onFailure = { message -> Log.e("FetchSong", "Failed: $message") },
            onError = { exception.value = it }
        )
    }
}