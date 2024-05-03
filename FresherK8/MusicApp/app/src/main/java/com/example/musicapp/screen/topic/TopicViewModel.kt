package com.example.musicapp.screen.topic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.repositories.TopicRepository


class TopicViewModel(private val topicRepository: TopicRepository) : ViewModel() {
    private val _topics = MutableLiveData<ArrayList<Topic>>()
    val topics : LiveData<ArrayList<Topic>> = _topics

    fun fetchTopic(id : Int){
        val topicsApi = topicRepository.getListTopicByIdCategory(id)
        _topics.value = topicsApi
    }

}