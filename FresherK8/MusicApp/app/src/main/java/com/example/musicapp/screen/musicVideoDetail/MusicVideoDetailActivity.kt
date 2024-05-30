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
import com.example.musicapp.screen.musicVideoDetail.adapter.MusicVideoDetailAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.widget.CustomPlayerUiController
import com.example.musicapp.shared.widget.SnackBarManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicVideoDetailActivity : AppCompatActivity() {
    private val viewModel: MusicVideoDetailViewModel by viewModel()
    private val musicVideoAdapter = MusicVideoDetailAdapter(::onClickItem)
    private var mMusicVideo: MusicVideo? = null
    private var mMusicVideos: MutableList<MusicVideo> = mutableListOf()
    private var positionMusicVideo = 0
    private var customPlayerUiController : CustomPlayerUiController? = null
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
        handlerEvent()
        getBundlerValue()
        initRecyclerView()
        handlerEventViewModel()
        initPlayerYoutube()
        showLayout(true)
    }

    private fun handlerEvent() {
        binding.btnFollow.setOnClickListener { SnackBarManager.showMessage(binding.imageView13, "Tính năng phá triển sau") }
    }

    private fun getBundlerValue() {
        intent.getParcelableExtra<MusicVideo>(Constant.KEY_INTENT_ITEM)?.let {
            binding.musicVideo = it
            mMusicVideo = it
            viewModel.fetchMusicVideoDetail(it.musicVideoId)
        }
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
            val modifiedList = it.toMutableList()
            musicVideoAdapter.submitList(modifiedList)
            mMusicVideos = it
            mMusicVideos.add(0, mMusicVideo!!)
        }
    }

    private fun initPlayerYoutube() {
        val customPlayerUi: View =
            binding.youtubePlayerView.inflateCustomPlayerUi(R.layout.layout_controller_music_video)

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                customPlayerUiController = CustomPlayerUiController(
                    customPlayerUi,
                    youTubePlayer,
                    binding.youtubePlayerView,
                    ::onClickController
                )
                youTubePlayer.addListener(customPlayerUiController!!)
                mMusicVideo?.let {
                    youTubePlayer.loadOrCueVideo(
                        lifecycle,
                        mMusicVideo!!.musicVideoId,
                        0F
                    )
                    showLayout(false)
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

    private fun onClickItem(musicVideo: MusicVideo, position : Int) {
        showLayout(true)
        getYouTubePlayerWhenReady(musicVideo)
        positionMusicVideo = position
        customPlayerUiController!!.updateUI()
    }

    private fun getYouTubePlayerWhenReady(musicVideo: MusicVideo){
        binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.pause()
                musicVideo.musicVideoId.let {
                    youTubePlayer.loadOrCueVideo(
                        lifecycle,
                        it,
                        0F
                    )
                    showLayout(false)
                }

            }
        })
        binding.musicVideo = musicVideo
        mMusicVideo = musicVideo
    }

    private fun onClickController(enum : Constant.ClickControllerPlayerUi){
        when(enum){
            Constant.ClickControllerPlayerUi.ON_BACK -> {
                onBackPressedDispatcher.onBackPressed()
            }
            Constant.ClickControllerPlayerUi.ON_NEXT_VIDEO -> {
                nextVideoMusic()
            }
            Constant.ClickControllerPlayerUi.ON_BACK_VIDEO -> {
                backVideoMusic()
            }
        }
    }

    private fun nextVideoMusic(){
        positionMusicVideo ++
        if (positionMusicVideo >= mMusicVideos.size){
           positionMusicVideo = 0
        }
        mMusicVideos.getOrNull(positionMusicVideo)?.let { getYouTubePlayerWhenReady(it) }
        customPlayerUiController!!.updateUI()
    }

    private fun backVideoMusic(){
        positionMusicVideo --
        if (positionMusicVideo < 0){
           positionMusicVideo = mMusicVideos.size - 1
        }
        getYouTubePlayerWhenReady(mMusicVideos[positionMusicVideo])
        customPlayerUiController!!.updateUI()
    }

    private fun showLayout(isShow : Boolean){
        if (isShow){
            binding.includeLayoutMvDetail.visibility = View.VISIBLE
            binding.layoutMvDetail.visibility = View.GONE
        }else{
            binding.includeLayoutMvDetail.visibility = View.GONE
            binding.layoutMvDetail.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
        mMusicVideo = null
    }
}