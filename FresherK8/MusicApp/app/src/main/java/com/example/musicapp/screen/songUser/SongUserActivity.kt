package com.example.musicapp.screen.songUser

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.databinding.ActivitySongDownBinding
import com.example.musicapp.screen.explore.adapter.SongAgainAdapter
import com.example.musicapp.screen.main.MainActivity
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.adapter.SongDetailAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_NAME_TAB
import com.example.musicapp.shared.utils.constant.Constant.KEY_SONG_LOCAL
import com.example.musicapp.shared.utils.constant.Constant.LOCAL
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Random

class SongUserActivity : AppCompatActivity() {
    private val viewModel: SongUserViewModel by viewModel()
    private val adapter = SongDetailAdapter(::onItemClick)
    private val songAgainAdapter = SongAgainAdapter(::onItemClick, 0)
    private var mSongs = arrayListOf<Song>()
    private var songsAgain = mutableListOf<SongAgain>()
    private var value: String? = null
    private var title : String? = null
    private val binding by lazy {
        ActivitySongDownBinding.inflate(layoutInflater)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
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
        getValueIntent()
        initViewModel()
        handleEvent()
        showLoading()
    }

    private fun getValueIntent() {
        value = intent.getStringExtra(Constant.KEY_INTENT_ITEM)
        val name = intent.getStringExtra(Constant.KEY_NAME)
        binding.tvTitleSongUser.text = name
        when(value){
            Constant.DOWN -> {
                viewModel.fetchSongLocal()
            }
            Constant.AGAIN -> {
                FirebaseAuth.getInstance().currentUser?.uid?.let { viewModel.fetchSongAgain(it) }
            }
        }
        if (value != null) {
            setViewModel(value!!)
            initRecyclerview(value!!)
        }
       title = name
    }

    private fun showLoading() {
        binding.layoutSongUserLoading.visibility = View.VISIBLE
        binding.layoutSongUserEmpty.visibility = View.GONE
        binding.btnPlaySongUser.isEnabled = false
    }

    private fun initViewModel() {
        binding.songDownViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setViewModel(value : String) {
        when(value){
            Constant.DOWN -> {
                viewModel.songs.observe(this) {
                    handlerPostDelay {
                        binding.layoutSongUserLoading.visibility = View.GONE
                        if (it.isNotEmpty()) {
                            adapter.submitList(it)
                            mSongs = it
                            binding.btnPlaySongUser.isEnabled = true
                        } else {
                            binding.layoutSongUserEmpty.visibility = View.VISIBLE
                        }
                    }
                }
            }
           Constant.AGAIN -> {
                viewModel.songsAgain.observe(this) {
                    handlerPostDelay {
                        binding.layoutSongUserLoading.visibility = View.GONE
                        if (it.isNotEmpty()) {
                            songAgainAdapter.submitList(it.reversed()) // đảo ngược mảng
                            songsAgain = it.reversed() as MutableList<SongAgain>
                            binding.btnPlaySongUser.isEnabled = true
                        } else {
                            binding.layoutSongUserEmpty.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun handleEvent() {
        binding.btnBackSongDown.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.btnExplore.setOnClickListener { startExplore() }
        binding.btnPlaySongUser.setOnClickListener { startSong() }
    }

    private fun startSong() {
        val intent = Intent(this, SongActivity::class.java)
        sharedPreferences.edit().putBoolean(KEY_SONG_LOCAL, true).apply()
        intent.putExtra(Constant.KEY_POSITION_SONG, 0)
        intent.putParcelableArrayListExtra(Constant.KEY_INTENT_ITEM, mSongs)
        intent.putExtra(KEY_NAME_TAB, title)
        startActivity(intent)
    }

    private fun startExplore() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constant.KEY_SONG_USER, true)
        startActivity(intent)
    }

    private fun initRecyclerview(value : String) {
        when(value){
            Constant.DOWN -> {
                binding.rcvSongDown.setAdapterLinearVertical(adapter)
            }
            Constant.AGAIN -> {
                binding.rcvSongDown.setAdapterLinearVertical(songAgainAdapter)            }
        }
    }

    private fun onItemClick(any: Any, position: Int) {
        val intent = Intent(this, SongActivity::class.java)
        val  songs =  addListSong()

        when(value){
            Constant.DOWN -> {
                intent.putParcelableArrayListExtra(Constant.KEY_INTENT_ITEM, mSongs)
                sharedPreferences.edit().putBoolean(KEY_SONG_LOCAL, true).apply()
            }
            Constant.AGAIN -> {
                intent.putParcelableArrayListExtra(Constant.KEY_INTENT_ITEM, songs)
            }
        }

        intent.putExtra(KEY_NAME_TAB, title)
        intent.putExtra(Constant.KEY_POSITION_SONG, position)
        startActivity(intent)
    }

    private fun addListSong() : ArrayList<Song>{
        val songList  = arrayListOf<Song>()
        for (songAgain in songsAgain){
            val song = Song(
                0,
                songAgain.id,
                songAgain.name,
                songAgain.image,
                songAgain.url,
                songAgain.nameArtist,
                0
            )
            songList.add(song)
        }
        return songList
    }

    private fun handlerPostDelay(listener: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            listener.invoke()
        }, 500)
    }
}