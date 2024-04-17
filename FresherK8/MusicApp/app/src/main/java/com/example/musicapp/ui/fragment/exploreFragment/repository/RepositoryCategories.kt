package com.example.musicapp.ui.fragment.exploreFragment.repository

import com.example.musicapp.model.Category
import com.example.musicapp.model.Topic

data class RepositoryCategories (
    val status : Int,
    val categories : ArrayList<Category>
)