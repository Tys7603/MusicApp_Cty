package com.example.musicapp.data.di

import com.example.musicapp.data.source.remote.api.ApiClient
import org.koin.dsl.module

val networkModule = module {
    single {
        ApiClient.getApiClient()
    }
    single {
        ApiClient.apiService
    }
}