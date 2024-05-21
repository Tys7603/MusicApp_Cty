package com.example.musicapp.screen.songUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.repositories.exploreReposotory.ExploreRepository
import com.example.musicapp.data.repositories.musicRepository.MusicRepository
import com.example.musicapp.data.repositories.musicRepository.MusicRepositoryImpl
import com.example.musicapp.shared.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SongUserViewModel(private val musicRepository: MusicRepository, private val exploreRepository: ExploreRepository) : BaseViewModel() {

    private val _songs = MutableLiveData<ArrayList<Song>>()
    val songs: LiveData<ArrayList<Song>> = _songs

    private val _songsAgain = MutableLiveData<ArrayList<SongAgain>>()
    val songsAgain: LiveData<ArrayList<SongAgain>> = _songsAgain

    fun fetchSongLocal() {
       viewModelScope.launch {
          val songs = musicRepository.getListSongLocal()
           _songs.postValue(songs.value)
       }
    }

    fun fetchSongAgain (userId : String){
        launchTaskSync(
            onRequest = {exploreRepository.getListListenAgain(userId)},
            onSuccess = {_songsAgain.value = it},
            onFailure = { Log.d("fetchSongAgain", "fetchSongAgain: $it")},
            onError = {exception.value = it  }
        )
    }
}