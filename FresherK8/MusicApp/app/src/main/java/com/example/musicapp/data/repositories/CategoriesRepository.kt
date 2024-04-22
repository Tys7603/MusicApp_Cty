package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.Category

data class CategoriesRepository (
    val status : Int,
    val categories : ArrayList<Category>
)