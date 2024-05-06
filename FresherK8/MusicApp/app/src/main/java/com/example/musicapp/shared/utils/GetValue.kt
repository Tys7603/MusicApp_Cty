package com.example.musicapp.shared.utils

import android.content.SharedPreferences
import com.example.musicapp.data.model.Song
import com.example.musicapp.shared.utils.constant.Constant
import com.google.gson.Gson

object GetValue {
    fun getSong(sharedPreferences: SharedPreferences): Song? {
        val jsonSong = sharedPreferences.getString(Constant.KEY_SONG, "")
        if (jsonSong.isNullOrEmpty()) {
            return null
        }
        return Gson().fromJson(jsonSong, Song::class.java)
    }
}