package com.example.musicapp.data.di

import com.example.musicapp.data.repositories.ExploreRepository
import com.example.musicapp.data.repositories.MusicRepository
import com.example.musicapp.data.repositories.TopicRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ExploreRepository(get()) }
    single { MusicRepository(get()) }
    single { TopicRepository(get()) }
}