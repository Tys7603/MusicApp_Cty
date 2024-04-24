package com.example.musicapp.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.musicapp.shared.extension.MyApplication
import com.example.musicapp.R
import com.example.musicapp.presentation.main.MainActivity
import com.example.musicapp.presentation.music.MusicContract


class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder: IBinder = LocalBinder()
    private var isMediaPrepared = false // Biến này để theo dõi trạng thái chuẩn bị âm thanh
    private lateinit var mView: MusicContract.View
    private var onCompletionListener: (() -> Unit)? = null // kết thúc bài hát
    private var isAutoRestart = false //  lập lại bài hát
    private var isNext = false //  qua bài mới
    private var isShuffle = false //  đảo bài hài

    companion object {
        const val NOTIFICATION_ID = 0
        const val ACTION_PAUSE = 1
        const val ACTION_START = 2
        const val ACTION_NEXT = 3
        const val ACTION_BACK = 4
    }

    fun musicService(mView: MusicContract.View) {
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
                    if (isAutoRestart) {
                        start()
                    } else {
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
        createNotification()
        return START_NOT_STICKY
    }

    // Phương thức để tạo notification
    @SuppressLint("ForegroundServiceType", "NotificationId0")
    private fun createNotification() {
        // Tạo Intent để mở Activity khi notification được nhấn
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
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
        // Hiển thị notification
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.song1)
        val notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_avatar)
            .setContentTitle("Chạy Ngay Đi")
            .setContentText("Sơn Tùng M-TP")
            .setLargeIcon(bitmap)
            .setContentIntent(pendingIntent)
            .setSound(null)
            .addAction(R.drawable.ic_skip_back, "Play", null)
            .addAction(R.drawable.ic_play_black, "Play", null)
            .addAction(R.drawable.ic_skip_next, "Play", null)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1)
                    .setMediaSession(MediaSessionCompat(this, "tag").sessionToken)
            )
            .build()
        startForeground(NOTIFICATION_ID, notification)
        Toast.makeText(this, " đã chạy", Toast.LENGTH_SHORT).show()
    }

    fun playFromUrl(url: String) {
        mediaPlayer?.apply {
            reset()
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                isMediaPrepared = true // Đánh dấu rằng âm thanh đã được chuẩn bị
                mView.onMediaPrepared()
            }
        }
    }

    fun start() {
        mediaPlayer?.start()
    }

    fun isMediaPrepared(): Boolean {
        return isMediaPrepared
    }

    fun setMediaPrepared(mediaPrepared: Boolean) {
        isMediaPrepared = mediaPrepared
    }

    fun setOnCompletionListener(listener: () -> Unit) {
        onCompletionListener = listener
    }

    fun isAutoRestart() = isAutoRestart

    fun setAutoRestart(isAutoRestart: Boolean) {
        this.isAutoRestart = isAutoRestart
    }

    fun isNextMusic() = isNext

    fun setNextMusic(isNext: Boolean) {
        this.isNext = isNext
    }

    fun isShuffleMusic() = isShuffle

    fun setShuffleMusic(isShuffle: Boolean) {
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
        mediaPlayer = null
    }
}