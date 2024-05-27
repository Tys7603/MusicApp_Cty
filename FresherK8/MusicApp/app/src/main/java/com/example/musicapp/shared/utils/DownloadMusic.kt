package com.example.musicapp.shared.utils

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.shared.utils.constant.Constant


object DownloadMusic {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun downloadMusic(context: Context, song: Song) {
            val request = DownloadManager.Request(Uri.parse(song.url))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle("Downloading ${song.name}")
            request.setDescription("Downloading music file...")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, song.name)
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)
            Toast.makeText(context, Constant.KEY_DOWN, Toast.LENGTH_SHORT).show()

            val onCompleteListener = object : BroadcastReceiver() {
                @SuppressLint("Range")
                override fun onReceive(context: Context, intent: Intent) {
                    Log.d("DownloadMusic", "onReceive: ")
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                        val query = DownloadManager.Query()
                        query.setFilterById(downloadId)
                        val cursor = downloadManager.query(query)

                        if (cursor.moveToFirst()) {
                            val status =
                                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                // Lấy thông tin về bài hát đã tải xuống
                                val uri =
                                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                                val downloadedSong = Song(song.songLoveId,song.id, song.name, song.image, uri , song.nameArtis, 1)
                                // Thêm thông tin bài hát vào SQLite
                                SongDao(context).insertSong(downloadedSong)
                                Log.d("DownloadMusic", "onReceive: " +   SongDao(context).readSongs().value.toString())
                            }
                        }
                        cursor.close()
                        context.unregisterReceiver(this)
                    }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.registerReceiver(
                    onCompleteListener,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED
                )
            }else{
                context.registerReceiver(
                    onCompleteListener,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED)
            }
    }
}