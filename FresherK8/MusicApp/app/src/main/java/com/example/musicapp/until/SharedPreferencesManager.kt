package com.example.musicapp.until

import android.content.Context
import android.content.SharedPreferences
import com.example.musicapp.model.User

class SharedPreferencesManager(context : Context, key : String) {
    private val sharedPreferences : SharedPreferences by lazy {
        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    fun saveValueUser(key: String, value: Boolean){
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getValueUser(key: String, defaultValue: Boolean){
        sharedPreferences.getBoolean(key,defaultValue)
    }

}