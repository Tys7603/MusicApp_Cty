package com.example.musicapp.shared.utils
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.TrackInfo

object DownloadMusic {
    fun downloadMusic(context: Context, url: String, fileName: String) {
        // tạo yêu cầu tại xuống url
        val request = DownloadManager.Request(Uri.parse(url))
        // tải xuống wifi or dữ liệu dt
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        // tiêu đề tải xuống và mô tả
        request.setTitle("Downloading $fileName")
        request.setDescription("Downloading music file...")
        // thông báo khi tải hoàn thành
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        // nởi file được lưu
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, fileName.trim() + System.currentTimeMillis())
        // gửi yêu cầu tải xuống
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    @SuppressLint("Range")
    fun getDownloadedTracksInfo(context: Context): MutableList<TrackInfo> {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query()
        query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL) // Lọc các tệp đã tải thành công
        val cursor: Cursor = downloadManager.query(query)
        val trackInfoList = mutableListOf<TrackInfo>()


        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE))
            val uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))

            // Lấy thông tin từ MediaStore
            val contentResolver = context.contentResolver
            val mediaUri = Uri.parse(uri)
            val projection = arrayOf(
                MediaStore.Audio.AudioColumns.ALBUM_ID,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ARTIST
            )
            val mediaCursor: Cursor? = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns.TITLE}=?",
                arrayOf(title),
                null
            )

            mediaCursor?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID))
                    val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST))
                    val albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId)
                    trackInfoList.add(TrackInfo(title, artist, albumArtUri, uri))
                } else {
                    Log.d("UserFragment", "No data found in MediaStore for title: $title")
                }
            } ?: Log.d("UserFragment", "MediaCursor is null or empty")
        }
        cursor.close()
        return trackInfoList
    }
}