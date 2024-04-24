package com.example.musicapp.presentation.music

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.BasePresenter

interface MusicContract {
    interface View {
        fun onListSong(songs: ArrayList<Song>)
        fun onMediaPrepared()
        fun onNextMusic()
        fun onBackMusic()
        fun onPlayMusic()
    }

    interface Presenter : BasePresenter<View> {
        fun getListSong()
    }
}