package com.example.musicapp.ui.fragment.musicFragment

import com.example.musicapp.model.Song

interface MusicContract {
    interface View {
        fun onListSong(songs: ArrayList<Song>)
    }

    interface Presenter {
        fun getListSong()
    }
}