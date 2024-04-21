package com.example.musicapp.ui.fragment.exploreFragment

import com.example.musicapp.model.AlbumLove
import com.example.musicapp.model.AlbumNew
import com.example.musicapp.model.Category
import com.example.musicapp.model.Playlist
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongAgain
import com.example.musicapp.model.SongRank
import com.example.musicapp.model.Topic
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositorySongAgain
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryTopic

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

    interface Presenter {
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