package com.example.musicapp.screen.songDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repositories.musicRepository.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class SongDetailViewModel(private val musicRepository: MusicRepository) : BaseViewModel() {
    private val _songTopic = MutableLiveData<ArrayList<Song>>()
    val songTopic: LiveData<ArrayList<Song>> = _songTopic

    private val _songPlaylist = MutableLiveData<ArrayList<Song>>()
    val songPlaylist: LiveData<ArrayList<Song>> = _songPlaylist

    private val _songAlbum = MutableLiveData<ArrayList<Song>>()
    val songAlbum: LiveData<ArrayList<Song>> = _songAlbum

    private val _isInsertPlaylist = MutableLiveData<Boolean>()
    val isInsertPlaylist: LiveData<Boolean> = _isInsertPlaylist

    private val _songsLove = MutableLiveData<ArrayList<Song>>()
    val songsLove: LiveData<ArrayList<Song>> = _songsLove

    private val _isUserLogin = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean> = _isUserLogin

    fun fetchSongPlaylist(id : Int) {
        launchTaskSync(
            onRequest = { musicRepository.getListSongPlaylist(id) },
            onSuccess = { _songPlaylist.value = it },
            onFailure = { Log.e("fetchSong", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun fetchSongTopic(id : Int) {
        launchTaskSync(
            onRequest = { musicRepository.getListSongTopic(id) },
            onSuccess = { _songTopic.value = it },
            onFailure = { Log.e("fetchSong", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun fetchSongAlbum(id : Int) {
        launchTaskSync(
            onRequest = { musicRepository.getListSongAlbum(id) },
            onSuccess = { _songAlbum.value = it },
            onFailure = { Log.e("fetchSong", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun fetchSongLove(userId : String) {
            launchTaskSync(
                onRequest = { musicRepository.getListSongLove(userId) },
                onSuccess = { _songsLove.value = it },
                onFailure = { Log.e("fetchSong", "Failed: $it") },
                onError = { exception.value = it }
            )
    }

    fun insertPlaylist(playlistId : Int){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            _isUserLogin.value = true
            launchTaskSync(
                onRequest = { musicRepository.insertPlaylistIntoPlaylistLove(user.uid, playlistId) },
                onSuccess = { _isInsertPlaylist.value = it },
                onFailure = { Log.e("insertPlaylist", "Failed: $it") },
                onError = { exception.value = it }
            )
        }else{
            _isUserLogin.value = false
        }
    }
}