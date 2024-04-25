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
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.source.local.dao.SongDao


object DownloadMusic {
    fun downloadMusic(context: Context, song: Song) {
        // tạo yêu cầu tại xuống url
        val request = DownloadManager.Request(Uri.parse(song.url))
        // tải xuống wifi or dữ liệu dt
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        // tiêu đề tải xuống và mô tả
        request.setTitle("Downloading ${song.name}")
        request.setDescription("Downloading music file...")
        // thông báo khi tải hoàn thành
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        // nởi file được lưu
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, song.name)
        // gửi yêu cầu tải xuống

        // Gửi yêu cầu tải xuống
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        SongDao(context).insertSong(song)
//        Log.d("TAG", "onReceive: " + SongDao(context).readSongs().toString())

        // Lưu thông tin bài hát vào SQLite khi tải xuống hoàn thành
//        val onCompleteListener = object : BroadcastReceiver() {
//            @SuppressLint("Range")
//            override fun onReceive(context: Context, intent: Intent) {
//                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
//                    val query = DownloadManager.Query()
//                    query.setFilterById(downloadId)
//                    val cursor = downloadManager.query(query)
//
//                    if (cursor.moveToFirst()) {
//                        val status =
//                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
//                            // Lấy thông tin về bài hát đã tải xuống
//                            val uri =
//                                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
//                            val downloadedSong = Song(song.id, song.name, song.image, uri , song.nameArtis)
//                            // Thêm thông tin bài hát vào SQLite
//                            SongDao(context).insertSong(downloadedSong)
//                            Log.d("TAG", "onReceive: " + SongDao(context).readSongs().toString())
//                        }
//                    }
//                    cursor.close()
//                    // Hủy đăng ký BroadcastReceiver sau khi hoàn thành
//                    context.unregisterReceiver(this)
//                }
//            }
//        }
//
//        // Đăng ký BroadcastReceiver để xử lý khi tải xuống hoàn thành
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                context.registerReceiver(
//                    onCompleteListener,
//                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED
//                )
//            }
//        }
    }
}