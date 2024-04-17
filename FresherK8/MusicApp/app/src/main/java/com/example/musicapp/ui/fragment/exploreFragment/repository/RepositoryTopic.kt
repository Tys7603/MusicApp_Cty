package com.example.musicapp.ui.fragment.exploreFragment.repository

import com.example.musicapp.model.Category
import com.example.musicapp.model.Topic

data class RepositoryTopic (
    val status : Int,
    val topics : ArrayList<Topic>,
)