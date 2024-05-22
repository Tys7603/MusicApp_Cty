package com.example.musicapp.data.source

import androidx.lifecycle.LiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.Status
import com.example.musicapp.data.model.reponse.LyricRepository
import com.example.musicapp.data.model.reponse.SongAgainRepository
import com.example.musicapp.data.model.reponse.SongRepository
import retrofit2.Response

interface MusicDataSource {
    interface Remote {
        suspend fun getListSong(): Response<SongRepository>
        suspend fun getListSongLove(userId: String): Response<SongRepository>
        suspend fun createSongLove(userId: String, songId: Int): Response<Status>
        suspend fun insertPlaylistIntoPlaylistLove(userId: String, playlistId: Int): Response<Status>
        suspend fun deleteSongLove(songLoveId: Int): Response<Status>
        suspend fun getListSongAgain(user: String): Response<SongAgainRepository>
        suspend fun getListSongTopic(id: Int): Response<SongRepository>
        suspend fun getListSongPlaylist(id: Int): Response<SongRepository>
        suspend fun getListSongAlbum(id: Int): Response<SongRepository>
        suspend fun getListSongPlaylistUser(playlistUserId: Int): Response<SongRepository>
        suspend fun getLyricsBySongId(songId: Int): Response<LyricRepository>
    }

    interface Local {
        suspend fun getListSongLocal(): LiveData<ArrayList<Song>>
    }

}