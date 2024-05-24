package com.example.musicapp.screen.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Search
import com.example.musicapp.data.model.SearchAll
import com.example.musicapp.data.repositories.searchRepository.SearchRepository
import com.example.musicapp.shared.base.BaseViewModel

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {
    private val _searchAll= MutableLiveData<List<SearchAll>>()
    val searchAll: LiveData<List<SearchAll>> = _searchAll

    private val _search= MutableLiveData<Search>()
    val search: LiveData<Search> = _search

    init {
        fetchSearchAll()
    }

    private fun fetchSearchAll() {
        launchTaskSync(
            onRequest = { searchRepository.getSearchAllName() },
            onSuccess = { _searchAll.value = it },
            onFailure = { Log.e("fetchSearch", "Failed: $it") },
            onError = { exception.value = it }
        )
    }

    fun fetchSearch(keyword : String) {
        launchTaskSync(
            onRequest = { searchRepository.getSearch(keyword) },
            onSuccess = { _search.value = it },
            onFailure = { Log.e("fetchSearch", "Failed: $it") },
            onError = { exception.value = it }
        )
    }
}