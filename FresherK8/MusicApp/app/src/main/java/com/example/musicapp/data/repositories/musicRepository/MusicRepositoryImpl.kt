package com.example.musicapp.data.repositories.musicRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Lyric
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

    override suspend fun insertPlaylistIntoPlaylistLove(
        userId: String,
        playlistId: Int
    ): DataResult<Boolean> {
        return try {
            val response = remote.insertPlaylistIntoPlaylistLove(userId, playlistId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(true)
            } else if (response.body() != null && response.body()!!.status == Constant.STATUS_DUPLICATE) {
                DataResult.Success(false)
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

    override suspend fun getListSongTopic(id: Int): DataResult<ArrayList<Song>> {
        return try {
            val response = remote.getListSongTopic(id)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListSongPlaylist(id: Int): DataResult<ArrayList<Song>> {
        return try {
            val response = remote.getListSongPlaylist(id)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListSongAlbum(id: Int): DataResult<ArrayList<Song>> {
        return try {
            val response = remote.getListSongAlbum(id)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songs)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getLyricsBySongId(songId: Int): DataResult<ArrayList<Lyric>> {
        return try {
            val response = remote.getLyricsBySongId(songId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.lyrics)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR)
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}