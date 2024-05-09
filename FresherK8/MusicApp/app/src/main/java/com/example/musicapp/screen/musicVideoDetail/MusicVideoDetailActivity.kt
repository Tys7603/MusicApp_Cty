package com.example.musicapp.screen.musicVideoDetail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
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
import com.example.musicapp.shared.widget.CustomPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicVideoDetailActivity : AppCompatActivity() {
    private val viewModel: MusicVideoViewModel by viewModel()
    private val musicVideoAdapter = MusicVideoAdapter(::onClickItem)
    private var mMusicVideo: MusicVideo? = null
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

        initViewModel()
        getBundlerValue()
        initRecyclerView()
        handlerEventViewModel()
        initPlayerYoutube()

    }

    private fun getBundlerValue() {
        val musicVideo = intent.getParcelableExtra<MusicVideo>(Constant.KEY_INTENT_ITEM)
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
        val customPlayerUi: View =
            binding.youtubePlayerView.inflateCustomPlayerUi(R.layout.layout_controller_music_video)

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val customPlayerUiController = CustomPlayerUiController(
                    customPlayerUi,
                    youTubePlayer,
                    binding.youtubePlayerView
                )
                youTubePlayer.addListener(customPlayerUiController)
                mMusicVideo?.let {
                    youTubePlayer.loadOrCueVideo(
                        lifecycle,
                        mMusicVideo!!.musicVideoId,
                        0F
                    )
                }
            }
        }

        val options = IFramePlayerOptions
            .Builder()
            .controls(0)
            .ivLoadPolicy(3)
            .fullscreen(1)
            .build()
        binding.youtubePlayerView.initialize(listener, options)
    }

    private fun onClickItem(musicVideo: MusicVideo) {
        binding.musicVideo = musicVideo

        binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.pause()
                mMusicVideo?.let {
                    youTubePlayer.loadOrCueVideo(
                        lifecycle,
                        musicVideo.musicVideoId,
                        0F
                    )
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
        mMusicVideo = null
    }
}