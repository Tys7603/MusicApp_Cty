package com.example.musicapp.screen.songDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
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
import com.example.musicapp.shared.utils.DownloadMusic
import com.example.musicapp.shared.utils.constant.Constant.KEY_INTENT_ITEM
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION_SONG
import com.example.musicapp.shared.widget.SnackBarManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Random

class SongDetailActivity : AppCompatActivity() {

    private val viewModel: SongDetailViewModel by viewModel()
    private val songAdapter = SongDetailAdapter(::onItemClick)
    private val binding by lazy {
        ActivitySongDetailBinding.inflate(layoutInflater)
    }
    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private var mSongs: ArrayList<Song>? = arrayListOf()
    private var mPlaylist: Playlist? = null

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
        viewModel.songTopic.observe(this) {
            songAdapter.submitList(it)
            mSongs = it
            initQuantitySong(it)
        }
        viewModel.songPlaylist.observe(this) {
            songAdapter.submitList(it)
            mSongs = it
            initQuantitySong(it)
        }
        viewModel.songAlbum.observe(this) {
            songAdapter.submitList(it)
            mSongs = it
            initQuantitySong(it)
        }
        viewModel.isUserLogin.observe(this) {
            if (it) {
                viewModel.isInsertPlaylist.observe(this) { isInserted ->
                    val message = if (isInserted) {
                        "Đã thêm vào playlist yêu thích"
                    } else {
                        "Đã tồn tại trong playlist yêu thích"
                    }
                    SnackBarManager.showMessage(binding.btnAddPlaylistDetail, message)
                }
            } else {
                SnackBarManager.showMessage(binding.btnAddPlaylistDetail, "Bạn chưa đăng nhập")
            }
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
        binding.btnShuffleDetail.setOnClickListener { startShuffle() }
        binding.btnPlayPlaylistActivity.setOnClickListener { startSongMusic() }
        binding.btnAddPlaylistDetail.setOnClickListener {
            mPlaylist?.id?.let { viewModel.insertPlaylist(it) }
        }
        binding.btnDowPlaylistActivity.setOnClickListener {downloadListSong()}
    }

    private fun downloadListSong() {
        mSongs?.let {
            for (song in mSongs!!){
                DownloadMusic.downloadMusic(this, song)
            }
        }
        Toast.makeText(this, Constant.KEY_DOWN, Toast.LENGTH_SHORT).show()
    }

    private fun startSongMusic() {
        onStartPosition(0, false)
    }

    private fun startShuffle() {
        onStartPosition(0, true)
    }

    private fun initValue() {
        when (val item = intent.getParcelableExtra<Parcelable>(KEY_INTENT_ITEM)) {
            is Playlist -> {
                item.image.let { binding.imgSongActivity.loadImageUrl(it) }
                item.image.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.name
                binding.tvNameArtistPlaylistActivity.text = item.nameArtist
                viewModel.fetchSongPlaylist(item.id)
                sharedPreferences.edit().putString(Constant.KEY_NAME_TAB, item.name).apply()
                mPlaylist = item
            }

            is Album -> {
                binding.btnAddPlaylistDetail.visibility = View.GONE
                item.albumImage.let { binding.imgSongActivity.loadImageUrl(it) }
                item.albumImage.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.albumName
                binding.tvNameArtistPlaylistActivity.text = item.nameArtist
                viewModel.fetchSongAlbum(item.albumId)
                sharedPreferences.edit().putString(Constant.KEY_NAME_TAB, item.albumImage).apply()
            }

            is Topic -> {
                binding.btnAddPlaylistDetail.visibility = View.GONE
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
    private fun initQuantitySong(songs: ArrayList<Song>) {
        val quantity = if (songs.isNotEmpty()) {
            songs.size
        } else {
            0
        }
        binding.tvQuantitySongPlaylistActivity.text = "$quantity $SONG"
    }

    companion object {
        const val SONG = " bài hát"
    }

    private fun onItemClick(song: Song, position: Int) {
        onStartPosition(position, false)
    }

    private fun onStartPosition(position: Int, isShuffle: Boolean) {
        val intent = Intent(this, SongActivity::class.java)
        intent.putExtra(KEY_POSITION_SONG, position)
        if (isShuffle) intent.putParcelableArrayListExtra(
            KEY_INTENT_ITEM,
            mSongs?.shuffled(Random()) as ArrayList<Song>
        )
        else intent.putParcelableArrayListExtra(KEY_INTENT_ITEM, mSongs)
        startActivity(intent)
    }
}