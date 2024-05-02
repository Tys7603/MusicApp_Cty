package com.example.musicapp.presentation.music

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.musicapp.R
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ActivitySongBinding
import com.example.musicapp.presentation.music.base.ConstantBase
import com.example.musicapp.presentation.music.base.ConstantBase.KEY_AUTO_RESTART
import com.example.musicapp.presentation.music.base.ConstantBase.KEY_PLAY_CLICK
import com.example.musicapp.presentation.music.base.ConstantBase.KEY_POSITION_SONG
import com.example.musicapp.presentation.music.base.ConstantBase.KEY_SHUFFLE
import com.example.musicapp.presentation.music.base.ConstantBase.VALUE_DEFAULT
import com.example.musicapp.presentation.music.base.MusicContract
import com.example.musicapp.presentation.music.base.MusicPresenter
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.DownloadMusic
import com.example.musicapp.shared.utils.format.FormatUtils
import com.google.gson.Gson

class SongActivity : AppCompatActivity(), MusicContract.View {

    private val binding by lazy {
        ActivitySongBinding.inflate(layoutInflater)
    }

    private var musicService: MusicService? = null
    private var mSongs: ArrayList<Song>? = null
    private var mSongsDefault: ArrayList<Song>? = null
    private var position = 0
    private var isServiceBound = false // kiểm tra kết nối service
    private val mPresenter by lazy {
        MusicPresenter()
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
            musicService?.musicService(this@SongActivity)
            musicService!!.musicShared(sharedPreferences)
            setFuncMusic()
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

        handlerEvent()
    }

    private fun handlerEvent() {
        binding.btnPlayAt.setOnClickListener { playMusic() }
        binding.btnNextAt.setOnClickListener { nextMusic() }
        binding.btnBackAt.setOnClickListener { backMusic() }
        binding.btnLoopAt.setOnClickListener { autoRestart() }
        binding.btnShuffleAt.setOnClickListener { shuffleMusic() }
        binding.btnDownloadAt.setOnClickListener { downloadMusic() }
        binding.imgBackSongAt.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.seekBarAt.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (isServiceBound) {
                    musicService?.seekTo(binding.seekBarAt.progress)
                }
            }
        })
    }

    // hiển thị tên, ảnh bài hát, tên ca sĩ, bg
    private fun initValueSong() {
        position = sharedPreferences.getInt(KEY_POSITION_SONG, 0)
        mSongs?.get(position)?.let { binding.imgSongAt.loadImageUrl(it.image) }
        binding.tvNameArtistSongAt.text = mSongs?.get(position)?.nameArtis
        binding.tvNameSongAt.text = mSongs?.get(position)?.name
        binding.tvTotalTimeSongAt.text = VALUE_DEFAULT
        mSongs?.get(position)?.let { binding.imgBgSongAt.loadImageUrl(it.image) }
        if (isServiceBound) {
            if (!musicService?.isMediaPrepared()!!) {
                mSongs?.get(position)?.let { musicService?.playFromUrl(it.url) }
                musicService?.setOnCompletionListener {
                    // Xử lý khi bài hát kết thúc, chuyển sang bài hát tiếp theo
                   nextMusic()
                }
            } else {
                // khi bài hát đã được chuẩn bị -> khi bấm qua fragment khác
                setTimeTotal()
                updateTimeSong()
            }
        }
        saveSong()
        initViewButton()
        initFunc()
    }

    // kiểm tra xem nhạc có đang phát không -> hiển thị nút play, pause
    private fun initViewButton() {
        if (sharedPreferences.getBoolean(KEY_PLAY_CLICK, false)) {
            binding.btnPlayAt.setImageResource(R.drawable.ic_pause_music)
        } else {
            binding.btnPlayAt.setImageResource(R.drawable.ic_play_button)
        }
    }

    private fun initFunc() {
        val shuffle = sharedPreferences.getBoolean(KEY_SHUFFLE, false)
        val autoRestart = sharedPreferences.getBoolean(KEY_AUTO_RESTART, false)
        if (shuffle) {
            binding.btnShuffleAt.setImageResource(R.drawable.ic_shuffle_color)
        }
        if (autoRestart) {
            binding.btnLoopAt.setImageResource(R.drawable.ic_loop_color)
        }
    }

    // phát nhạc
    private fun playMusic() {
        var isPlaySelected: Boolean by BooleanProperty(
            sharedPreferences,
            KEY_PLAY_CLICK,
            false
        )

        if (isServiceBound) { // kiểm tra đã kết nối chưa
            isPlaySelected = if (!musicService?.isPlaying()!!) { // kiểm tra xem đã play chưa
                musicService!!.start()
                updateTimeSong()
                binding.btnPlayAt.setImageResource(R.drawable.ic_pause_music)
                true
            } else {
                musicService!!.pause()
                binding.btnPlayAt.setImageResource(R.drawable.ic_play_button)
                false
            }
            musicService!!.updateNotificationFromActivity()
        }
    }

    // next sang bài nhạc tiếp
    private fun nextMusic() {
        position = sharedPreferences.getInt(KEY_POSITION_SONG, 0)
        position++
        if (position > mSongs!!.size - 1) {
            position = 0
        }
        sharedPreferences.edit().putInt(KEY_POSITION_SONG, position).apply()
        setFuncMusic()
        musicService?.setNextMusic(true)
    }

    //    // quay lại bài nhạc
    private fun backMusic() {
        position--
        if (position < 0) {
            position = mSongs!!.size - 1
        }
        sharedPreferences.edit().putInt(KEY_POSITION_SONG, position).apply()
        setFuncMusic()
    }

    private fun setFuncMusic() {
        musicService?.stop()
        musicService?.setMediaPrepared(false)
        var isPlaySelected: Boolean by BooleanProperty(
            sharedPreferences,
            KEY_PLAY_CLICK,
            false
        )
        isPlaySelected = true
        initValueSong()
    }

    // nghe lại bài nhạc
    private fun autoRestart() {
        if (musicService?.isAutoRestart()!!) {
            // auto restart tắt
            musicService!!.setAutoRestart(false)
            binding.btnLoopAt.setImageResource(R.drawable.ic_loop)
        } else {
            // auto restart bật
            musicService!!.setAutoRestart(true)
            binding.btnLoopAt.setImageResource(R.drawable.ic_loop_color)
//           // kiểm tra để dùng 1 chức năng
            if (musicService!!.isShuffleMusic()) {
                // shuffle tắt
                mSongs = mSongsDefault
                musicService!!.setShuffleMusic(false)
                binding.btnShuffleAt.setImageResource(R.drawable.ic_shuffle)
            }
        }
        sharedPreferences.edit()
            .putBoolean(KEY_AUTO_RESTART, musicService!!.isAutoRestart())
            .apply()
    }

    // nghe ngẫu nhiên
    private fun shuffleMusic() {
        if (musicService?.isShuffleMusic() == true) {
            // shuffle tắt
            mSongs = mSongsDefault?.toList() as ArrayList<Song>
            mSongs!!.clear()
            mSongs!!.addAll(mSongsDefault!!)
            musicService!!.setShuffleMusic(false)
            binding.btnShuffleAt.setImageResource(R.drawable.ic_shuffle)
        } else {
            // shuffle bật
            mSongs = mSongsDefault?.toList() as ArrayList<Song>
            mSongs!!.shuffle()
            musicService?.setShuffleMusic(true)
            binding.btnShuffleAt.setImageResource(R.drawable.ic_shuffle_color)
//            // kiểm tra để dùng 1 chức năng
            if (musicService!!.isAutoRestart()) {
                // auto restart tắt
                musicService!!.setAutoRestart(false)
                binding.btnLoopAt.setImageResource(R.drawable.ic_loop)
            }
        }

        sharedPreferences.edit()
            .putBoolean(KEY_SHUFFLE, musicService!!.isShuffleMusic()).apply()
    }

    // set thời gian tổng cho tv và gán max của skbar = time của bài hát
    private fun setTimeTotal() {
        if (isServiceBound) {
            binding.tvTotalTimeSongAt.text =
                musicService?.let { FormatUtils.formatTime(it.getDuration()) }
            // gán max cho skbar
            binding.seekBarAt.max = musicService!!.getDuration()
        }
    }

    private fun updateTimeSong() {
        // Tạo một Handler liên kết với Looper của luồng chính
        val handler = Handler(Looper.getMainLooper())

        // Đặt một hành động trì hoãn để cập nhật UI sau 100 mili giây
        handler.postDelayed({

            // Cập nhật UI với vị trí hiện tại của trình phát nhạc
            binding.tvTimeSongAt.text =
                musicService?.let { FormatUtils.formatTime(it.getCurrentPosition()) }

            // set progress cho seekbar
            binding.seekBarAt.progress = musicService?.getCurrentPosition() ?: 0

            // Đặt một hành động trì hoãn khác để gọi lại updateTimeSong sau 500 mili giây
            handler.postDelayed({ updateTimeSong() }, 500)

        }, 100)
    }

    private fun downloadMusic() {
        if (mSongs?.get(position)?.download == 0) {
            position = sharedPreferences.getInt(KEY_POSITION_SONG, 0)
            mSongs!![position].download = 1
            DownloadMusic.downloadMusic(this, mSongs!![position])
            Toast.makeText(this, ConstantBase.KEY_DOWN, Toast.LENGTH_SHORT).show()
            // hàm update download
        } else {
            Toast.makeText(this, ConstantBase.KEY_HAVE_DOWN, Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveSong() {
        val jsonSong = Gson().toJson(mSongs?.get(position))
        sharedPreferences.edit().putString(Constant.KEY_SONG, jsonSong).apply()
    }

    private fun getListSongIntent() {
        val songs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(Constant.KEY_INTENT_ITEM, Song::class.java)
        } else {
            null
        }
        val mPosition = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getIntExtra(KEY_POSITION_SONG, 0)
        } else {
            0
        }
        sharedPreferences.edit().putInt(KEY_POSITION_SONG, mPosition).apply()
        mSongs = songs
        mSongsDefault = songs
    }

    override fun onListSong(songs: ArrayList<Song>) = Unit

    override fun onMediaPrepared() {
        musicService?.let {
            setTimeTotal()
            if (it.isNextMusic()) {
                it.start()
                updateTimeSong()
            }
        }
    }

    override fun onNextMusic() {
        nextMusic()
    }

    override fun onBackMusic() {
        backMusic()
    }

    override fun onPlayMusic() {
        playMusic()
    }

    override fun onStart() {
        super.onStart()
        // khởi tạo và liên kết tới music service
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        // khởi tạo view
        mPresenter.run {
            setView(this@SongActivity)
            getListSongIntent()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            // ngắt kiên kết
            unbindService(serviceConnection)
            isServiceBound = false
        }

        mPresenter.onDestroy()
        mSongs = null
        mSongsDefault = null
        musicService = null
    }
}