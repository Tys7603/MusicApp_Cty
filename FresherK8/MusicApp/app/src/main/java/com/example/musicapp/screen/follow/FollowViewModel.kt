package com.example.musicapp.screen.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Follow
import com.example.musicapp.data.repositories.followRepository.FollowRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class FollowViewModel(private val followRepository: FollowRepository) : BaseViewModel() {
    private val _follows = MutableLiveData<List<Follow>>()
    val follows : LiveData<List<Follow>> = _follows

    init {
        fetchFollows()
    }

    private fun fetchFollows() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            launchTaskSync(
                onRequest = {followRepository.getFollowTheArtist(user.uid)},
                onSuccess = {_follows.value = it},
                onFailure = { Log.d("FollowActivity", "fetchFollows: $it")},
                onError = {exception.value = it  }
            )
        }
    }
}