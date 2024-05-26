package com.example.musicapp.shared.utils

import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic

object ListDefault {

    fun initSong(): Song {
        return Song(0,0,"Loading","https://iili.io/HlHy9Yx.png","","Loading",0)
    }

    fun initListPlaylist(): MutableList<Playlist> {
        return mutableListOf(
            Playlist(0, "Loading", "https://iili.io/HlHy9Yx.png", "Loading...", 0, 0,false),
            Playlist(0, "Loading", "https://iili.io/HlHy9Yx.png", "Loading...", 0, 0,false),
            Playlist(0, "Loading", "https://iili.io/HlHy9Yx.png", "Loading...", 0, 0,false),
            Playlist(0, "Loading", "https://iili.io/HlHy9Yx.png", "Loading...", 0, 0,false),
            Playlist(0, "Loading", "https://iili.io/HlHy9Yx.png", "Loading...", 0, 0,false),
            Playlist(0, "Loading", "https://iili.io/HlHy9Yx.png", "Loading...", 0, 0,false),
        )
    }

    fun initListCategories(): MutableList<Category> {
        return mutableListOf(
            Category(0, "", "https://iili.io/HlHy9Yx.png"),
            Category(0, "", "https://iili.io/HlHy9Yx.png"),
            Category(0, "", "https://iili.io/HlHy9Yx.png"),
            Category(0, "", "https://iili.io/HlHy9Yx.png"),
            Category(0, "", "https://iili.io/HlHy9Yx.png"),
            Category(0, "", "https://iili.io/HlHy9Yx.png"),
            Category(0, "", "https://iili.io/HlHy9Yx.png"),
            Category(0, "", "https://iili.io/HlHy9Yx.png")
        )
    }

    fun initListTopic(): MutableList<Topic> {
        return mutableListOf(
            Topic(0, "Loading...", "https://iili.io/HlHy9Yx.png", 0),
            Topic(0, "Loading...", "https://iili.io/HlHy9Yx.png", 0),
            Topic(0, "Loading...", "https://iili.io/HlHy9Yx.png", 0),
            Topic(0, "Loading...", "https://iili.io/HlHy9Yx.png", 0),
            Topic(0, "Loading...", "https://iili.io/HlHy9Yx.png", 0),
            Topic(0, "Loading...", "https://iili.io/HlHy9Yx.png", 0),
        )
    }

    fun initListAlbum(): MutableList<Album> {
        return mutableListOf(
            Album(0,"Loading","https://iili.io/HlHy9Yx.png","Loading",0),
            Album(0,"Loading","https://iili.io/HlHy9Yx.png","Loading",0),
            Album(0,"Loading","https://iili.io/HlHy9Yx.png","Loading",0),
            Album(0,"Loading","https://iili.io/HlHy9Yx.png","Loading",0),
            Album(0,"Loading","https://iili.io/HlHy9Yx.png","Loading",0),
            Album(0,"Loading","https://iili.io/HlHy9Yx.png","Loading",0),
        )
    }
}