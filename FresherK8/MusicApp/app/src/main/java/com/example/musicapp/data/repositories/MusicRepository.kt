package com.example.musicapp.data.repositories

import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.data.source.remote.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult


class MusicRepository(private val songDao: SongDao) {
    suspend fun getListSong(): DataResult<ArrayList<Song>> {
        return try {
            val response = ApiClient.apiService.getListSong()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListSongLove(userId: String): DataResult<ArrayList<Song>> {
        return try {
            val response = ApiClient.apiService.getListSongLove(userId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun createSongLove(userId: String, songId: Int): DataResult<Boolean> {
        return try {
            val response = ApiClient.apiService.createSongLove(userId, songId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(true)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun deleteSongLove(songLoveId: Int): DataResult<Boolean> {
        return try {
            val response = ApiClient.apiService.deleteSongLove(songLoveId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(true)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    fun getListSongLocal(): DataResult<ArrayList<Song>> {
        return try {
            val response = songDao.readSongs()
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListListenAgain(userID: String): DataResult<ArrayList<SongAgain>> {
        return try {
            val response = ApiClient.apiService.getListSongAgain(userID)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songAgain)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }

    }
}