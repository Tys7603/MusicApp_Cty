package com.example.musicapp.data.di

import com.example.musicapp.data.source.ExploreDataSource
import com.example.musicapp.data.source.MusicDataSource
import com.example.musicapp.data.source.MusicVideoDataSource
import com.example.musicapp.data.source.TopicDataSource
import com.example.musicapp.data.source.UserDataSource
import com.example.musicapp.data.source.local.MusicLocalImpl
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.data.source.remote.ExploreRemoteImpl
import com.example.musicapp.data.source.remote.MusicRemoteImpl
import com.example.musicapp.data.source.remote.MusicVideoRemoteImpl
import com.example.musicapp.data.source.remote.TopicRemoteImpl
import com.example.musicapp.data.source.remote.UserRemoteImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    single { SongDao(androidContext()) }

    single<TopicDataSource> { TopicRemoteImpl() }
    single<ExploreDataSource> { ExploreRemoteImpl() }
    single<MusicDataSource.Remote> { MusicRemoteImpl() }
    single<MusicDataSource.Local> { MusicLocalImpl(get()) }
    single<UserDataSource> { UserRemoteImpl() }
    single<MusicVideoDataSource> { MusicVideoRemoteImpl() }
}
