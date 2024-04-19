package com.example.musicapp.network

object ManagerUrl {
     const val BASE_URL = "http://10.20.23.239:3000/api/"

    // playlist
    const val GET_PLAYLIST = "playlists"
    const val GET_PLAYLIST_MODE_TODAY = "playlists/mood/today"

    // category and topic
    const val GET_TOPICS= "topics"
    const val GET_CATEGORIES= "categories"

    //song again
    const val GET_SONG_AGAIN= "songs/Again/{userID}"

    //album love
    const val GET_ALBUM_LOVE= "albums/love"

    //album new
    const val GET_ALBUM_NEW= "albums/new"

    //album new
    const val GET_SONG= "songs"
}