package com.example.musicapp.screen.music

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repositories.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel

class MusicViewModel(private val musicRepository: MusicRepository) : BaseViewModel() {
    private val _songs = MutableLiveData<ArrayList<Song>>()
    val songs : LiveData<ArrayList<Song>> = _songs

    init {
        fetchSong()
    }

    private fun fetchSong(){
        launchTaskSync(
            onRequest = {musicRepository.getListSong()},
            onSuccess = {_songs.value = it},
            onFailure = {message ->  Log.e("FetchSong", "Failed: $message")},
            onError ={exception.value = it  }

        )
    }
}