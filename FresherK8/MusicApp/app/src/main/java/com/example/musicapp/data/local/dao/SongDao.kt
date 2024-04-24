package com.example.musicapp.data.local.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.musicapp.data.local.helper.DbHelper
import com.example.musicapp.data.model.Song

class SongDao(val context: Context) {
    private val sqlData: SQLiteDatabase = DbHelper(context, "Music App", null, 1).writableDatabase

    @SuppressLint("Recycle", "Range")
    fun getData(query: String, vararg selections: String): ArrayList<Song> {
        val songs = ArrayList<Song>()
        val cursor = sqlData.rawQuery(query, selections)
        while (cursor.moveToNext()) {
            val song = Song(
                cursor.getString(cursor.getColumnIndex("song_name")),
                cursor.getString(cursor.getColumnIndex("song_image")),
                cursor.getString(cursor.getColumnIndex("song_file_path")),
                cursor.getString(cursor.getColumnIndex("song_name_artist"))
            )
            songs.add(song)
        }
        cursor.close()
        return songs
    }

    fun insertSong(song : Song) : Long {
        val values = ContentValues()
        values.put("song_name", song.name)
        values.put("song_image", song.image)
        values.put("song_file_path", song.url)
        values.put("song_name_artist", song.nameArtis)
        return sqlData.insert("Song", null, values)
    }

    fun readSong() : ArrayList<Song> {
        return getData("SELECT * FROM Song")
    }

    fun deleteSong (songId : String) : Int {
        return sqlData.delete("Song", "song_id = ? ", arrayOf(songId))
    }
}