package com.example.musicapp.data.repositories


import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.source.ExploreDataSource
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult


class ExploreRepositoryImpl (private val exploreDataSource: ExploreDataSource) : ExploreRepository {

    override suspend fun getListPlaylist(): DataResult<ArrayList<Playlist>> {
        return try {
            val response = exploreDataSource.getListPlaylist()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.playlists)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListPlaylistMoodToday(): DataResult<ArrayList<Playlist>> {
        return try {
            val response = exploreDataSource.getListPlaylistMoodToday()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.playlists)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListTopic(): DataResult<ArrayList<Topic>> {
        return try {
            val response = exploreDataSource.getListTopic()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.topics)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListCategory(): DataResult<ArrayList<Category>> {
        return try {
            val response = exploreDataSource.getListCategory()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.categories)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListListenAgain(userID: String): DataResult<ArrayList<SongAgain>> {
        return try {
            val response = exploreDataSource.getListSongAgain(userID)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.songAgain)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }

    }

    override suspend fun getListAlbumLove(): DataResult<ArrayList<Album>> {
        return try {
            val response = exploreDataSource.getListAlbumLove()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.albums)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListAlbumNew(): DataResult<ArrayList<Album>> {
        return try {
            val response = exploreDataSource.getListAlbumNew()
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.albums)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getListSongRank(): DataResult<ArrayList<SongRank>> {
        return try {
            val response = exploreDataSource.getListSongRank()
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