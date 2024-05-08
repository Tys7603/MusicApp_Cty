package com.example.musicapp.screen.songDown

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repositories.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel

class SongDownViewModel(private val musicRepository: MusicRepository) : BaseViewModel() {

    private val _songsLocal = MutableLiveData<ArrayList<Song>>()
    val songsLocal: LiveData<ArrayList<Song>> = _songsLocal

    init {
        fetchSongLocal()
    }

    private fun fetchSongLocal() {
        launchTaskSync(
            onRequest = { musicRepository.getListSongLocal() },
            onSuccess = { _songsLocal.value = it },
            onError = { exception.value = it }
        )
    }
}