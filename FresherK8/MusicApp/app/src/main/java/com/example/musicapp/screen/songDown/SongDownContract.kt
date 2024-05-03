package com.example.musicapp.screen.songDown

import android.content.Context
import com.example.musicapp.data.model.Song
import com.example.musicapp.screen.base.BasePresenter

interface SongDownContract {
    interface View {
        fun onListSong(songs : ArrayList<Song>)
    }

    interface Presenter : BasePresenter<View> {
        fun getListSong(context: Context)
    }
}