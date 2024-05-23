package com.example.musicapp.screen.lyrics

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ActivityLyricBinding
import com.example.musicapp.screen.lyrics.adapter.LyricsAdapter
import com.example.musicapp.screen.main.MainActivity
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_REFRESH_LYRIC
import com.example.musicapp.shared.widget.ProgressBarManager
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class LyricActivity : AppCompatActivity() {
    private var handler: Handler? = null
    private var updateRunnable: Runnable? = null
    private val viewModel: LyricViewModel by viewModel()
    private var isServiceBound = false // kiểm tra kết nối service
    private var musicService: MusicService? = null
    private var lyricsAdapter = LyricsAdapter()
    private var currentLyricIndex = 0
    private var isLyricNew = false

    private val binding by lazy {
        ActivityLyricBinding.inflate(layoutInflater)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    private val serviceConnection = object : ServiceConnection {
        // kết nối thành công lấy được đối tượng IBinder để try cập music service
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true
            syncLyricsWithMusic()
        }

        // ngắt kết nối music service
        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
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
        initSharedPreferences()
        initValue()
        initViewModel()
        initRecyclerView()
        handlerViewModel()
        setOnCompleteListener()
        handlerEvent()
    }

    private fun initSharedPreferences() {
        isLyricNew = sharedPreferences.getBoolean(Constant.KEY_LYRIC_NEW, false)

        if (isLyricNew) {
            sharedPreferences.edit().putBoolean(Constant.KEY_LYRIC_NEW, false).apply()
            sharedPreferences.edit().putInt(Constant.KEY_LYRIC, 0).apply()
        }
        currentLyricIndex = sharedPreferences.getInt(Constant.KEY_LYRIC, 0)
    }

    private fun initValue() {
        val songJson = intent.getStringExtra(Constant.KEY_INTENT_ITEM)
        val song = Gson().fromJson(songJson, Song::class.java)
        if (song != null) {
            binding.song = song
            binding.bgImgLyric.loadImageUrl(song.image)
            binding.imgLyric.loadImageUrl(song.image)
            viewModel.fetchLyrics(song.id)
            ProgressBarManager.showProgressBar(binding.progressBar, binding.linearLayout7)
        }
        sharedPreferences.edit().putBoolean(Constant.KEY_ACTIVITY_LYRIC, true).apply()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        binding.rcvLyric.setAdapterLinearVertical(lyricsAdapter)
    }

    private fun handlerEvent() {
        binding.btnClose.setOnClickListener { onBackStack() }
    }

    private fun onBackStack() {
        val str = intent.getStringExtra(KEY_REFRESH_LYRIC)

        val intent = when (str) {
            "Fragment" -> Intent(this, MainActivity::class.java)
            "Activity" -> Intent(this, SongActivity::class.java)
            else -> {
                onBackPressedDispatcher.onBackPressed()
                return
            }
        }

        sharedPreferences.edit().putBoolean(Constant.KEY_ACTIVITY_LYRIC, false).apply()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    private fun handlerViewModel() {
        viewModel.lyrics.observe(this) {
            if (it.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                lyricsAdapter.submitList(it)
                binding.tvEmpty.visibility = View.GONE
            }
            ProgressBarManager.dismissProgressBar(binding.progressBar, binding.linearLayout7)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setOnCompleteListener() {
        musicService?.setOnCompletionListener {
            currentLyricIndex = 0
            lyricsAdapter.highlightedPosition = -1
            lyricsAdapter.notifyDataSetChanged()
        }
    }

    fun syncLyricsWithMusic() {
        val handler = Handler(Looper.getMainLooper())
        val updateRunnable = object : Runnable {
            override fun run() {
                val currentPosition = musicService?.getCurrentPosition()?.toLong()
                if (currentPosition != null) {
                    // Tìm vị trí lời bài hát tương ứng với thời gian hiện tại
                    val newLyricIndex = findLyricIndex(currentPosition)

                    if (newLyricIndex != currentLyricIndex) {
                        currentLyricIndex = newLyricIndex
                        highlightText()
                        scrollToPositionHighlightText()
                    }
                }
                handler.postDelayed(this, 100)
            }

        }
        handler.post(updateRunnable)
    }

    private fun findLyricIndex(currentPosition: Long): Int {
        for (i in lyricsAdapter.currentList.indices) {
            if (currentPosition < lyricsAdapter.currentList[i].startTimeMs) {
                return i - 1
            }
        }
        return lyricsAdapter.currentList.size - 1
    }

    private fun highlightText() {
        lyricsAdapter.notifyItemChanged(lyricsAdapter.highlightedPosition)
        lyricsAdapter.highlightedPosition = currentLyricIndex
        lyricsAdapter.notifyItemChanged(currentLyricIndex)
    }

    private fun scrollToPositionHighlightText() {
        val layoutManager = binding.rcvLyric.layoutManager as LinearLayoutManager
        if (currentLyricIndex > 1) {
            layoutManager.scrollToPositionWithOffset(currentLyricIndex - 2, 0)
        }
    }


    private fun registerMusicService() {
        val intent = Intent(this, MusicService::class.java)
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStart() {
        super.onStart()
        registerMusicService()
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences.edit().putInt(Constant.KEY_LYRIC, currentLyricIndex)
            .apply()
    }

    override fun onBackPressed() {
        onBackStack()
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
        musicService = null
        updateRunnable?.let { handler?.removeCallbacks(it) }
    }
}