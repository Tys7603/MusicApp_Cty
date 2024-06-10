package com.example.musicapp.screen.music

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repositories.followRepository.FollowRepository
import com.example.musicapp.data.repositories.musicRepository.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class MusicViewModel(private val musicRepository: MusicRepository) : BaseViewModel() {
    private val _songs = MutableLiveData<ArrayList<Song>>()
    val songs: LiveData<ArrayList<Song>> = _songs

    private val _songsLove = MutableLiveData<ArrayList<Song>>()
    val songsLove: LiveData<ArrayList<Song>> = _songsLove

    private val _isAddSongLove = MutableLiveData<Boolean>()
    val isAddSongLove: LiveData<Boolean> = _isAddSongLove

    private val _isQuantity = MutableLiveData<Int>()
    val isQuantity: LiveData<Int> = _isQuantity

    init {
        fetchData()
    }

    fun fetchData() {
        fetchSong()
        fetchSongLove()
    }

    private fun fetchSong() {
        launchTaskSync(
            onRequest = { musicRepository.getListSong() },
            onSuccess = { _songs.value = it },
            onFailure = { Log.e("FetchSong", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    private fun fetchSongLove() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            launchTaskSync(
                onRequest = { musicRepository.getListSongLove(user.uid) },
                onSuccess = { _songsLove.value = it },
                onFailure = { Log.e("FetchSong", "fetchSongLove: $it") },
                onError = { exception.value = it }
            )
        }
    }

    fun addSongLove(userId: String, songId: Int) {
        launchTaskSync(
            onRequest = { musicRepository.createSongLove(userId, songId) },
            onSuccess = {
                _isAddSongLove.value = true
                fetchSongLove()
            },
            onFailure = { Log.e("FetchSong", "fetchSongLove: $it") },
            onError = { exception.value = it }
        )

    }

    fun addSongAgain(userId: String, songId: Int) {
        launchTaskSync(
            onRequest = { musicRepository.createSongAgain(userId, songId) },
            onSuccess = { Log.d("TAG", "addSongAgain: oke")},
            onFailure = { Log.e("FetchSong", "fetchSongLove: $it") },
            onError = { exception.value = it }
        )

    }

    fun deleteSongLove(songId: Int) {
        launchTaskSync(
            onRequest = { musicRepository.deleteSongLove(songId) },
            onSuccess = {
                _isAddSongLove.value = false
                fetchSongLove()
            },
            onFailure = { Log.e("FetchSong", "fetchSongLove: $it") },
            onError = { exception.value = it }
        )
    }
}