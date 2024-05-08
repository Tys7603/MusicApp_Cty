package com.example.musicapp.data.source.local.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.source.local.database.SongDatabase
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.source.local.entry.SongEntryLocal

class SongDao(val context: Context) {
    private val sqlData: SQLiteDatabase = SongDatabase(context).writableDatabase

    @SuppressLint("Recycle", "Range")
    fun getData(query: String, vararg selections: String): LiveData<ArrayList<Song>> {
        val songs = MutableLiveData<ArrayList<Song>>()
        val songList = arrayListOf<Song>()
        val cursor = sqlData.rawQuery(query, selections)
        while (cursor.moveToNext()) {
            val song = Song(
                0,
                cursor.getInt(cursor.getColumnIndex(SongEntryLocal.COLUMN_SONG_ID)) ,
                cursor.getString(cursor.getColumnIndex(SongEntryLocal.COLUMN_SONG_NAME)),
                cursor.getString(cursor.getColumnIndex(SongEntryLocal.COLUMN_SONG_IMAGE)),
                cursor.getString(cursor.getColumnIndex(SongEntryLocal.COLUMN_SONG_FILE_PATH)),
                cursor.getString(cursor.getColumnIndex(SongEntryLocal.COLUMN_SONG_NAME_ARTIST)),
                1
            )
            songList.add(song)
        }
        cursor.close()
        songs.value = songList
        return songs
    }

    fun insertSong(song : Song) : Long {
        val values = ContentValues()
        values.put(SongEntryLocal.COLUMN_SONG_ID, song.id)
        values.put(SongEntryLocal.COLUMN_SONG_NAME, song.name)
        values.put(SongEntryLocal.COLUMN_SONG_IMAGE, song.image)
        values.put(SongEntryLocal.COLUMN_SONG_FILE_PATH, song.url)
        values.put(SongEntryLocal.COLUMN_SONG_NAME_ARTIST, song.nameArtis)
        return sqlData.insert(SongEntryLocal.TABLE_NAME, null, values)
    }

    fun readSongs() : LiveData<ArrayList<Song>> {
        return getData("SELECT * FROM ${SongEntryLocal.TABLE_NAME}")
    }

    fun deleteSong (songId : String) : Int {
        return sqlData.delete(SongEntryLocal.TABLE_NAME, "${SongEntryLocal.COLUMN_SONG_ID} = ? ", arrayOf(songId))
    }
}