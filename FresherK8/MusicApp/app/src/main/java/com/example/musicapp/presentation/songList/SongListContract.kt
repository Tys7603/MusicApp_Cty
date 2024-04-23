package com.example.musicapp.presentation.songList

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.BasePresenter

interface SongListContract {
    interface View {
        fun onListSong(songs : ArrayList<Song>)
    }

    interface Presenter : BasePresenter<View> {
        fun getListSongTopic(id : Int)
        fun getListSongPlaylist(id : Int)
    }
}