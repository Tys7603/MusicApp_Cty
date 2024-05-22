package com.example.musicapp.screen.song

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
import com.example.musicapp.screen.base.BaseService
import com.example.musicapp.screen.lyrics.LyricActivity
import com.example.musicapp.screen.music.MusicFragment.Companion.ADD_SONG_LOVE
import com.example.musicapp.screen.music.MusicViewModel
import com.example.musicapp.screen.music.adapter.BottomSheetAddSongPlaylist
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.DownloadMusic
import com.example.musicapp.shared.utils.OnChangeListener
import com.example.musicapp.shared.utils.constant.Constant.KEY_AUTO_RESTART
import com.example.musicapp.shared.utils.constant.Constant.KEY_DOWN
import com.example.musicapp.shared.utils.constant.Constant.KEY_HAVE_DOWN
import com.example.musicapp.shared.utils.constant.Constant.KEY_NAME_TAB
import com.example.musicapp.shared.utils.constant.Constant.KEY_PLAY_CLICK
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION_SONG
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION_SONG_LIST_NAME
import com.example.musicapp.shared.utils.constant.Constant.KEY_SHUFFLE
import com.example.musicapp.shared.utils.constant.Constant.VALUE_DEFAULT
import com.example.musicapp.shared.utils.constant.ManagerUrl.DELETE_SONG_LOVE
import com.example.musicapp.shared.utils.format.FormatUtils
import com.example.musicapp.shared.widget.ProgressBarManager
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class SongActivity : AppCompatActivity(), BaseService {

    private val viewModel: MusicViewModel by viewModel()
    private val binding by lazy {
        ActivitySongBinding.inflate(layoutInflater)
    }

    private var musicService: MusicService? = null
    private var mSongs: MutableList<Song> = mutableListOf()
    private var mSongsLove: MutableList<Song> = mutableListOf()
    private var mSongsDefault: MutableList<Song> = mutableListOf()
    private var position = 0
    private var isServiceBound = false // kiểm tra kết nối service

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
            initMusicView()
            getListSongIntent()
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
        setUpViewModel()
        initViewModel()
        enableButton(false)
        ProgressBarManager.showProgressBarPlay(
            binding.progressBarPlay,
            binding.layoutPlay,
            binding.btnPlay
        )
    }

    private fun initMusicView() {
        val isPlaying = sharedPreferences.getBoolean(KEY_PLAY_CLICK, false)
        if (isPlaying) {
            binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
        } else {
            binding.btnPlay.setImageResource(R.drawable.ic_play_button)
        }
    }

    private fun enableButton(boolean: Boolean) {
        with(binding) {
            btnShuffle.isEnabled = boolean
            btnNext.isEnabled = boolean
            btnBack.isEnabled = boolean
            btnLoop.isEnabled = boolean
            btnAddLove.isEnabled = boolean
            btnDownload.isEnabled = boolean
            btnLyrics.isEnabled = boolean
            seekBar.isEnabled = boolean
            btnAddPlaylist.isEnabled = boolean
        }
    }

    private fun setUpViewModel() {
        binding.musicViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initViewModel() {
        viewModel.songsLove.observe(this) {
            mSongsLove = it
            checkSongLove()
        }

        viewModel.isAddSongLove.observe(this) {
            if (it) {
                SnackBarManager.showMessage(binding.btnPlay, ADD_SONG_LOVE)
                binding.btnAddLove.setImageResource(R.drawable.ic_love_red)
            } else {
                SnackBarManager.showMessage(binding.btnPlay, DELETE_SONG_LOVE)
                binding.btnAddLove.setImageResource(R.drawable.ic_heart_black)
            }
            binding.btnAddLove.isEnabled = true
        }
    }

    private fun handlerEvent() {
        binding.btnPlay.setOnClickListener { playMusic() }
        binding.btnNext.setOnClickListener { nextMusic() }
        binding.btnBack.setOnClickListener { backMusic() }
        binding.btnLoop.setOnClickListener { autoRestart() }
        binding.btnShuffle.setOnClickListener { shuffleMusic() }
        binding.btnDownload.setOnClickListener { downloadMusic() }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) = Unit
            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (isServiceBound) {
                    musicService?.seekTo(binding.seekBar.progress)
                }
            }
        })
        binding.btnAddLove.setOnClickListener { checkUserLogin() }
        binding.btnAddPlaylist.setOnClickListener { openBottomSheet() }
        binding.btnLyrics.setOnClickListener { putLyrics() }
        binding.btnBackSong.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun getPosition(): Int {
        return  sharedPreferences.getInt(KEY_POSITION_SONG, 0)
    }

    private fun putPosition(position: Int) {
        sharedPreferences.edit().putInt(KEY_POSITION_SONG, position).apply()
    }

    private fun putLyrics() {
        position = getPosition()
        val intent = Intent(this, LyricActivity::class.java)
        intent.putExtra(Constant.KEY_INTENT_ITEM, mSongs.getOrNull(position))
        startActivity(intent)
    }

    private fun openBottomSheet() {
        position = getPosition()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val song = mSongs.getOrNull(position)
            song?.let {
                val bottomSheet = BottomSheetAddSongPlaylist(it, binding.btnPlay)
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        } else {
            openBottomSheetLogin()
        }
    }

    private fun openBottomSheetLogin(){
        val bottomSheetLogin = BottomSheetLogin()
        bottomSheetLogin.show(supportFragmentManager, bottomSheetLogin.tag)
    }

    private fun checkUserLogin() {
        val user = FirebaseAuth.getInstance().currentUser
        position = getPosition()
        if (user != null) {
            val songToCheck = getSongToCheckCurrent()
            if (songToCheck != null) {
                processSong(user.uid, songToCheck)
            }
        } else {
            openBottomSheetLogin()
        }
    }

    private fun getSongToCheckCurrent(): Song? {
        return if (position in mSongs.indices) {
            mSongs[position]
        } else {
            null
        }
    }

    private fun processSong(userId: String, songToCheck: Song) {
        val isSongInLoveList = isSongInList(songToCheck, mSongsLove)

        if (isSongInLoveList) {
            val songLove = mSongsLove.find { it.id == songToCheck.id }
            songLove?.let {
                viewModel.deleteSongLove(it.songLoveId)
            }
        } else {
            viewModel.addSongLove(userId, songToCheck.id)
        }
        binding.btnAddLove.isEnabled = false
    }

    // hiển thị tên, ảnh bài hát, tên ca sĩ, bg
    private fun initValueSong() {
        position = getPosition()
        val song = mSongs.getOrNull(position)

        if (song != null) {
            binding.song = song
            binding.tvTotalTimeSong.text = VALUE_DEFAULT
            binding.imgSong.loadImageUrl(song.image)
            binding.imgBg.loadImageUrl(song.image)

            if (isServiceBound) {
                val musicPrepared = checkMusicServiceToPlay(song)
                if (musicPrepared) {
                    // khi bài hát đã được chuẩn bị
                    setTimeTotal()
                    updateTimeSong()
                }
            }
            saveSong()
            initViewButton()
            enableButton(true)
            musicService?.setOnStartMusicListener { playMusic() }
        }
    }

    private fun checkMusicServiceToPlay(song: Song): Boolean {
        return if (!musicService?.isMediaPrepared()!!) {
            musicService?.songPlayFromUrl(song.url)
            musicService?.setOnCompletionListener {
                // Xử lý khi bài hát kết thúc, chuyển sang bài hát tiếp theo
                nextMusic()
            }
            false
        } else {
            true
        }
    }

    private fun checkSongLove() {
        if (position in mSongs.indices && mSongsLove.isNotEmpty()) {
            val song = mSongs[position]
            if (isSongInList(song, mSongsLove)) {
                binding.btnAddLove.setImageResource(R.drawable.ic_love_red)
            } else {
                binding.btnAddLove.setImageResource(R.drawable.ic_heart_black)
            }
        }
    }

    // kiểm tra xem nhạc có đang phát không -> hiển thị nút play, pause
    private fun initViewButton() {
        if (sharedPreferences.getBoolean(KEY_PLAY_CLICK, false)) {
            binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
        } else {
            binding.btnPlay.setImageResource(R.drawable.ic_play_button)
        }
    }

    private fun initFunc() {
        val shuffle = sharedPreferences.getBoolean(KEY_SHUFFLE, false)
        val autoRestart = sharedPreferences.getBoolean(KEY_AUTO_RESTART, false)
        if (shuffle) {
            binding.btnShuffle.setImageResource(R.drawable.ic_shuffle_color)
        }
        if (autoRestart) {
            binding.btnLoop.setImageResource(R.drawable.ic_loop_color)
        }
    }

    private fun playMusic() {
        var isPlaySelected: Boolean by BooleanProperty(sharedPreferences, KEY_PLAY_CLICK, false)

        if (isServiceBound) { // kiểm tra đã kết nối chưa
            isPlaySelected = if (!musicService?.isPlaying()!!) { // kiểm tra xem đã play chưa
                musicService?.start()
                updateTimeSong()
                binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
                true
            } else {
                musicService?.pause()
                binding.btnPlay.setImageResource(R.drawable.ic_play_button)
                false
            }
            musicService?.updateNotificationFromActivity()
        }
    }

    private fun nextMusic() {
        position = getPosition()
        position++
        if (position > mSongs.size - 1) {
            position = 0
        }
        putPosition(position)
        setFuncMusic()
        musicService?.setNextMusic(true)
        ProgressBarManager.showProgressBarPlay(
            binding.progressBarPlay,
            binding.layoutPlay,
            binding.btnPlay
        )
    }

    private fun backMusic() {
        position = getPosition()
        position--
        if (position < 0) {
            position = mSongs.size - 1
        }
        putPosition(position)
        setFuncMusic()
        ProgressBarManager.showProgressBarPlay(
            binding.progressBarPlay,
            binding.layoutPlay,
            binding.btnPlay
        )
    }

    private fun setFuncMusic() {
        musicService?.stop()
        musicService?.setMediaPrepared(false)
        sharedPreferences.edit().putBoolean(KEY_PLAY_CLICK, true).apply()
        initValueSong()
        checkSongLove()
        sharedPreferences.edit().putBoolean(Constant.KEY_LYRIC_NEW, true).apply()
        senBroadcastInitValueLyric()
    }

    private fun senBroadcastInitValueLyric() {
        val isCheck = sharedPreferences.getBoolean(Constant.KEY_ACTIVITY_LYRIC, false)
        if (isCheck) {
            val intent = Intent(Constant.UPDATE_LYRIC)
            intent.putExtra(Constant.KEY_INTENT_ITEM, sharedPreferences.getString(Constant.KEY_SONG, ""))
            sendBroadcast(intent)
        }
    }

    private fun autoRestart() {
        if (musicService?.isAutoRestart() == true) {
            // auto restart tắt
            musicService?.setAutoRestart(false)
            binding.btnLoop.setImageResource(R.drawable.ic_loop)
        } else {
            // auto restart bật
            musicService?.setAutoRestart(true)
            binding.btnLoop.setImageResource(R.drawable.ic_loop_color)
//           // kiểm tra để dùng 1 chức năng
            if (musicService?.isShuffleMusic() == true) {
                // shuffle tắt
                mSongs = mSongsDefault
                musicService?.setShuffleMusic(false)
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
            }
        }
        musicService?.isAutoRestart()?.let {
            sharedPreferences.edit().putBoolean(KEY_AUTO_RESTART, it)
                .apply()
        }
    }

    private fun shuffleMusic() {
        if (musicService?.isShuffleMusic() == true) {
            // shuffle tắt
            mSongs = mSongsDefault.toList() as ArrayList<Song>
            mSongs.clear()
            mSongs.addAll(mSongsDefault)
            musicService?.setShuffleMusic(false)
            binding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
        } else {
            // shuffle bật
            mSongs = mSongsDefault.toList() as ArrayList<Song>
            mSongs.shuffle()
            musicService?.setShuffleMusic(true)
            binding.btnShuffle.setImageResource(R.drawable.ic_shuffle_color)
//            // kiểm tra để dùng 1 chức năng
            if (musicService?.isAutoRestart() == true) {
                // auto restart tắt
                musicService?.setAutoRestart(false)
                binding.btnLoop.setImageResource(R.drawable.ic_loop)
            }
        }
        musicService?.isShuffleMusic()
            ?.let { sharedPreferences.edit().putBoolean(KEY_SHUFFLE, it).apply() }
    }

    private fun setTimeTotal() {
        if (isServiceBound) {
            binding.tvTotalTimeSong.text =
                musicService?.let { FormatUtils.formatTime(it.getDuration()) }
            // gán max cho skbar
            binding.seekBar.max = musicService?.getDuration()!!
            ProgressBarManager.dismissProgressBarPlay(
                binding.progressBarPlay,
                binding.layoutPlay,
                binding.btnPlay
            )
        }
    }

    private fun updateTimeSong() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.tvTimeSong.text =
                musicService?.let { FormatUtils.formatTime(it.getCurrentPosition()) }
            binding.seekBar.progress = musicService?.getCurrentPosition() ?: 0
            handler.postDelayed({ updateTimeSong() }, 500)
        }, 100)
    }

    private fun downloadMusic() {
        mSongs.getOrNull(position)?.let { DownloadMusic.downloadMusic(this, it) }
        Toast.makeText(this, KEY_DOWN, Toast.LENGTH_SHORT).show()
    }

    private fun saveSong() {
        val jsonSong = Gson().toJson(mSongs.getOrNull(position))
        sharedPreferences.edit().putString(Constant.KEY_SONG, jsonSong).apply()
    }

    private fun getListSongIntent() {
        val songs = intent.getParcelableArrayListExtra<Song>(Constant.KEY_INTENT_ITEM)
        val mPosition = intent.getIntExtra(KEY_POSITION_SONG, 0)
        if (songs != null){
            mSongs = songs
            mSongsDefault = songs
            sharedPreferences.edit().putBoolean(Constant.KEY_TAB_MUSIC, true).apply()
            sharedPreferences.edit().putInt(Constant.KEY_POSITION_TAB, 1).apply()
            sharedPreferences.edit().putString(Constant.KEY_LIST_SONG, Gson().toJson(songs)).apply()
            sharedPreferences.edit().putInt(KEY_POSITION_SONG, mPosition).apply()
            binding.tvTitleSong.text = sharedPreferences.getString(KEY_NAME_TAB, "")
            setFuncMusic()
            initFunc()
        }
    }

    override fun onMediaPrepared() {
        musicService?.let {
            setTimeTotal()
            if (it.isNextMusic()) {
                it.start()
                updateTimeSong()
            }
        }
    }

    private fun isSongInList(song: Song, list: List<Song>): Boolean {
        return list.any { it.id == song.id }
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
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            // ngắt kiên kết
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }
}