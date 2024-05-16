package com.example.musicapp.screen.lyrics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Lyric
import com.example.musicapp.data.repositories.musicRepository.MusicRepository
import com.example.musicapp.shared.base.BaseViewModel

class LyricViewModel(private val musicRepository: MusicRepository) : BaseViewModel() {

    private val _lyrics = MutableLiveData<ArrayList<Lyric>>()
    val lyrics  : LiveData<ArrayList<Lyric>> = _lyrics

    fun fetchLyrics(songId : Int) {
        launchTaskSync(
            onRequest = { musicRepository.getLyricsBySongId(songId) },
            onSuccess = { _lyrics.value = it },
            onFailure = { Log.e("FetchSong", "Failed: $it") },
            onError = { exception.value = it }
        )
    }
}