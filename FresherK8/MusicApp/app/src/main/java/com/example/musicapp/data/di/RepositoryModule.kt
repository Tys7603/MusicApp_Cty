package com.example.musicapp.data.di

import com.example.musicapp.data.repositories.ExploreRepository
import com.example.musicapp.data.repositories.MusicRepository
import com.example.musicapp.data.repositories.TopicRepository
import com.example.musicapp.data.repositories.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ExploreRepository() }
    single { MusicRepository() }
    single { TopicRepository() }
    single { UserRepository() }
}