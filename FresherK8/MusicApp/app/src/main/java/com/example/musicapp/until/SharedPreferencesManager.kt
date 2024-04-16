package com.example.musicapp.until

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferencesManager(context : Context) {
    private val sharedPreferences : SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveValue(key: String, value: Boolean){
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getValue(key: String, defaultValue: Boolean) = sharedPreferences.getBoolean(key,defaultValue)

    fun clear(){
        sharedPreferences.edit().clear().apply()
    }

}