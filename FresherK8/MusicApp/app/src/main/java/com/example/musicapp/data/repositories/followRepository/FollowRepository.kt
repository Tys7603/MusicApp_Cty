package com.example.musicapp.data.repositories.followRepository

import com.example.musicapp.data.model.Follow
import com.example.musicapp.shared.utils.scheduler.DataResult

interface FollowRepository {
    suspend fun insertFollowTheArtist(userId : String, artistId: Int) : DataResult<Boolean>
    suspend fun checkFollowTheArtist(userId : String, artistId: Int) :  DataResult<Boolean>
    suspend fun getQuantityFollowTheArtist(userId : String) : DataResult<Int>
    suspend fun getFollowTheArtist(userId : String) :  DataResult<List<Follow>>
    suspend fun deleteFollowTheArtist(userId : String, artistId: Int) :  DataResult<Boolean>
}