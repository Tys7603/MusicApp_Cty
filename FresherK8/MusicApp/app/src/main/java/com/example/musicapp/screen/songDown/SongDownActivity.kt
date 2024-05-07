package com.example.musicapp.screen.songDown

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ActivitySongDownBinding
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.adapter.SongDetailAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import org.koin.androidx.viewmodel.ext.android.viewModel

class SongDownActivity : AppCompatActivity() {
    private val viewModel : SongDownViewModel by viewModel()
    private  val adapter = SongDetailAdapter(::onItemClick)

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
        initViewModel()
        setViewModel()
        handleEvent()
        initRecyclerview()
    }


    private fun initViewModel() {
        binding.songDownViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setViewModel() {
        viewModel.songsLocal.observe(this){
            adapter.submitList(it)
        }
    }

    private fun handleEvent() {
        binding.btnBackSongDown.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun initRecyclerview() {
        binding.rcvSongDown.setAdapterLinearVertical(adapter)
    }

    private fun onItemClick(songs: Song) {
        startActivity(Intent(this, SongActivity::class.java))
    }
}