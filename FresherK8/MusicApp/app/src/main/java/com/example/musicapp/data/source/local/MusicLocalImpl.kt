package com.example.musicapp.data.source.local

import androidx.lifecycle.LiveData
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repositories.TopicRepositoryImpl
import com.example.musicapp.data.source.MusicDataSource
import com.example.musicapp.data.source.local.dao.SongDao
import retrofit2.Response

class MusicLocalImpl(private val songDao: SongDao) : MusicDataSource.Local{
    override suspend fun getListSongLocal(): LiveData<ArrayList<Song>> = songDao.readSongs()

}