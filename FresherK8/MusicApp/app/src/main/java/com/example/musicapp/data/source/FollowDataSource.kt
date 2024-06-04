package com.example.musicapp.data.source

import com.example.musicapp.data.model.Status
import com.example.musicapp.data.model.reponse.FollowRepository
import retrofit2.Response

interface FollowDataSource {
    suspend fun insertFollowTheArtist(userId : String, artistId: Int) : Response<Status>
    suspend fun checkFollowTheArtist(userId : String, artistId: Int) : Response<FollowRepository>
    suspend fun getQuantityFollowTheArtist(userId : String) : Response<FollowRepository>
    suspend fun getFollowTheArtist(userId : String) : Response<FollowRepository>
    suspend fun deleteFollowTheArtist(userId : String, artistId: Int) : Response<Status>
}