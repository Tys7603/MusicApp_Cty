package com.example.musicapp.presentation.explore

import com.example.musicapp.data.model.AlbumLove
import com.example.musicapp.data.model.AlbumNew
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.presentation.base.BasePresenter

interface ExploreContract {
    interface View {
        fun onListPlaylist(playlists : ArrayList<Playlist>)
        fun onListPlaylistMoodToday(playlists : ArrayList<Playlist>)
        fun onListTopic(topics : ArrayList<Topic>)
        fun onListCategory(categories : ArrayList<Category>)
        fun onListListenAgain(songAgain: ArrayList<SongAgain>)
        fun onListAlbumLove(albumLove: ArrayList<AlbumLove>)
        fun onListAlbumNew(albumNew: ArrayList<AlbumNew>)
        fun onListSongRank(songRanks: ArrayList<SongRank>)
    }

    interface Presenter :BasePresenter<View> {
        fun getListPlaylist()
        fun getListPlaylistMoodToday()
        fun getListTopic()
        fun getListCategory()
        fun getListListenAgain(userID : Int)
        fun getListAlbumLove()
        fun getListAlbumNew()
        fun getListSongRank()
    }
}