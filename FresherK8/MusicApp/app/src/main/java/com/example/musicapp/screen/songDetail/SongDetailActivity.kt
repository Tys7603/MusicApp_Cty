package com.example.musicapp.screen.songDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.musicapp.R
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ActivitySongDetailBinding
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.adapter.SongDetailAdapter
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant.KEY_INTENT_ITEM
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION_SONG
import org.koin.androidx.viewmodel.ext.android.viewModel

class SongDetailActivity : AppCompatActivity() {

    private val viewModel: SongDetailViewModel by viewModel()
    private val songAdapter = SongDetailAdapter(::onItemClick)
    private val binding by lazy {
        ActivitySongDetailBinding.inflate(layoutInflater)
    }
    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private var mSongs : ArrayList<Song>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initValue()
        handleEvent()
        initViewModel()
        initRecyclerView()
        handleEventViewModel()
    }

    private fun handleEventViewModel() {
        viewModel.songTopic.observe(this){
            songAdapter.submitList(it)
            mSongs = it
            initQuantitySong(it)
        }
        viewModel.songPlaylist.observe(this){
            songAdapter.submitList(it)
            mSongs = it
            initQuantitySong(it)
        }
        viewModel.songAlbum.observe(this){
            songAdapter.submitList(it)
            mSongs = it
            initQuantitySong(it)
        }
    }

    private fun initViewModel() {
        binding.songDetailViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        binding.rcvPlaylistActivity.setAdapterLinearVertical(songAdapter)
        binding.rcvPlaylistActivity.isNestedScrollingEnabled = false
    }

    private fun handleEvent() {
        binding.imgBackPlaylistActivity.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initValue() {
        when(val item = intent.getParcelableExtra<Parcelable>(KEY_INTENT_ITEM)){
            is Playlist -> {
                item.image.let { binding.imgSongActivity.loadImageUrl(it) }
                item.image.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.name
                binding.tvNameArtistPlaylistActivity.text = item.nameArtist
                viewModel.fetchSongPlaylist(item.id)
                sharedPreferences.edit().putString(Constant.KEY_NAME_TAB, item.name).apply()
            }

            is Album -> {
                item.albumImage.let { binding.imgSongActivity.loadImageUrl(it) }
                item.albumImage.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.albumName
                binding.tvNameArtistPlaylistActivity.text = item.nameArtist
                viewModel.fetchSongAlbum(item.albumId)
                sharedPreferences.edit().putString(Constant.KEY_NAME_TAB, item.albumImage).apply()
            }

            is Topic -> {
                item.image.let { binding.imgSongActivity.loadImageUrl(it) }
                item.image.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.name
                binding.tvNameArtistPlaylistActivity.text = ""
                viewModel.fetchSongTopic(item.id)
                sharedPreferences.edit().putString(Constant.KEY_NAME_TAB, item.name).apply()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initQuantitySong(songs: ArrayList<Song>){
        val quantity = if (songs.isNotEmpty()) {
            songs.size
        } else {
            0
        }
        binding.tvQuantitySongPlaylistActivity.text = "$quantity $SONG"
    }

    companion object{
        const val SONG = " bài hát"
    }

    private fun onItemClick(song: Song, position : Int) {
        val intent = Intent(this, SongActivity::class.java)
        intent.putExtra(KEY_POSITION_SONG, position)
        intent.putParcelableArrayListExtra(KEY_INTENT_ITEM, mSongs)
        startActivity(intent)
    }

}