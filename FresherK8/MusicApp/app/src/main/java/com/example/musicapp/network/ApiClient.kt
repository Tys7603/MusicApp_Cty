package com.example.musicapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null
    private var apiService: ApiService? = null

    private fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            retrofit =  Retrofit.Builder ()
                .baseUrl(ManagerUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit
    }

    fun getApiService(): ApiService? {
        if (apiService == null) {
            apiService = getApiClient()?.create(ApiService::class.java)
        }
        return apiService
    }
}