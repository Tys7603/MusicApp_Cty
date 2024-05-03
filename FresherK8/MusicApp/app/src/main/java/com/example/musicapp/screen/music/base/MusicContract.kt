package com.example.musicapp.screen.music.base

interface MusicContract {
    interface View {
        fun onMediaPrepared()
        fun onNextMusic()
        fun onBackMusic()
        fun onPlayMusic()
    }

}