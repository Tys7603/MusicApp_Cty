package com.example.musicapp.screen.music

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
import androidx.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.musicapp.R
import com.example.musicapp.shared.utils.constant.Constant.KEY_SONG
import com.example.musicapp.databinding.FragmentMusicBinding
import com.example.musicapp.data.model.Song
import com.example.musicapp.shared.utils.constant.Constant.KEY_PLAY_CLICK
import com.example.musicapp.screen.base.BaseService
import com.example.musicapp.screen.music.adapter.BottomSheetAddSongPlaylist
import com.example.musicapp.screen.user.adapter.BottomSheetPlaylist
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.DownloadMusic
import com.example.musicapp.shared.utils.constant.Constant.KEY_AUTO_RESTART
import com.example.musicapp.shared.utils.constant.Constant.KEY_DOWN
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION
import com.example.musicapp.shared.utils.constant.Constant.KEY_SHUFFLE
import com.example.musicapp.shared.utils.constant.Constant.VALUE_DEFAULT
import com.example.musicapp.shared.utils.format.FormatUtils
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicFragment : Fragment(), BaseService {

    private val viewModel: MusicViewModel by viewModel()

    private val binding by lazy {
        FragmentMusicBinding.inflate(layoutInflater)
    }

    private var musicService: MusicService? = null
    private var mSongs: ArrayList<Song>? = null
    private var mSongsLove: ArrayList<Song>? = null
    private var mSongsDefault: ArrayList<Song>? = null
    private var position = 0
    private var isServiceBound = false // kiểm tra kết nối service

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private val serviceConnection = object : ServiceConnection {
        // kết nối thành công lấy được đối tượng IBinder để try cập music service
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true
            musicService?.musicService(this@MusicFragment)
            musicService!!.musicShared(sharedPreferences)
        }

        // ngắt kết nối music service
        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handlerEvent()
        setUpViewModel()
        initViewModel()
    }

    private fun setUpViewModel() {
        binding.musicViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initViewModel() {
        viewModel.songs.observe(viewLifecycleOwner) {
            mSongs = it
            mSongsDefault = it
            initValueSong()
            if (isServiceBound) {
                initFunc()
            }
        }

        viewModel.songsLove.observe(viewLifecycleOwner) {
            mSongsLove = it
            checkSongLove()
        }

        viewModel.isAddSongLove.observe(viewLifecycleOwner) {
            if (it) {
                SnackBarManager.showMessage(binding.btnPlay, ADD_SONG_LOVE)
                binding.btnAddLove.setImageResource(R.drawable.ic_love_red)
            } else {
                SnackBarManager.showMessage(binding.btnPlay, DELETE_SONG_LOVE)
                binding.btnAddLove.setImageResource(R.drawable.ic_heart_black)
            }
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
    }

    private fun openBottomSheet() {
        position = sharedPreferences.getInt(KEY_POSITION, 0)
        val bottomSheet = BottomSheetAddSongPlaylist(mSongs!![position], binding.btnPlay)
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        bottomSheet.isCancelable = false
    }

    private fun checkUserLogin() {
        val user = FirebaseAuth.getInstance().currentUser
        position = sharedPreferences.getInt(KEY_POSITION, 0)
        if (user != null) {
            mSongsLove?.let { songsLoveList ->
                val songToCheck = mSongs!![position]
                val isSongInLoveList = isSongInList(songToCheck, songsLoveList)
                if (isSongInLoveList) {
                    val songLove = songsLoveList.find { it.id == songToCheck.id }
                    songLove?.let {
                        viewModel.deleteSongLove(it.songLoveId)
                    }
                } else {
                    viewModel.addSongLove(user.uid, mSongs!![position].id)
                }
            }
        } else {
            SnackBarManager.showMessage(binding.btnPlay, NOT_LOGIN)
        }
    }

    // hiển thị tên, ảnh bài hát, tên ca sĩ, bg
    private fun initValueSong() {
        position = sharedPreferences.getInt(KEY_POSITION, 0)
        mSongs?.get(position)?.let { binding.imgSong.loadImageUrl(it.image) }
        binding.tvNameArtistSong.text = mSongs?.get(position)?.nameArtis
        binding.tvNameSong.text = mSongs?.get(position)?.name
        binding.tvTotalTimeSong.text = VALUE_DEFAULT
        mSongs?.get(position)?.let { binding.imgBg.loadImageUrl(it.image) }

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
        checkSongLove()
    }

    private fun checkSongLove() {
        if (!mSongsLove.isNullOrEmpty() && !mSongs.isNullOrEmpty()) {
            if (isSongInList(mSongs!![position], mSongsLove!!)) {
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

    // phát nhạc
    private fun playMusic() {
        var isPlaySelected: Boolean by BooleanProperty(sharedPreferences, KEY_PLAY_CLICK, false)

        if (isServiceBound) { // kiểm tra đã kết nối chưa
            isPlaySelected = if (!musicService?.isPlaying()!!) { // kiểm tra xem đã play chưa
                musicService!!.start()
                updateTimeSong()
                binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
                true
            } else {
                musicService!!.pause()
                binding.btnPlay.setImageResource(R.drawable.ic_play_button)
                false
            }
            musicService!!.updateNotificationFromActivity()
        }
    }

    // next sang bài nhạc tiếp
    private fun nextMusic() {
        position = sharedPreferences.getInt(KEY_POSITION, 0)
        position++
        if (position > mSongs!!.size - 1) {
            position = 0
        }
        sharedPreferences.edit().putInt(KEY_POSITION, position).apply()
        setFuncMusic()
        musicService?.setNextMusic(true)
    }

    //    // quay lại bài nhạc
    private fun backMusic() {
        position--
        if (position < 0) {
            position = mSongs!!.size - 1
        }
        sharedPreferences.edit().putInt(KEY_POSITION, position).apply()
        setFuncMusic()
    }

    private fun setFuncMusic() {
        musicService?.stop()
        musicService?.setMediaPrepared(false)
        var isPlaySelected: Boolean by BooleanProperty(sharedPreferences, KEY_PLAY_CLICK, false)
        isPlaySelected = true
        initValueSong()
        checkSongLove()
    }

    // nghe lại bài nhạc
    private fun autoRestart() {
        if (musicService?.isAutoRestart()!!) {
            // auto restart tắt
            musicService!!.setAutoRestart(false)
            binding.btnLoop.setImageResource(R.drawable.ic_loop)
        } else {
            // auto restart bật
            musicService!!.setAutoRestart(true)
            binding.btnLoop.setImageResource(R.drawable.ic_loop_color)
//           // kiểm tra để dùng 1 chức năng
            if (musicService!!.isShuffleMusic()) {
                // shuffle tắt
                mSongs = mSongsDefault
                musicService!!.setShuffleMusic(false)
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
            }
        }
        sharedPreferences.edit().putBoolean(KEY_AUTO_RESTART, musicService!!.isAutoRestart())
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
            binding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
        } else {
            // shuffle bật
            mSongs = mSongsDefault?.toList() as ArrayList<Song>
            mSongs!!.shuffle()
            musicService?.setShuffleMusic(true)
            binding.btnShuffle.setImageResource(R.drawable.ic_shuffle_color)
//            // kiểm tra để dùng 1 chức năng
            if (musicService!!.isAutoRestart()) {
                // auto restart tắt
                musicService!!.setAutoRestart(false)
                binding.btnLoop.setImageResource(R.drawable.ic_loop)
            }
        }
        sharedPreferences.edit().putBoolean(KEY_SHUFFLE, musicService!!.isShuffleMusic()).apply()
    }

    // set thời gian tổng cho tv và gán max của skbar = time của bài hát
    private fun setTimeTotal() {
        if (isServiceBound) {
            binding.tvTotalTimeSong.text =
                musicService?.let { FormatUtils.formatTime(it.getDuration()) }
            // gán max cho skbar
            binding.seekBar.max = musicService!!.getDuration()
        }
    }

    private fun updateTimeSong() {
        // Tạo một Handler liên kết với Looper của luồng chính
        val handler = Handler(Looper.getMainLooper())

        // Đặt một hành động trì hoãn để cập nhật UI sau 100 mili giây
        handler.postDelayed({

            // Cập nhật UI với vị trí hiện tại của trình phát nhạc
            binding.tvTimeSong.text =
                musicService?.let { FormatUtils.formatTime(it.getCurrentPosition()) }

            // set progress cho seekbar
            binding.seekBar.progress = musicService?.getCurrentPosition() ?: 0

            // Đặt một hành động trì hoãn khác để gọi lại updateTimeSong sau 500 mili giây
            handler.postDelayed({ updateTimeSong() }, 500)

        }, 100)
    }

    private fun downloadMusic() {
        DownloadMusic.downloadMusic(requireContext(), mSongs!![position])
        Toast.makeText(requireContext(), KEY_DOWN, Toast.LENGTH_SHORT).show()
    }

    private fun saveSong() {
        val jsonSong = Gson().toJson(mSongs?.get(position))
        sharedPreferences.edit().putString(KEY_SONG, jsonSong).apply()
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

    // Kiểm tra xem một bài hát có nằm trong danh sách đã cho hay không
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
        val intent = Intent(activity, MusicService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        // khởi tạo view
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            // ngắt kiên kết
            requireContext().unbindService(serviceConnection)
            isServiceBound = false
        }

        mSongs = null
        mSongsDefault = null
        musicService = null
    }

    companion object {
        const val NOT_LOGIN = "Bạn chưa đăng nhập"
        const val ADD_SONG_LOVE = "Đã thêm vào bài hát yêu thích"
        const val DELETE_SONG_LOVE = "Đã xóa bài hát khỏi yêu thích"
    }
}

