package com.example.musicapp.shared.utils.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.musicapp.data.di.dataSourceModule
import com.example.musicapp.data.di.networkModule
import com.example.musicapp.data.di.repositoryModule
import com.google.android.gms.cast.framework.CastContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    companion object {
        const val CHANNEL_ID = "music-app"
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
        val modules = listOf(repositoryModule,dataSourceModule, networkModule , viewModelModule)

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(modules)
        }
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Channel music",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.setSound(null, null)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(
                notificationChannel
            )
        }
    }
}