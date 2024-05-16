package com.example.musicapp.screen.musicVideoDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.repositories.musicVideoRepository.MusicVideoRepository
import com.example.musicapp.shared.base.BaseViewModel

class MusicVideoDetailViewModel (private val musicVideoRepository: MusicVideoRepository) : BaseViewModel() {
    private val _musicVideos = MutableLiveData<ArrayList<MusicVideo>>()
    val musicVideos: LiveData<ArrayList<MusicVideo>> = _musicVideos

    private val _topics = MutableLiveData<ArrayList<Topic>>()
    val topics: LiveData<ArrayList<Topic>> = _topics

    fun fetchMusicVideoDetail(musicVideoId : String) {
        launchTaskSync(
            onRequest = { musicVideoRepository.getListMusicVideoDetail(musicVideoId) },
            onSuccess = { _musicVideos.value = it },
            onFailure = { Log.e("fetchMusicVideo", "Failed: $it") },
            onError = { exception.value = it }
        )
    }
}
