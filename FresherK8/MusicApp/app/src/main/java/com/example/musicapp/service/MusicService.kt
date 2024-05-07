package com.example.musicapp.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ServiceInfo
import android.graphics.Bitmap
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.transition.Transition
import com.example.musicapp.shared.utils.di.MyApplication
import com.example.musicapp.R
import com.example.musicapp.screen.main.MainActivity
import com.example.musicapp.screen.base.MusicContract
import com.example.musicapp.shared.utils.GetValue
import java.io.File


class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder: IBinder = LocalBinder()
    private var isMediaPrepared = false // Biến này để theo dõi trạng thái chuẩn bị âm thanh
    private var mView: MusicContract.View? = null

    private var mShared: SharedPreferences? = null
    private var onCompletionListener: (() -> Unit)? = null // kết thúc bài hát
    private var isAutoRestart = false //  lập lại bài hát
    private var isNext = false //  qua bài mới
    private var isShuffle = false //  đảo bài hài

    companion object {
        const val NOTIFICATION_ID = 1
        const val ACTION_PLAY = "Play"
        const val ACTION_NEXT = "Next"
        const val ACTION_BACK = "Back"
    }

    fun musicService(mView: MusicContract.View) {
        this.mView = mView
    }

    fun musicShared(sharedPreferences: SharedPreferences) {
        this.mShared = sharedPreferences
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
            ACTION_PLAY -> {
                mView?.onPlayMusic()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    createNotification()
                }
            }

            ACTION_NEXT -> {
                mView?.onNextMusic()
            }

            ACTION_BACK -> {
                mView?.onBackMusic()
            }
        }
        return START_NOT_STICKY
    }

    // Phương thức để tạo notification
    @SuppressLint("NotificationId0","ForegroundServiceType")
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createNotification() {
        // Tạo Intent để mở Activity khi notification được nhấn
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        // Tạo các action cho notification (Play, Pause, Next)
        val playIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_PLAY
        }
        val nextIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_NEXT
        }
        val backIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_BACK
        }
        val playPendingIntent =
            PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)
        val nextPendingIntent =
            PendingIntent.getService(this, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)
        val backPendingIntent =
            PendingIntent.getService(this, 0, backIntent, PendingIntent.FLAG_IMMUTABLE)
        // tạo action icon
        val playActionIcon =
            if (mediaPlayer!!.isPlaying) R.drawable.ic_pause_ else R.drawable.ic_play_black
//         Hiển thị notification
        val song = mShared?.let { GetValue.getSong(it) }
        Glide.with(this)
            .asBitmap()
            .load(song?.image)
            .into(object : com.bumptech.glide.request.target.SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    // Khi ảnh đã được tải xong, đặt bitmap làm LargeIcon cho notification
                    val notification =
                        NotificationCompat.Builder(this@MusicService, MyApplication.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_avatar)
                            .setContentTitle(song?.name)
                            .setContentText(song?.nameArtis)
                            .setLargeIcon(resource) // Đặt bitmap làm LargeIcon
                            .setContentIntent(pendingIntent)
                            .setSound(null)
                            .addAction(R.drawable.ic_skip_back, "Back", backPendingIntent)
                            .addAction(playActionIcon, "Play", playPendingIntent)
                            .addAction(R.drawable.ic_skip_next, "Next", nextPendingIntent)
                            .setStyle(
                                androidx.media.app.NotificationCompat.MediaStyle()
                                    .setShowActionsInCompactView(1)
                                    .setMediaSession(
                                        MediaSessionCompat(
                                            this@MusicService,
                                            "tag"
                                        ).sessionToken
                                    )
                            )
                            .build()
                    startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
                }
            })
    }

    fun playFromUrl(url: String) {
        mediaPlayer?.apply {
            reset()
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                isMediaPrepared = true // Đánh dấu rằng âm thanh đã được chuẩn bị
                mView?.onMediaPrepared()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    createNotification()
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun playFromLocal(fileName: String) {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        val fullPath = File(directory, fileName).absolutePath
        mediaPlayer?.apply {
            reset()
            setDataSource(fullPath)
            prepareAsync()
            setOnPreparedListener {
                isMediaPrepared = true
                mView?.onMediaPrepared()
            }
        }
    }

    fun updateNotificationFromActivity(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            createNotification()
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

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.release()
        mediaPlayer = null
        mView = null
        mShared = null

        Log.d("TAG", "onDestroy: chạy")
    }
}