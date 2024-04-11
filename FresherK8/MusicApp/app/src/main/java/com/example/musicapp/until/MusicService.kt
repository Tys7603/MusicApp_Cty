package com.example.musicapp.until

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.io.IOException


class MusicService: Service() {

    private lateinit var mediaPlayer : MediaPlayer
    private val binder: IBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: ")
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            // Thêm sự kiện lắng nghe để xử lý khi phát nhạc kết thúc
            setOnCompletionListener {
                Toast.makeText(this@MusicService, "Nhạc kết thúc.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    fun play(url: String){
        if (!mediaPlayer.isPlaying){
            try {
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Đã xảy ra lỗi khi phát nhạc", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun pause(){
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }
    }

    fun stop (){
        mediaPlayer.stop()
        mediaPlayer.prepareAsync()
    }

    fun getDuration(): Int {
        return mediaPlayer.duration
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    fun seekTo(position: Int) {
        mediaPlayer.seekTo(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }
}