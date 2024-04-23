package com.example.musicapp.presentation.topic

import com.example.musicapp.data.model.Topic
import com.example.musicapp.presentation.base.BasePresenter

interface TopicContract {
    interface View {
        fun onListTopicByIdCategory(topics : ArrayList<Topic>)
    }

    interface Presenter : BasePresenter<View>{
        fun getListTopicByIdCategory(id : Int)
    }
}