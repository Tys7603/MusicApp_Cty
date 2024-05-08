package com.example.musicapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.source.MusicDataSource
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult


class MusicRepositoryImpl(
    private val remote: MusicDataSource.Remote,
    private val local: MusicDataSource.Local
) : MusicRepository {

   override suspend fun getListSong(): DataResult<ArrayList<Song>> {
        return try {
            val response = remote.getListSong()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListSongLove(userId: String): DataResult<ArrayList<Song>> {
        return try {
            val response = remote.getListSongLove(userId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun createSongLove(userId: String, songId: Int): DataResult<Boolean> {
        return try {
            val response = remote.createSongLove(userId, songId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(true)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun deleteSongLove(songLoveId: Int): DataResult<Boolean> {
        return try {
            val response = remote.deleteSongLove(songLoveId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(true)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListSongLocal(): LiveData<ArrayList<Song>> {
        return try {
            local.getListSongLocal()
        } catch (e: Exception) {
            MutableLiveData()
        }
    }

    override  suspend fun getListListenAgain(userID: String): DataResult<ArrayList<SongAgain>> {
        return try {
            val response = remote.getListSongAgain(userID)
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