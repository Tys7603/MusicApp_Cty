package com.example.musicapp.screen.songDetail

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_BUNDLE_ITEM
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ActivitySongDetailBinding
import com.example.musicapp.shared.extension.loadImageUrl

class SongDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySongDetailBinding.inflate(layoutInflater)
    }

    private val mPresenter by lazy {
        SongListPresenter()
    }
    private var mSong : ArrayList<Song>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            initValue()
        }

        handleEvent()
    }

    private fun handleEvent() {
        binding.imgBackPlaylistActivity.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initValue() {
        val bundle = intent.getBundleExtra(KEY_BUNDLE_ITEM)
        when(val item = bundle?.getParcelable(Constant.KEY_INTENT_ITEM, Parcelable::class.java)){

            is Playlist -> {
                item.image.let { binding.imgSongActivity.loadImageUrl(it) }
                item.image.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.name
                binding.tvNameArtistPlaylistActivity.text = item.nameArtist
//                mPresenter.getListSongPlaylist(item.id)
            }

            is Album -> {
                item.albumImage.let { binding.imgSongActivity.loadImageUrl(it) }
                item.albumImage.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.albumName
                binding.tvNameArtistPlaylistActivity.text = item.nameArtist
//                mPresenter.getListSongPlaylist(item.albumId)
            }

            is Topic -> {
                item.image.let { binding.imgSongActivity.loadImageUrl(it) }
                item.image.let { binding.imgBgPlaylistActivity.loadImageUrl(it) }
                binding.tvNamePlaylistActivity.text = item.name
                binding.tvNameArtistPlaylistActivity.text = ""
//                mPresenter.getListSongTopic(item.id)
            }
        }
    }


//    @SuppressLint("SetTextI18n")
//    override fun onListSong(songs: ArrayList<Song>) {
//        val adapter = SongDetailAdapter(songs, this)
//        binding.rcvPlaylistActivity.layoutManager = LinearLayoutManager(this)
//        binding.rcvPlaylistActivity.adapter = adapter
//        binding.tvQuantitySongPlaylistActivity.text = songs.size.toString() + SONG
//        binding.rcvPlaylistActivity.isNestedScrollingEnabled = false
//        mSong = songs
//    }
//
//    companion object{
//        const val SONG = " bài hát"
//    }
//
//    override fun onItemClick(item: Any) {
//        val intent = Intent(this, SongActivity::class.java)
//        intent.putExtra(ConstantBase.KEY_POSITION_SONG, item as Int)
//        intent.putParcelableArrayListExtra(KEY_INTENT_ITEM, mSong)
//        startActivity(intent)
//    }

}