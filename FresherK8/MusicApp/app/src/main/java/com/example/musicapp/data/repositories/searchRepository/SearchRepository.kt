package com.example.musicapp.data.repositories.searchRepository

import com.example.musicapp.data.model.Search
import com.example.musicapp.data.model.SearchAll
import com.example.musicapp.shared.utils.scheduler.DataResult

interface SearchRepository {
    suspend fun getSearchAllName() : DataResult<List<SearchAll>>
    suspend fun getSearch(keyword : String) : DataResult<Search>
}