package com.example.musicapp.data.source.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.musicapp.data.source.local.entry.SongEntryLocal

class SongDatabase(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(sql: SQLiteDatabase?) {
        val song = "CREATE TABLE ${SongEntryLocal.TABLE_NAME}(" +
                "${SongEntryLocal.COLUMN_SONG_ID} INTEGER PRIMARY KEY,"+
                "${SongEntryLocal.COLUMN_SONG_NAME} TEXT," +
                "${SongEntryLocal.COLUMN_SONG_IMAGE} TEXT," +
                "${SongEntryLocal.COLUMN_SONG_FILE_PATH} TEXT," +
                "${SongEntryLocal.COLUMN_SONG_NAME_ARTIST} TEXT"+
                ")"
        sql?.execSQL(song)
    }

    override fun onUpgrade(sql: SQLiteDatabase?, p1: Int, p2: Int) {
        sql?.execSQL("DROP TABLE  IF EXISTS ${SongEntryLocal.TABLE_NAME}")
        onCreate(sql)
    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "music-app.db"
    }

}