package com.example.musicapp.data.repositories


import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.source.remote.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult


class ExploreRepository() {

    suspend fun getListPlaylist(): DataResult<ArrayList<Playlist>> {
        return try {
            val response = ApiClient.apiService.getListPlaylist()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.playlists)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListPlaylistMoodToday(): DataResult<ArrayList<Playlist>> {
        return try {
            val response = ApiClient.apiService.getListPlaylistMoodToday()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.playlists)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListTopic(): DataResult<ArrayList<Topic>> {
        return try {
            val response = ApiClient.apiService.getListTopic()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.topics)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListCategory(): DataResult<ArrayList<Category>> {
        return try {
            val response = ApiClient.apiService.getListCategory()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.categories)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListListenAgain(userID: Int): DataResult<ArrayList<SongAgain>> {

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

    suspend fun getListAlbumLove(): DataResult<ArrayList<Album>> {
        return try {
            val response = ApiClient.apiService.getListAlbumLove()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.albums)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListAlbumNew(): DataResult<ArrayList<Album>> {
        return try {
            val response = ApiClient.apiService.getListAlbumNew()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.albums)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    suspend fun getListSongRank(): DataResult<ArrayList<SongRank>> {
        return try {
            val response = ApiClient.apiService.getListSongRank()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songRanks)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}