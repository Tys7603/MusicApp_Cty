package com.example.musicapp.screen.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.repositories.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.example.musicapp.shared.utils.scheduler.DataResult
import com.google.firebase.auth.FirebaseAuth

class UserViewModel (private val musicRepository: MusicRepository) : BaseViewModel() {

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin : LiveData<Boolean> = _isLogin

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _image = MutableLiveData<String>()
    val image : LiveData<String> = _image

    private val _songsLove = MutableLiveData<ArrayList<Song>>()
    val songsLove: LiveData<ArrayList<Song>> = _songsLove

    private val _songsLocal = MutableLiveData<ArrayList<Song>>()
    val songsLocal: LiveData<ArrayList<Song>> = _songsLocal

    private val _songsAgain = MutableLiveData<ArrayList<SongAgain>>()
    val songsAgain: LiveData<ArrayList<SongAgain>> = _songsAgain

    init {
        initValueUser()
        fetchSongLove()
        fetchSongLocal()
        fetchSongAgain()
    }

    private fun initValueUser(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            _userName.value = user.email
            _image.value = user.photoUrl.toString()
            _isLogin.value = true
            Log.d("TAG", "initValueUser: " + user.photoUrl)
        }
    }

    private fun fetchSongLove() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            fetchDataWithSync(
                onRequest = { musicRepository.getListSongLove(user.uid) },
                onSuccess = { _songsLove.value = it }
            )
        }
    }

    private fun fetchSongLocal() {
            launchTaskSync(
                onRequest = { musicRepository.getListSongLocal() },
                onSuccess = { _songsLocal.value = it },
                onError = { exception.value = it }
            )
    }

    private fun fetchSongAgain() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            fetchDataWithSync(
                onRequest = { musicRepository.getListListenAgain(user.uid) },
                onSuccess = { _songsAgain.value = it }
            )
        }
    }

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