package com.example.musicapp.screen.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.repositories.followRepository.FollowRepository
import com.example.musicapp.data.repositories.musicRepository.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UserViewModel (private val musicRepository: MusicRepository, private val followRepository: FollowRepository) : BaseViewModel() {

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

    private val _isQuantity = MutableLiveData<Int>()
    val isQuantity: LiveData<Int> = _isQuantity

    init {
       fetchData()
    }

    fun fetchData(){
        initValueUser()
        fetchSongLove()
        fetchSongLocal()
        fetchSongAgain()
        fetchQuantityFollow()
    }

    fun initValueUser(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            _userName.value = user.email
            _image.value = user.photoUrl.toString()
            _isLogin.value = true
        }else{
            _userName.value = "Bạn chưa đăng nhập"
            _image.value = Constant.URL_IMAGE
            _isLogin.value = false
        }
    }

    private fun fetchQuantityFollow() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null ) {
            launchTaskSync(
                onRequest = { followRepository.getQuantityFollowTheArtist(user.uid) },
                onSuccess = { _isQuantity.value = it },
                onFailure = { Log.e("MusicViewModel", "Failed: $it") },
                onError = { exception.value = it }
            )
        }else{
            _isQuantity.value = 0
        }
    }


    fun fetchSongLove() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            fetchDataWithSync(
                onRequest = { musicRepository.getListSongLove(user.uid) },
                onSuccess = { _songsLove.value = it }
            )
        }
    }

    fun fetchSongLocal() {
        viewModelScope.launch {
            val songs = musicRepository.getListSongLocal()
            _songsLocal.postValue(songs.value)
        }
    }

    fun fetchSongAgain() {
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