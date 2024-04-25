package com.example.musicapp.presentation.songDown

import android.content.Context
import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.BasePresenter

interface SongDownContract {
    interface View {
        fun onListSong(songs : ArrayList<Song>)
    }

    interface Presenter : BasePresenter<View> {
        fun getListSong(context: Context)
    }
}