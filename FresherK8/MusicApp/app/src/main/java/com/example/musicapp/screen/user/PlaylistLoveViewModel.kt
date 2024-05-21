package com.example.musicapp.screen.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.repositories.userRepository.UserRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class PlaylistLoveViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private val _playlists = MutableLiveData<ArrayList<Playlist>>()
    val playlists: LiveData<ArrayList<Playlist>> = _playlists

    init {
        fetchPlaylists()
    }

    fun fetchPlaylists() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            launchTaskSync(
                onRequest = { userRepository.getListPlaylistLove(user.uid) },
                onSuccess = { _playlists.value = it },
                onFailure = { Log.e("fetchPlaylists", "Failed: $it") },
                onError = { exception.value = it }
            )
        }
    }

    fun deletePlaylistLove(playlistsLoveId: String) {
        launchTaskSync(
            onRequest = { userRepository.deletePlaylistLove(playlistsLoveId) },
            onSuccess = { fetchPlaylists() },
            onFailure = { Log.e("TAG", "onFailure: $it") },
            onError = { Log.e("TAG", "onError: $it") }
        )
    }
}