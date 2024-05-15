package com.example.musicapp.screen.lyrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Lyric
import com.example.musicapp.data.repositories.musicRepository.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel

class LyricViewModel(private val musicRepository: MusicRepository) : BaseViewModel() {

    private val _lyrics = MutableLiveData<Lyric>()
    val lyrics  : LiveData<Lyric> = _lyrics

    fun fetchLyrics() {
//        _lyrics.value = lyrics
    }
}