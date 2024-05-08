package com.example.musicapp.data.repositories

import androidx.lifecycle.LiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.data.source.remote.api.ApiClient
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult


interface MusicRepository {
    /**
     * Remote
     */

    suspend fun getListSong(): DataResult<ArrayList<Song>>

    suspend fun getListSongLove(userId: String): DataResult<ArrayList<Song>>

    suspend fun createSongLove(userId: String, songId: Int): DataResult<Boolean>

    suspend fun deleteSongLove(songLoveId: Int): DataResult<Boolean>

    suspend fun getListListenAgain(userID: String): DataResult<ArrayList<SongAgain>>

    /**
     * Local
     */

    suspend fun getListSongLocal(): LiveData<ArrayList<Song>>
}