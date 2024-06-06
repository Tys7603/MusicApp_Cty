package com.example.musicapp.screen.topic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.repositories.topicRepository.TopicRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.example.musicapp.shared.utils.scheduler.DataResult


class TopicViewModel(private val topicRepository: TopicRepository) : BaseViewModel() {
    private val _topics = MutableLiveData<ArrayList<Topic>>()
    val topics : LiveData<ArrayList<Topic>> = _topics

    fun fetchTopic(id : Int){
       fetchDataWithSync(
           {topicRepository.getListTopicByIdCategory(id)},
           {_topics.value = it}
       )
    }

    private inline fun <T> fetchDataWithSync(
        crossinline onRequest: suspend () -> DataResult<T>,
        crossinline onSuccess: (T) -> Unit
    ) {
        launchTaskSync(
            onRequest = { onRequest() },
            onSuccess = { onSuccess(it) },
            onFailure = { message -> Log.e("FetchSong", "Failed: $message") },
            onError = { exception.value = it }
        )
    }
}