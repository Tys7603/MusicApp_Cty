package com.example.musicapp.data.di

import com.example.musicapp.data.repositories.exploreReposotory.ExploreRepository
import com.example.musicapp.data.repositories.exploreReposotory.ExploreRepositoryImpl
import com.example.musicapp.data.repositories.musicRepository.MusicRepository
import com.example.musicapp.data.repositories.musicRepository.MusicRepositoryImpl
import com.example.musicapp.data.repositories.musicVideoRepository.MusicVideoRepository
import com.example.musicapp.data.repositories.musicVideoRepository.MusicVideoRepositoryImpl
import com.example.musicapp.data.repositories.searchRepository.SearchRepository
import com.example.musicapp.data.repositories.searchRepository.SearchRepositoryImpl
import com.example.musicapp.data.repositories.topicRepository.TopicRepository
import com.example.musicapp.data.repositories.topicRepository.TopicRepositoryImpl
import com.example.musicapp.data.repositories.userRepository.UserRepository
import com.example.musicapp.data.repositories.userRepository.UserRepositoryImpl
import org.koin.dsl.module


val repositoryModule = module {
    single<ExploreRepository> { ExploreRepositoryImpl(get()) }
    single<MusicRepository>   { MusicRepositoryImpl( remote = get(), local = get() ) }
    single<TopicRepository>   { TopicRepositoryImpl(get()) }
    single<UserRepository>    { UserRepositoryImpl(get()) }
    single<MusicVideoRepository>    { MusicVideoRepositoryImpl(get()) }
    single<SearchRepository>    { SearchRepositoryImpl(get()) }
}