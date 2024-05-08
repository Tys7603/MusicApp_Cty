package com.example.musicapp.data.di

import com.example.musicapp.data.repositories.TopicRepositoryImpl
import com.example.musicapp.data.source.TopicDataSource
import com.example.musicapp.data.source.remote.TopicRemoteImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<TopicDataSource> { TopicRemoteImpl() }
}
