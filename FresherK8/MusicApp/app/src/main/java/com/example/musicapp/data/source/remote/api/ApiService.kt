package com.example.musicapp.data.source.remote.api

import com.example.musicapp.data.model.User
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_ALBUM_LOVE
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_ALBUM_NEW
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_CATEGORIES
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_PLAYLIST
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_PLAYLIST_MODE_TODAY
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_AGAIN
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_RANK
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_TOPICS
import com.example.musicapp.data.model.reponse.AlbumRepository
import com.example.musicapp.data.model.reponse.CategoriesRepository
import com.example.musicapp.data.model.reponse.PlaylistRepository
import com.example.musicapp.data.model.reponse.SongRepository
import com.example.musicapp.data.model.reponse.SongAgainRepository
import com.example.musicapp.data.model.reponse.SongRankRepository
import com.example.musicapp.data.model.Status
import com.example.musicapp.data.model.reponse.LyricRepository
import com.example.musicapp.data.model.reponse.MusicVideoRepository
import com.example.musicapp.data.model.reponse.PlaylistUserRepository
import com.example.musicapp.data.model.reponse.TopicRepository
import com.example.musicapp.shared.utils.constant.ManagerUrl.CREATE_PLAYLIST_USER
import com.example.musicapp.shared.utils.constant.ManagerUrl.CREATE_SONG_LOVE
import com.example.musicapp.shared.utils.constant.ManagerUrl.CREATE_USER
import com.example.musicapp.shared.utils.constant.ManagerUrl.DELETE_PLAYLIST_LOVE
import com.example.musicapp.shared.utils.constant.ManagerUrl.DELETE_PLAYLIST_USER
import com.example.musicapp.shared.utils.constant.ManagerUrl.DELETE_SONG_LOVE
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_LYRIC_SONG_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_MUSIC_VIDEO
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_MUSIC_VIDEO_EXCLUDING_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_PLAYLIST_LOVE
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_PLAYLIST_USER
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_BY_ALBUM_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_BY_PLAYLIST_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_BY_TOPIC_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_SONG_LOVE
import com.example.musicapp.shared.utils.constant.ManagerUrl.GET_TOPIC_BY_CATEGORY_ID
import com.example.musicapp.shared.utils.constant.ManagerUrl.INSERT_SONG_PLAYLIST_USER
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Playlist
    @GET(GET_PLAYLIST)
    suspend fun getListPlaylist(): Response<PlaylistRepository>

    @GET(GET_PLAYLIST_MODE_TODAY)
    suspend fun getListPlaylistMoodToday(): Response<PlaylistRepository>

    @GET(GET_PLAYLIST_USER)
    suspend fun getListPlaylistUser(@Path("userId") userId: String): Response<PlaylistUserRepository>

    @GET(GET_PLAYLIST_LOVE)
    suspend fun getListPlaylistLove(@Path("userId") userId: String): Response<PlaylistRepository>

    @FormUrlEncoded
    @POST(CREATE_PLAYLIST_USER)
    suspend fun createPlaylistUser(
        @Field("userId") userId: String,
        @Field("namePlaylist") namePlaylist: String
    ): Response<Status>

    @FormUrlEncoded
    @POST(INSERT_SONG_PLAYLIST_USER)
    suspend fun insertSongPlaylistUser(
        @Field("playlistUserId") playlistUserId: Int,
        @Field("songId") songId: Int
    ): Response<Status>

    @DELETE(DELETE_PLAYLIST_USER)
    suspend fun deletePlaylistUser(
        @Query("playlistUserId") playlistUserId: String
    ): Response<Status>

    @DELETE(DELETE_PLAYLIST_LOVE)
    suspend fun deletePlaylistLove(
        @Query("playlistLoveId") playlistLoveId: String
    ): Response<Status>

    // Categories and topic
    @GET(GET_TOPICS)
    suspend fun getListTopic(): Response<TopicRepository>

    @GET(GET_CATEGORIES)
    suspend fun getListCategory(): Response<CategoriesRepository>

    @GET(GET_TOPIC_BY_CATEGORY_ID)
    suspend fun getListTopicByIdCategory(@Path("categoryId") categoryId: Int): Response<TopicRepository>

    //song
    @GET(GET_SONG_AGAIN)
    suspend fun getListSongAgain(@Path("userID") user: String): Response<SongAgainRepository>

    @GET(GET_SONG)
    suspend fun getListSong(): Response<SongRepository>

    @GET(GET_SONG_RANK)
    suspend fun getListSongRank(): Response<SongRankRepository>

    @GET(GET_SONG_BY_PLAYLIST_ID)
    suspend fun getListSongPlaylistById(@Path("playlistId") playlistId: Int): Response<SongRepository>

    @GET(GET_SONG_BY_TOPIC_ID)
    suspend fun getListSongTopicById(@Path("topicId") topicId: Int): Response<SongRepository>

    @GET(GET_SONG_BY_ALBUM_ID)
    suspend fun getListSongAlbumById(@Path("albumId") albumId: Int): Response<SongRepository>

    @FormUrlEncoded
    @POST(CREATE_SONG_LOVE)
    suspend fun createSongLove(
        @Field("userId") userId: String,
        @Field("songId") songId: Int
    ): Response<Status>

    @DELETE(DELETE_SONG_LOVE)
    suspend fun deleteSongLove(@Path("songLoveId") songLoveId: Int): Response<Status>

    @GET(GET_SONG_LOVE)
    suspend fun getListSongLove(@Path("userId") userId: String): Response<SongRepository>

    // album
    @GET(GET_ALBUM_LOVE)
    suspend fun getListAlbumLove(): Response<AlbumRepository>

    @GET(GET_ALBUM_NEW)
    suspend fun getListAlbumNew(): Response<AlbumRepository>

    //user
    @FormUrlEncoded
    @POST(CREATE_USER)
    suspend fun createUser(@Field("userId") userId: String): Response<User>

    // music video
    @GET(GET_MUSIC_VIDEO)
    suspend fun getListMusicVideo(): Response<MusicVideoRepository>

    @GET(GET_MUSIC_VIDEO_EXCLUDING_ID)
    suspend fun getListMusicVideoExcludingId(@Path("musicVideoId") musicVideoId: String): Response<MusicVideoRepository>

    // lyrics
    @GET(GET_LYRIC_SONG_ID)
    suspend fun getLyricsBySongId(@Path("songId") songId: Int): Response<LyricRepository>
}