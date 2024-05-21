package com.example.musicapp.screen.exploreDetail

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.musicapp.R
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ActivityExploreDetailBinding
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.screen.explore.ExploreViewModel
import com.example.musicapp.screen.explore.adapter.AlbumAdapter
import com.example.musicapp.screen.explore.adapter.CategoriesAdapter
import com.example.musicapp.screen.explore.adapter.PlayListAdapter
import com.example.musicapp.screen.explore.adapter.SongAgainAdapter
import com.example.musicapp.screen.explore.adapter.SongRankAdapter
import com.example.musicapp.screen.explore.adapter.TopicAdapterLinear
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.screen.topic.TopicActivity
import com.example.musicapp.shared.extension.setAdapterGrid
import com.example.musicapp.shared.extension.setAdapterGridVertical
import com.example.musicapp.shared.extension.setAdapterLinearHorizontal
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.ListDefault
import com.example.musicapp.shared.utils.constant.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Random

class ExploreDetailActivity : AppCompatActivity() {
    private val viewModel: ExploreViewModel by viewModel()
    private val binding by lazy {
        ActivityExploreDetailBinding.inflate(layoutInflater)
    }
    private val playListAdapter = PlayListAdapter(::onItemClick, 0)
    private val playListMoodAdapter = PlayListAdapter(::onItemClick, 0)
    private val categoriesAdapter = CategoriesAdapter(::onItemClick, 0)
    private val albumNewAdapter = AlbumAdapter(::onItemClick, 0)
    private val albumLoveAdapter = AlbumAdapter(::onItemClick, 0)

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
        initValue()
        handelEvent()
        handelViewModel()
    }

    private fun initValue() {
        val name = intent.getStringExtra(Constant.KEY_NAME)
        val nameData = intent.getStringExtra(Constant.KEY_INTENT_ITEM)
        binding.tvTitleDetail.text = name
        nameData?.let { setViewModel(nameData) }
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setViewModel(nameData: String) {
        when(nameData){
            Constant.PLAYLIST -> {
                binding.rcvExploreDetail.setAdapterGridVertical(playListAdapter)
                playListAdapter.submitList(ListDefault.initListPlaylist())
                playListAdapter.setEnableItem(false)
            }
            Constant.CATEGORIES -> {
                binding.rcvExploreDetail.setAdapterLinearVertical(categoriesAdapter)
                categoriesAdapter.submitList(ListDefault.initListCategories())
                categoriesAdapter.setEnableItem(false)
            }
            Constant.MOOD_TODAY -> {
                binding.rcvExploreDetail.setAdapterGridVertical(playListMoodAdapter)
                playListMoodAdapter.submitList(ListDefault.initListPlaylist())
                playListMoodAdapter.setEnableItem(false)
            }
            Constant.ALBUM_NEW -> {
                binding.rcvExploreDetail.setAdapterGridVertical(albumNewAdapter)
                albumNewAdapter.submitList(ListDefault.initListAlbum())
                albumNewAdapter.setEnableItem(false)
            }
            Constant.ALBUM_LOVE -> {
                binding.rcvExploreDetail.setAdapterGridVertical(albumLoveAdapter)
                albumLoveAdapter.submitList(ListDefault.initListAlbum())
                albumLoveAdapter.setEnableItem(false)
            }
        }
    }

    private fun handelViewModel() {
        viewModel.playlist.observe(this) {
            handlerPostDelay {
                playListAdapter.submitList(it.shuffled(Random()) as ArrayList<Playlist>)
                playListAdapter.setEnableItem(true)
            }
        }

        viewModel.playlistsMood.observe(this) {
            handlerPostDelay {
                playListMoodAdapter.submitList(it.shuffled(Random()) as ArrayList<Playlist>)
                playListMoodAdapter.setEnableItem(true)
            }
        }

        viewModel.categories.observe(this) {
            handlerPostDelay {
                categoriesAdapter.submitList(it.shuffled(Random()) as ArrayList<Category>)
                categoriesAdapter.setEnableItem(true)
            }
        }

        viewModel.albumLove.observe(this) {
            handlerPostDelay {
                albumLoveAdapter.submitList(it.shuffled() as ArrayList<Album>)
                albumLoveAdapter.setEnableItem(true)
            }
        }

        viewModel.albumNew.observe(this) {
            handlerPostDelay {
                albumNewAdapter.submitList(it.shuffled() as ArrayList<Album>)
                albumNewAdapter.setEnableItem(true)
            }
        }
    }

    private fun onItemClick(item: Any) {

        when (item) {
            is Playlist -> {
                val intent = Intent(this, SongDetailActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, item)
                startActivity(intent)
            }

            is Category -> {
                val intent = Intent(this, TopicActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, item)
                startActivity(intent)
            }

            is Topic -> {
                val intent = Intent(this, SongDetailActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, item)
                startActivity(intent)
            }

            is SongAgain -> {
                val intent = Intent(this, SongActivity::class.java)
                startActivity(intent)
            }

            is Album -> {
                val intent = Intent(this, SongDetailActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, item)
                startActivity(intent)
            }
        }
    }

    private fun handelEvent() {
        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun handlerPostDelay(listener : () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed({
            listener.invoke()
        }, 500)
    }
}