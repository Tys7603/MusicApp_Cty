package com.example.musicapp.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
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
        Log.d("TAG", "onDestroy: ")
    }
}