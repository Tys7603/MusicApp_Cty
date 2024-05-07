package com.example.musicapp.screen.base

interface MusicContract {
    interface View {
        fun onMediaPrepared()
        fun onNextMusic()
        fun onBackMusic()
        fun onPlayMusic()
    }

}