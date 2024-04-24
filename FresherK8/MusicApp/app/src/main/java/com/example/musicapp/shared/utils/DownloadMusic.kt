package com.example.musicapp.shared.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

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
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, fileName)
        // gửi yêu cầu tải xuống
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}