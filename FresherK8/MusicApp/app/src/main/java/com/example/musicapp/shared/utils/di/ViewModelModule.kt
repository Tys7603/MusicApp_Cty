package com.example.musicapp.shared.utils.di

import com.example.musicapp.screen.account.singup.SingUpViewModel
import com.example.musicapp.screen.explore.ExploreViewModel
import com.example.musicapp.screen.lyrics.LyricViewModel
import com.example.musicapp.screen.music.MusicViewModel
import com.example.musicapp.screen.musicVideo.MusicVideoViewModel
import com.example.musicapp.screen.musicVideoDetail.MusicVideoDetailViewModel
import com.example.musicapp.screen.songDetail.SongDetailViewModel
import com.example.musicapp.screen.songUser.SongUserViewModel
import com.example.musicapp.screen.topic.TopicViewModel
import com.example.musicapp.screen.user.UserViewModel
import com.example.musicapp.screen.user.adapter.BottomSheetLoginViewModel
import com.example.musicapp.screen.user.PlaylistLoveViewModel
import com.example.musicapp.screen.user.PlaylistUserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ExploreViewModel(get()) }
    viewModel { MusicViewModel(get()) }
    viewModel { TopicViewModel(get()) }
    viewModel { BottomSheetLoginViewModel() }
    viewModel { SingUpViewModel(get(), androidContext()) }
    viewModel { UserViewModel(get()) }
    viewModel { SongUserViewModel(get(), get()) }
    viewModel { MusicVideoViewModel(get()) }
    viewModel { MusicVideoDetailViewModel(get()) }
    viewModel { PlaylistUserViewModel(get()) }
    viewModel { PlaylistLoveViewModel(get()) }
    viewModel { SongDetailViewModel(get()) }
    viewModel { LyricViewModel(get()) }

}