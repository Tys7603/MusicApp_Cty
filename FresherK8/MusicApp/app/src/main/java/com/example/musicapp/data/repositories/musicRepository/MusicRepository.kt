package com.example.musicapp.data.repositories.musicRepository

import androidx.lifecycle.LiveData
import com.example.musicapp.data.model.Lyric
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.Status
import com.example.musicapp.data.model.reponse.SongRepository
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.data.source.remote.api.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult
import retrofit2.Response


interface MusicRepository {
    /**
     * Remote
     */

    suspend fun getListSong(): DataResult<ArrayList<Song>>

    suspend fun getListSongLove(userId: String): DataResult<ArrayList<Song>>

    suspend fun createSongLove(userId: String, songId: Int): DataResult<Boolean>

    suspend fun insertPlaylistIntoPlaylistLove(userId: String, playlistId: Int): DataResult<Boolean>

    suspend fun deleteSongLove(songLoveId: Int): DataResult<Boolean>

    suspend fun getListListenAgain(userID: String): DataResult<ArrayList<SongAgain>>

    suspend fun getListSongTopic(id : Int) : DataResult<ArrayList<Song>>

    suspend fun getListSongPlaylist(id: Int): DataResult<ArrayList<Song>>

    suspend fun getListSongAlbum(id: Int): DataResult<ArrayList<Song>>

    suspend fun getLyricsBySongId(songId: Int): DataResult<ArrayList<Lyric>>

    /**
     * Local
     */

    suspend fun getListSongLocal(): LiveData<ArrayList<Song>>
}