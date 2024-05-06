package com.example.musicapp.data.di

import com.example.musicapp.data.source.remote.ApiClient
import org.koin.dsl.module

val networkModule = module {
    single {
        ApiClient.getApiClient()
    }
    single {
        ApiClient.apiService
    }
}