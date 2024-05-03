package com.example.musicapp.screen.songDown

import android.content.Context
import com.example.musicapp.data.source.local.dao.SongDao

class SongDownPresenter : SongDownContract.Presenter {

    private var mView : SongDownContract.View? = null

    override fun getListSong(context: Context) {
        val songs = SongDao(context).readSongs()
        mView?.onListSong(songs)
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: SongDownContract.View?) {
        this.mView = view
    }

    override fun onDestroy() {
        this.mView = null
    }
}