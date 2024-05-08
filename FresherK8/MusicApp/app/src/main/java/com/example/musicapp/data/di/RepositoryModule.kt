package com.example.musicapp.data.di

import com.example.musicapp.data.repositories.ExploreRepository
import com.example.musicapp.data.repositories.ExploreRepositoryImpl
import com.example.musicapp.data.repositories.MusicRepository
import com.example.musicapp.data.repositories.MusicRepositoryImpl
import com.example.musicapp.data.repositories.TopicRepository
import com.example.musicapp.data.repositories.TopicRepositoryImpl
import com.example.musicapp.data.repositories.UserRepository
import com.example.musicapp.data.repositories.UserRepositoryImpl
import com.example.musicapp.data.source.local.dao.SongDao
import org.koin.dsl.module


val repositoryModule = module {
    single<ExploreRepository> { ExploreRepositoryImpl(get()) }
    single<MusicRepository>   { MusicRepositoryImpl( remote = get(), local = get() ) }
    single<TopicRepository>   { TopicRepositoryImpl(get()) }
    single<UserRepository>    { UserRepositoryImpl(get()) }
}