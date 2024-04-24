package com.example.musicapp.data.local.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(sql: SQLiteDatabase?) {
        val song = "CREATE TABLE SONG(" +
                "song_id PRIMARY KEY AUTOINCREMENT,"+
                "song_name text," +
                "song_image text," +
                "song_file_path," +
                "song_name_artist"+
                ")"
        sql?.execSQL(song)
    }

    override fun onUpgrade(sql: SQLiteDatabase?, p1: Int, p2: Int) {
        sql?.execSQL("DROP TABLE  IF EXISTS SONG")
        onCreate(sql)
    }

}