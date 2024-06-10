package com.example.musicapp.screen.musicVideoDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.repositories.followRepository.FollowRepository
import com.example.musicapp.data.repositories.musicVideoRepository.MusicVideoRepository
import com.example.musicapp.shared.base.BaseViewModel

class MusicVideoDetailViewModel(
    private val musicVideoRepository: MusicVideoRepository,
    private val followRepository: FollowRepository
) : BaseViewModel() {
    private val _musicVideos = MutableLiveData<ArrayList<MusicVideo>>()
    val musicVideos: LiveData<ArrayList<MusicVideo>> = _musicVideos

    private val _topics = MutableLiveData<ArrayList<Topic>>()
    val topics: LiveData<ArrayList<Topic>> = _topics

    private val _isFollow = MutableLiveData<Boolean>()
    val isFollow: LiveData<Boolean> = _isFollow

    private val _isInsert = MutableLiveData<Boolean>()
    val isInsert: LiveData<Boolean> = _isInsert

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    fun checkFollowTheArtist(userId: String, artistId: Int) {
        launchTaskSync(
            onRequest = { followRepository.checkFollowTheArtist(userId, artistId) },
            onSuccess = { _isFollow.value = it },
            onFailure = { Log.e("MusicVideoDetailViewModel", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun insertFollowTheArtist(userId: String, artistId: Int) {
        launchTaskSync(
            onRequest = { followRepository.insertFollowTheArtist(userId, artistId) },
            onSuccess = { _isInsert.value = it },
            onFailure = { Log.e("MusicVideoDetailViewModel", "Failed: $it") },
            onError = { exception.value = it }
        )
    }


    fun deleteFollowTheArtist(userId: String, artistId: Int) {
        launchTaskSync(
            onRequest = { followRepository.deleteFollowTheArtist(userId, artistId) },
            onSuccess = { _isDelete.value = it },
            onFailure = { Log.e("MusicVideoDetailViewModel", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun fetchMusicVideoDetail(musicVideoId: String) {
        launchTaskSync(
            onRequest = { musicVideoRepository.getListMusicVideoDetail(musicVideoId) },
            onSuccess = { _musicVideos.value = it },
            onFailure = { Log.e("MusicVideoDetailViewModel", "Failed: $it") },
            onError = { exception.value = it }
        )
    }
}
