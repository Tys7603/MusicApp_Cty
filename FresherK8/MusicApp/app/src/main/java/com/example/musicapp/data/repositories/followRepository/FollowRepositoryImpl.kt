package com.example.musicapp.data.repositories.followRepository

import com.example.musicapp.data.model.Follow
import com.example.musicapp.data.source.FollowDataSource
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult

class FollowRepositoryImpl(private val followDataSource: FollowDataSource) : FollowRepository {

    override suspend fun insertFollowTheArtist(userId: String, artistId: Int): DataResult<Boolean> {
        return try {
            val response = followDataSource.insertFollowTheArtist(userId, artistId)
            if (response.isSuccessful) {
                if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                    DataResult.Success(true)
                } else {
                    DataResult.Success(false)
                }
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun checkFollowTheArtist(userId: String, artistId: Int): DataResult<Boolean> {
        return try {
            val response = followDataSource.checkFollowTheArtist(userId, artistId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                if (response.body()!!.follows[0].isFollow == 1) {
                    DataResult.Success(true)
                } else {
                    DataResult.Success(false)
                }
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getQuantityFollowTheArtist(userId: String): DataResult<Int> {
        return try {
            val response = followDataSource.getQuantityFollowTheArtist(userId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.follows[0].quantity)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getFollowTheArtist(userId: String): DataResult<List<Follow>> {
        return try {
            val response = followDataSource.getFollowTheArtist(userId)
            if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                DataResult.Success(response.body()!!.follows)
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.code())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun deleteFollowTheArtist(userId: String, artistId: Int): DataResult<Boolean> {
        return try {
            val response = followDataSource.deleteFollowTheArtist(userId, artistId)
            if (response.isSuccessful) {
                if (response.body() != null && response.body()!!.status == Constant.STATUS) {
                    DataResult.Success(true)
                } else {
                    DataResult.Success(false)
                }
            } else {
                DataResult.Failure(Constant.CALL_API_ERROR + response.message())
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}