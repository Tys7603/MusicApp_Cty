package com.example.musicapp.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.musicapp.until.Constant


class MusicService() : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder: IBinder = LocalBinder()
    private var isMediaPrepared = false // Biến này để theo dõi trạng thái chuẩn bị âm thanh

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

        }
    }


    fun playFromUrl(url: String) {
        mediaPlayer?.apply {
            reset()
            setDataSource(url)
            prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                isMediaPrepared = true // Đánh dấu rằng âm thanh đã được chuẩn bị
            }
        }
    }

    fun start(){
        mediaPlayer?.start()
    }

    fun isMediaPrepared(): Boolean {
        return isMediaPrepared
    }

    fun setMediaPrepared( mediaPrepared: Boolean) {
        isMediaPrepared = mediaPrepared
    }
    fun setOnCompletionListener(){
        mediaPlayer?.setOnCompletionListener {
            sendBroadcast(Intent(Constant.SONG_COMPLETED))
            Log.d("TAG", "onReceive: nhạn dc ở service")
        }
    }


    fun isPlaying() = mediaPlayer!!.isPlaying

    fun pause() {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration!!
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer!!.currentPosition
    }

    fun seekTo(position: Int) {
        mediaPlayer!!.seekTo(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.release()
    }
}