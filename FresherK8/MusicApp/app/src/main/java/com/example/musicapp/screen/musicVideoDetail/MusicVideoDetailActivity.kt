package com.example.musicapp.screen.musicVideoDetail

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.databinding.ActivityMusicVideoDetailBinding
import com.example.musicapp.screen.musicVideo.MusicVideoViewModel
import com.example.musicapp.screen.musicVideo.adapter.MusicVideoAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class MusicVideoDetailActivity : AppCompatActivity() {
    private val viewModel: MusicVideoViewModel by viewModel()
    private val musicVideoAdapter = MusicVideoAdapter(::onClickItem)
    private var mMusicVideo : MusicVideo? = null
    private val binding by lazy {
        ActivityMusicVideoDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        initViewModel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getBundlerValue()
        }
//        initRecyclerView()
//        handlerEventViewModel()
        initPlayerYoutube()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getBundlerValue() {
        val bundle = intent.getBundleExtra(Constant.KEY_BUNDLE_ITEM)
        val musicVideo = bundle?.getParcelable(Constant.KEY_INTENT_ITEM, MusicVideo::class.java)
        binding.musicVideo = musicVideo
        mMusicVideo = musicVideo
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        lifecycle.addObserver(binding.youtubePlayerView)
    }

    private fun initRecyclerView() {
        binding.rcvMusicVideoDetail.setAdapterLinearVertical(musicVideoAdapter)
    }

    private fun handlerEventViewModel() {
        viewModel.musicVideos.observe(this) {
            musicVideoAdapter.submitList(it.shuffled())
        }
    }

    private fun initPlayerYoutube() {
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                mMusicVideo?.let { youTubePlayer.loadVideo(it.musicVideoId, 0f) }
            }
        })
    }

    private fun onClickItem(musicVideo: MusicVideo) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mMusicVideo = null
    }
}