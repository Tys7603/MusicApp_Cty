package com.example.musicapp.screen.songDown

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ActivitySongDownBinding
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.adapter.SongDetailAdapter
import com.example.musicapp.shared.utils.OnItemClickListener

class SongDownActivity : AppCompatActivity(), SongDownContract.View, OnItemClickListener {

    private val binding by lazy {
        ActivitySongDownBinding.inflate(layoutInflater)
    }

    private val mPresenter by lazy {
        SongDownPresenter()
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

        handleEvent()
    }

    private fun handleEvent() {
        binding.btnBackSongDown.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    override fun onStart() {
        super.onStart()
        mPresenter.run {
            setView(this@SongDownActivity)
            getListSong(this@SongDownActivity)
        }
    }

    override fun onListSong(songs: ArrayList<Song>) {
        val adapter = SongDetailAdapter(songs, this)
        binding.rcvSongDown.layoutManager = LinearLayoutManager(this)
        binding.rcvSongDown.adapter = adapter
    }

    override fun onItemClick(item: Any) {
        startActivity(Intent(this, SongActivity::class.java))
    }
}