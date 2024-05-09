package com.example.musicapp.screen.musicVideo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.repositories.musicVideoRepository.MusicVideoRepository
import com.example.musicapp.shared.base.BaseViewModel

class MusicVideoViewModel (private val musicVideoRepository: MusicVideoRepository) : BaseViewModel() {
    private val _musicVideos = MutableLiveData<ArrayList<MusicVideo>>()
    val musicVideos: LiveData<ArrayList<MusicVideo>> = _musicVideos

    private val _topics = MutableLiveData<ArrayList<Topic>>()
    val topics: LiveData<ArrayList<Topic>> = _topics

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchMusicVideo()
        fetchCategory()
    }

    private fun fetchMusicVideo() {
        launchTaskSync(
            onRequest = { musicVideoRepository.getListMusicVideo() },
            onSuccess = { _musicVideos.value = it },
            onFailure = { Log.e("fetchMusicVideo", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    private fun fetchCategory() {
        launchTaskSync(
            onRequest = { musicVideoRepository.getListTopic() },
            onSuccess = { _topics.value = it },
            onFailure = { Log.e("fetchCategory", "Failed: $it") },
            onError = { exception.value = it }
        )
    }
}
