package com.example.musicapp.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.musicapp.R
import com.example.musicapp.presentation.main.MainActivity
import com.example.musicapp.presentation.music.MusicContract


class MusicService() : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder: IBinder = LocalBinder()
    private var isMediaPrepared = false // Biến này để theo dõi trạng thái chuẩn bị âm thanh
    private lateinit var mView: MusicContract.View
    private var onCompletionListener: (() -> Unit)? = null // kết thúc bài hát
    private var isAutoRestart = false //  lập lại bài hát
    private var isNext = false //  qua bài mới
    private var isShuffle= false //  đảo bài hài

    companion object{
        const val CHANNEL_ID = "zxcv"
        const val NOTIFICATION_ID = 0
    }

    fun musicService(mView : MusicContract.View){
        this.mView = mView
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setOnCompletionListener {
                    if (isAutoRestart){
                        start()
                    }else{
                        onCompletionListener?.invoke()
                    }
                }
            }
        }
    }

    // Trong phương thức onStartCommand(Intent, Int, Int)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "PLAY" -> {
                if (mediaPlayer?.isPlaying == false) {
                    start()
                }
            }
            "PAUSE" -> {
                if (mediaPlayer?.isPlaying == true) {
                    pause()
                }
            }
            // Xử lý các hành động khác (ví dụ: Next, Previous) tương tự
        }
        return START_NOT_STICKY
    }

    // Phương thức để tạo notification
    @SuppressLint("ForegroundServiceType", "NotificationId0")
    private fun createNotification() {
        // Tạo Intent để mở Activity khi notification được nhấn
        val notificationIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
//
//        // Tạo các action cho notification (Play, Pause)
//        val playIntent = Intent(this, MusicService::class.java).apply {
//            action = "PLAY"
//        }
//        val playPendingIntent = PendingIntent.getService(this, 0, playIntent, 0)
//
//        val pauseIntent = Intent(this, MusicService::class.java).apply {
//            action = "PAUSE"
//        }
//        val pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, 0)

        // Xây dựng notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.avatar)
            .setContentTitle("Music Player")
            .setContentText("Now playing")
//            .setContentIntent(pendingIntent)
//            .addAction(R.drawable.ic_play, "Play", playPendingIntent)
//            .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)
            .build()

        // Hiển thị notification
        startForeground(NOTIFICATION_ID, notification)
    }

    fun playFromUrl(url: String) {
        mediaPlayer?.apply {
            reset()
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                isMediaPrepared = true // Đánh dấu rằng âm thanh đã được chuẩn bị
                mView.onMediaPrepared()
                createNotification()
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
    fun setOnCompletionListener(listener: () -> Unit){
       onCompletionListener = listener
    }

    fun isAutoRestart() = isAutoRestart

    fun setAutoRestart(isAutoRestart : Boolean) {
        this.isAutoRestart = isAutoRestart
    }

    fun isNextMusic() = isNext

    fun setNextMusic(isNext : Boolean) {
        this.isNext = isNext
    }

    fun isShuffleMusic() = isShuffle

    fun setShuffleMusic(isShuffle : Boolean) {
        this.isShuffle = isShuffle
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