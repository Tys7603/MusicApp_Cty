package com.example.musicapp.data.di

import com.example.musicapp.data.repositories.ExploreRepository
import com.example.musicapp.data.repositories.MusicRepository
import com.example.musicapp.data.repositories.TopicRepository
import com.example.musicapp.data.repositories.TopicRepositoryImpl
import com.example.musicapp.data.repositories.UserRepository
import com.example.musicapp.data.source.TopicDataSource
import com.example.musicapp.data.source.local.dao.SongDao
import org.koin.dsl.module

val repositoryModule = module {

    single { SongDao(get()) }
    single { ExploreRepository() }
    single { MusicRepository(get()) }
    single<TopicRepository> { TopicRepositoryImpl(get()) }
    single { UserRepository() }
}