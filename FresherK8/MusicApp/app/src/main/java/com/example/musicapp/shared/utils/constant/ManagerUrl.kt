package com.example.musicapp.shared.utils.constant

object ManagerUrl {
    const val IP = "10.20.23.239"
    const val BASE_URL = "http://$IP:3000/api/"

    // playlist
    const val GET_PLAYLIST = "playlists"
    const val GET_PLAYLIST_MODE_TODAY = "playlists/mood/today"

    // category and topic
    const val GET_TOPICS = "topics"
    const val GET_CATEGORIES = "categories"
    const val GET_TOPIC_BY_CATEGORY_ID = "topics/categories/{categoryId}"

    //song
    const val GET_SONG_AGAIN = "songs/Again/{userID}"
    const val GET_SONG = "songs"
    const val GET_SONG_RANK = "songs/rank/listen"
    const val GET_SONG_BY_PLAYLIST_ID = "songs/playlist/{playlistId}"
    const val GET_SONG_BY_TOPIC_ID = "songs/topic/{topicId}"

    //album
    const val GET_ALBUM_LOVE = "albums/love"
    const val GET_ALBUM_NEW = "albums/new"


}