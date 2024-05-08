package com.example.musicapp.data.repositories.musicVideoRepository

import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.source.MusicVideoDataSource
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

class MusicVideoRepositoryImpl(private val dataSource: MusicVideoDataSource) : MusicVideoRepository {
    override suspend fun getListMusicVideo(): DataResult<ArrayList<MusicVideo>> {
        return try {
            val response = dataSource.getListMusicVideo()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.musicVideos)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        }catch (e : Exception){
            DataResult.Error(e)
        }
    }

    override suspend fun getListCategory(): DataResult<ArrayList<Category>> {
        return try {
            val response = dataSource.getListCategory()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.categories)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}