package com.example.musicapp.shared.utils.di

import com.example.musicapp.screen.account.singup.SingUpViewModel
import com.example.musicapp.screen.explore.ExploreViewModel
import com.example.musicapp.screen.music.MusicViewModel
import com.example.musicapp.screen.topic.TopicViewModel
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.screen.user.adapter.BottomSheetLoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ExploreViewModel(get()) }
    viewModel { MusicViewModel(get()) }
    viewModel { TopicViewModel(get()) }
    viewModel { BottomSheetLoginViewModel() }
    viewModel { SingUpViewModel(get()) }
}