package com.example.musicapp.shared.utils.constant

object ManagerUrl {
    private const val IP = "192.168.1.7"
    private const val IP_CTY = "10.20.23.239"
//    private const val IP_HOST = "https://lpzx2jg5-3000.asse.devtunnels.ms/api/"
    private const val IP_HOST = "https://6ztfh0rs-3000.asse.devtunnels.ms/api/"
//    const val BASE_URL = "http://$IP:3000/api/"
    const val BASE_URL = IP_HOST

    // playlist
    const val GET_PLAYLIST = "playlists"
    const val GET_PLAYLIST_MODE_TODAY = "playlists/mood/today"
    const val GET_PLAYLIST_USER = "playlists/{userId}"
    const val GET_PLAYLIST_LOVE = "playlists/love/{userId}"
    const val CREATE_PLAYLIST_USER = "playlist/user"
    const val DELETE_PLAYLIST_USER = "playlistsUser"
    const val DELETE_PLAYLIST_LOVE = "playlistsLove"
    const val INSERT_SONG_PLAYLIST_USER = "playlist/user/song"
    const val INSERT_SONG_PLAYLIST_LOVE = "playlist/user/love"

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
    const val GET_SONG_BY_ALBUM_ID = "songs/album/{albumId}"
    const val CREATE_SONG_LOVE = "song/love"
    const val CREATE_SONG_AGAIN = "song/again"
    const val DELETE_SONG_LOVE = "song/love/{songLoveId}"
    const val GET_SONG_LOVE = "songs/love/{userId}"
    const val GET_SONG_PLAYLIST_USER = "playlists/songs/{playlistUserId}"

    //album
    const val GET_ALBUM_LOVE = "albums/love"
    const val GET_ALBUM_NEW = "albums/new"

    //user
    const val CREATE_USER = "user"

    // music video
    const val GET_MUSIC_VIDEO = "musics/video"
    const val GET_MUSIC_VIDEO_EXCLUDING_ID = "musics/video/{musicVideoId}"

    // artist
    const val INSERT_ARTIST = "follow"
    const val CHECK_USER_FOLLOW_ARTIST = "followed/{userId}/{artistId}"
    const val GET_QUANTITY_USER_FOLLOW_ARTIST= "follow/quantity/{userId}"
    const val GET_USER_FOLLOW_ARTIST = "follows/{userId}"
    const val DELETE_USER_FOLLOW_ARTIST = "follow/{userId}/{artistId}"

    // lyric
    const val GET_LYRIC_SONG_ID = "lyrics/{songId}"

    //search
    const val GET_SEARCH_NAME_ALL = "search/all/name"
    const val GET_SEARCH = "search/{keyword}"
}