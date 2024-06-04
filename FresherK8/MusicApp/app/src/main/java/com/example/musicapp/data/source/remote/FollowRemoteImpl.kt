package com.example.musicapp.data.source.remote

import com.example.musicapp.data.model.Status
import com.example.musicapp.data.model.reponse.FollowRepository
import com.example.musicapp.data.source.FollowDataSource
import com.example.musicapp.data.source.remote.api.ApiClient
import retrofit2.Response

class FollowRemoteImpl : FollowDataSource {
    override suspend fun insertFollowTheArtist(
        userId: String,
        artistId: Int
    ): Response<Status> = ApiClient.apiService.insertFollowTheArtist(userId, artistId)

    override suspend fun checkFollowTheArtist(
        userId: String,
        artistId: Int
    ): Response<FollowRepository> = ApiClient.apiService.checkFollowTheArtist(userId, artistId)

    override suspend fun getQuantityFollowTheArtist(userId: String): Response<FollowRepository> =
        ApiClient.apiService.getQuantityFollowTheArtist(userId)

    override suspend fun getFollowTheArtist(userId: String): Response<FollowRepository> =
        ApiClient.apiService.getFollowTheArtist(userId)

    override suspend fun deleteFollowTheArtist(
        userId: String,
        artistId: Int
    ): Response<Status> = ApiClient.apiService.deleteFollowTheArtist(userId, artistId)
}