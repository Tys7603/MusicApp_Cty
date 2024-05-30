package com.example.musicapp.shared.utils

import android.util.Log
import com.pierfrancescosoffritti.androidyoutubeplayer.chromecast.chromecastsender.ChromecastYouTubePlayerContext
import com.pierfrancescosoffritti.androidyoutubeplayer.chromecast.chromecastsender.io.infrastructure.ChromecastConnectionListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class SimpleChromecastConnectionListener : ChromecastConnectionListener {
    override fun onChromecastConnecting() {
        Log.d(javaClass.getSimpleName(), "onChromecastConnecting")
    }

    override fun onChromecastConnected(chromecastYouTubePlayerContext: ChromecastYouTubePlayerContext) {
        Log.d(javaClass.getSimpleName(), "onChromecastConnected")
        initializeCastPlayer(chromecastYouTubePlayerContext)
    }

    override fun onChromecastDisconnected() {
        Log.d(javaClass.getSimpleName(), "onChromecastDisconnected")
    }

    private fun initializeCastPlayer(chromecastYouTubePlayerContext: ChromecastYouTubePlayerContext) {
        chromecastYouTubePlayerContext.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo("S0Q4gqBUs7c", 0f)
            }
        })
    }
}

