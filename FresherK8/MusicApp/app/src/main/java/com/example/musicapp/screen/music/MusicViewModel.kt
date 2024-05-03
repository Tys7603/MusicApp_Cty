package com.example.musicapp.screen.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repositories.MusicRepository

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {
    private val _songs = MutableLiveData<ArrayList<Song>>()
    val songs : LiveData<ArrayList<Song>> = _songs

    init {
        fetchSong()
    }

    private fun fetchSong(){
        val songsApi = musicRepository.getListSong()
        _songs.value = songsApi
    }
}