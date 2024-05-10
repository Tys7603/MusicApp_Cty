package com.example.musicapp.shared.widget

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicapp.R
import com.example.musicapp.shared.utils.constant.Constant
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.utils.FadeViewHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBarListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class CustomPlayerUiController(
    controlsUi: View,
    private val youTubePlayer: YouTubePlayer,
    private val youTubePlayerView: YouTubePlayerView,
    private val mListener: (Constant.ClickControllerPlayerUi) -> Unit
) : AbstractYouTubePlayerListener() {
    private val playerTracker: YouTubePlayerTracker = YouTubePlayerTracker()
    private var isFullScreen = false

    init {
        youTubePlayer.addListener(playerTracker)
        initViews(controlsUi)
    }

    private fun initViews(view: View) {
        val container = view.findViewById<View>(R.id.controller)
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.root)
        val seekBar = view.findViewById<YouTubePlayerSeekBar>(R.id.btn_seekBar)
        val pausePlay = view.findViewById<ImageView>(R.id.btn_pause_play_controller)
        val fullScreen = view.findViewById<ImageView>(R.id.btn_full_screen)
        val backStack = view.findViewById<ImageView>(R.id.btn_on_back_music_detail)
        val backVideo = view.findViewById<ImageView>(R.id.btn_back_music_detail)
        val nextVideo = view.findViewById<ImageView>(R.id.btn_next_music_detail)
        youTubePlayer.addListener(seekBar)

        backStack.setOnClickListener { mListener.invoke(Constant.ClickControllerPlayerUi.ON_BACK) }
        backVideo.setOnClickListener { mListener.invoke(Constant.ClickControllerPlayerUi.ON_BACK_VIDEO) }
        nextVideo.setOnClickListener { mListener.invoke(Constant.ClickControllerPlayerUi.ON_NEXT_VIDEO) }

        seekBar.youtubePlayerSeekBarListener = object : YouTubePlayerSeekBarListener {
            override fun seekTo(time: Float) {
                youTubePlayer.seekTo(time)
            }
        }

        pausePlay.setOnClickListener {
            if (playerTracker.state == PlayerState.PLAYING) {
                pausePlay.setImageResource(R.drawable.ic_play)
                youTubePlayer.pause()
            } else {
                pausePlay.setImageResource(R.drawable.ic_pause)
                youTubePlayer.play()
            }
        }

        fullScreen.setOnClickListener {
            rotateScreen() // Xoay màn hình
            if (isFullScreen) {
                youTubePlayerView.wrapContent()
            } else {
                youTubePlayerView.matchParent()
            }
            isFullScreen = !isFullScreen // Đảo ngược trạng thái toàn màn hình
        }

        val fadeViewHelper = FadeViewHelper(container)
        fadeViewHelper.animationDuration = FadeViewHelper.DEFAULT_ANIMATION_DURATION
        fadeViewHelper.fadeOutDelay = FadeViewHelper.DEFAULT_FADE_OUT_DELAY
        youTubePlayer.addListener(fadeViewHelper)
        constraintLayout.setOnClickListener { fadeViewHelper.toggleVisibility() }
        container.setOnClickListener { fadeViewHelper.toggleVisibility() }
    }

    private fun rotateScreen() {
        val activity = youTubePlayerView.context as AppCompatActivity
        if (isFullScreen) {
            activity.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED // Chế độ dọc
        } else {
            activity.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // Chế độ ngang
        }
    }
}
