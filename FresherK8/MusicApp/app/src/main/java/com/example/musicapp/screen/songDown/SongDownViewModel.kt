package com.example.musicapp.screen.songDown

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repositories.MusicRepositoryImpl
import com.example.musicapp.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class SongDownViewModel(private val musicRepository: MusicRepositoryImpl) : BaseViewModel() {

    private val _songsLocal = MutableLiveData<ArrayList<Song>>()
    val songsLocal: LiveData<ArrayList<Song>> = _songsLocal

    init {
        fetchSongLocal()
    }

    private fun fetchSongLocal() {
       viewModelScope.launch {
          val songs = musicRepository.getListSongLocal()
           _songsLocal.postValue(songs.value)
       }
    }
}