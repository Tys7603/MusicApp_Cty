package com.example.musicapp.data.repositories.searchRepository

import com.example.musicapp.data.model.Search
import com.example.musicapp.data.model.SearchAll
import com.example.musicapp.data.source.SearchDataSource
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

class SearchRepositoryImpl (private val dataSource: SearchDataSource) : SearchRepository {

    override suspend fun getSearchAllName(): DataResult<List<SearchAll>> {
        return try {
            val response = dataSource.getSearchAllName()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.search)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getSearch(keyword: String): DataResult<Search> {
        return try {
            val response = dataSource.getSearchKeyWord(keyword)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.search)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}