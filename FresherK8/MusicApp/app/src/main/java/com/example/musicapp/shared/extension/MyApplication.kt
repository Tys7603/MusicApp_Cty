package com.example.musicapp.shared.extension

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MyApplication : Application() {
    companion object{
        const val CHANNEL_ID = "music-app"
    }
    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, "Channel music", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.setSound(null, null)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(notificationChannel)
        }
    }
}