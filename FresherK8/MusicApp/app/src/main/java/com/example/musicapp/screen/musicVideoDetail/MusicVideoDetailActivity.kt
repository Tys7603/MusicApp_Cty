package com.example.musicapp.screen.musicVideoDetail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R

class MusicVideoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_music_video_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //    private var youTubePlayer: YouTubePlayer? = null
//    private val playerListener = object : AbstractYouTubePlayerListener() {
//        override fun onReady(youTubePlayer: YouTubePlayer) {
//            this@ViewHolder.youTubePlayer = youTubePlayer
//            // Bắt đầu phát video khi trình phát video đã sẵn sàng
//            binding.musicVideo?.musicVideoId?.let { this@ViewHolder.youTubePlayer?.cueVideo(it, 0F) }
//        }
//    }
//
//    init {
//        binding.youtubePlayerView.addYouTubePlayerListener(playerListener)
//        binding.youtubePlayerView.setOnClickListener { Log.d("TAG", " chuyển video ") }
//    }
//
//    fun releasePlayer() {
//        binding.youtubePlayerView.release()
//    }

}