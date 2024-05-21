package com.example.musicapp.screen.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.data.repositories.userRepository.UserRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class PlaylistUserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private val _playlistsUser = MutableLiveData<ArrayList<PlaylistUser>>()
    val playlistUser: LiveData<ArrayList<PlaylistUser>> = _playlistsUser

    private val _isCreatePlaylist = MutableLiveData<Boolean>()
    val isCreatePlaylist: LiveData<Boolean> = _isCreatePlaylist

    private val _isInsertSongPlaylist = MutableLiveData<Boolean>()
    val isInsertSongPlaylist: LiveData<Boolean> = _isInsertSongPlaylist

    var playlistName = ""

    fun fetchPlaylistsUser(userId : String) {
        launchTaskSync(
            onRequest = { userRepository.getListPlaylistUser(userId) },
            onSuccess = { _playlistsUser.value = it },
            onFailure = { Log.e("fetchMusicVideo", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun createPlaylistUser() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            launchTaskSync(
                onRequest = { userRepository.createPlaylistUser(user.uid, playlistName) },
                onSuccess = { _isCreatePlaylist.value = it },
                onFailure = { Log.e("createPlaylistUser", "Failed: $it") },
                onError = { exception.value = it }
            )
        }
    }

    fun insertSongPlaylistUser(
        playlistUserId: Int,
        songId: Int
    ) {
        launchTaskSync(
            onRequest = { userRepository.insertSongPlaylistUser(playlistUserId, songId) },
            onSuccess = { _isInsertSongPlaylist.value = it },
            onFailure = { Log.e("createPlaylistUser", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun deletePlaylistUser(playlistsUserId: String) {
        launchTaskSync(
            onRequest = { userRepository.deletePlaylistUser(playlistsUserId) },
            onSuccess = { fetchPlaylistsUser(FirebaseAuth.getInstance().currentUser!!.uid) },
            onFailure = { Log.e("TAG", "onFailure: $it") },
            onError = { Log.e("TAG", "onError: $it") }
        )
    }
}