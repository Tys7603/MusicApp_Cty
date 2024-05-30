package com.example.musicapp.data.repositories.musicVideoRepository

import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Topic
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

    override suspend fun getListMusicVideoDetail(musicVideoId: String): DataResult<ArrayList<MusicVideo>> {
        return try {
            val response = dataSource.getListMusicVideoDetail(musicVideoId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.musicVideos)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        }catch (e : Exception){
            DataResult.Error(e)
        }
    }

    override suspend fun getListTopic(): DataResult<ArrayList<Topic>> {
        return try {
            val response = dataSource.getListTopic()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.topics)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}