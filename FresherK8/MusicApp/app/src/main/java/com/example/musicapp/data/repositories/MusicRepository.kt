package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.Song
import com.example.musicapp.data.source.remote.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult


class MusicRepository {
    suspend fun getListSong(): DataResult<ArrayList<Song>> {
        return try {
            val response = ApiClient.apiService.getListSong()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}