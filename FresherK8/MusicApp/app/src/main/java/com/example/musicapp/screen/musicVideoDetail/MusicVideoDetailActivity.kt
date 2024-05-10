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
import com.example.musicapp.shared.widget.SnackBarManager
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
    private var mMusicVideosDefault: ArrayList<MusicVideo>? = null
    private var mMusicVideosSublist: ArrayList<MusicVideo>? = null
    private var positionMusicVideo = -1
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
        intent.getParcelableExtra<MusicVideo>(Constant.KEY_INTENT_ITEM)?.let {
            binding.musicVideo = it
            mMusicVideo = it
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
            mMusicVideosDefault = it
            mMusicVideosSublist = itemEqualListMusicVideo(it)
            musicVideoAdapter.submitList(mMusicVideosSublist)
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
                    binding.youtubePlayerView,
                    ::onClickController
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
        checkItemEqualMusicVideosDefault(musicVideo)
    }

    private fun checkItemEqualMusicVideosDefault(musicVideo: MusicVideo){
        binding.musicVideo = musicVideo
        mMusicVideo = musicVideo
        mMusicVideosDefault?.let { musicVideos ->
            val updatedList = itemEqualListMusicVideo(ArrayList(musicVideos))
            mMusicVideosSublist = updatedList
            musicVideoAdapter.submitList(mMusicVideosSublist)
            getYouTubePlayerWhenReady(musicVideo)
        }
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
                }
            }
        })
    }

    private fun itemEqualListMusicVideo(musicVideos: ArrayList<MusicVideo>) : ArrayList<MusicVideo>{
        val matchList = ArrayList(musicVideos)
        mMusicVideo?.musicVideoId?.let { musicVideoId ->
            for (musicVideo in musicVideos){
                if (musicVideoId == musicVideo.musicVideoId){
                    matchList.remove(musicVideo)
                    break
                }
            }
        }
        return matchList
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
        //test
        if (positionMusicVideo > mMusicVideosSublist!!.size){
            SnackBarManager.showMessage(binding.imageView13, "End Video")
            return
        }
        getYouTubePlayerWhenReady(mMusicVideosSublist!![positionMusicVideo])
        checkItemEqualMusicVideosDefault(mMusicVideosSublist!![positionMusicVideo])
    }

    private fun backVideoMusic(){
        positionMusicVideo --
        // test
        if (positionMusicVideo == 1){
            SnackBarManager.showMessage(binding.imageView13, "Firt Video")
            return
        }
        getYouTubePlayerWhenReady(mMusicVideosSublist!![positionMusicVideo])
        checkItemEqualMusicVideosDefault(mMusicVideosSublist!![positionMusicVideo])
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
        mMusicVideo = null
        mMusicVideosDefault = null
        mMusicVideosSublist = null
    }
}