package com.example.musicapp.data.repositories.musicVideoRepository

import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Topic
import com.example.musicapp.shared.utils.scheduler.DataResult

interface MusicVideoRepository {
    suspend fun getListMusicVideo(): DataResult<ArrayList<MusicVideo>>
    suspend fun getListTopic(): DataResult<ArrayList<Topic>>
}

