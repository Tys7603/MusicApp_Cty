package com.example.musicapp.ui.fragment.musicFragment

import com.example.musicapp.model.Song

interface MusicContract {
    interface View {
        fun onListSong(songs: ArrayList<Song>)
        fun onMediaPrepared()
    }

    interface Presenter {
        fun getListSong()
    }
}