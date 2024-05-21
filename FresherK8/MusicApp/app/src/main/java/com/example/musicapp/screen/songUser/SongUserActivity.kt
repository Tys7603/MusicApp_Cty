package com.example.musicapp.screen.songUser

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ActivitySongDownBinding
import com.example.musicapp.screen.explore.adapter.SongAgainAdapter
import com.example.musicapp.screen.main.MainActivity
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.adapter.SongDetailAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class SongUserActivity : AppCompatActivity() {
    private val viewModel: SongUserViewModel by viewModel()
    private val adapter = SongDetailAdapter(::onItemClick)
    private val songAgainAdapter = SongAgainAdapter(::onItemClick)

    private val binding by lazy {
        ActivitySongDownBinding.inflate(layoutInflater)
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
        val value = intent.getStringExtra(Constant.KEY_INTENT_ITEM)
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
            setViewModel(value)
            initRecyclerview(value)
        }
    }

    private fun showLoading() {
        binding.layoutSongUserLoading.visibility = View.VISIBLE
        binding.layoutSongUserEmpty.visibility = View.GONE
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
                            songAgainAdapter.submitList(it)
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
        startActivity(Intent(this, SongActivity::class.java))
    }

    private fun handlerPostDelay(listener: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            listener.invoke()
        }, 1000)
    }
}