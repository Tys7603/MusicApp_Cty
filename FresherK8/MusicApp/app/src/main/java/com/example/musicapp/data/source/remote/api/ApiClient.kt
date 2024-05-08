package com.example.musicapp.data.source.remote.api

import com.example.musicapp.shared.utils.constant.ManagerUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null

     fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            retrofit =  Retrofit.Builder ()
                .baseUrl(ManagerUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit
    }

    val apiService: ApiService by lazy {
        getApiClient()!!.create(ApiService::class.java)
    }
}