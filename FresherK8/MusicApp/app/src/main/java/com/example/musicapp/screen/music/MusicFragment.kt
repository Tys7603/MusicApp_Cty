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
import com.example.musicapp.screen.lyrics.LyricActivity
import com.example.musicapp.screen.music.adapter.BottomSheetAddSongPlaylist
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.DownloadMusic
import com.example.musicapp.shared.utils.OnChangeListener
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_AUTO_RESTART
import com.example.musicapp.shared.utils.constant.Constant.KEY_DOWN
import com.example.musicapp.shared.utils.constant.Constant.KEY_INTENT_ITEM
import com.example.musicapp.shared.utils.constant.Constant.KEY_LIST_SONG
import com.example.musicapp.shared.utils.constant.Constant.KEY_LYRIC_NEW
import com.example.musicapp.shared.utils.constant.Constant.KEY_NAME_TAB
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION_SONG
import com.example.musicapp.shared.utils.constant.Constant.KEY_POSITION_TAB
import com.example.musicapp.shared.utils.constant.Constant.KEY_SHUFFLE
import com.example.musicapp.shared.utils.constant.Constant.KEY_TAB_MUSIC
import com.example.musicapp.shared.utils.constant.Constant.VALUE_DEFAULT
import com.example.musicapp.shared.utils.format.FormatUtils
import com.example.musicapp.shared.widget.ProgressBarManager
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicFragment : Fragment(), BaseService {

    private val viewModel: MusicViewModel by viewModel()

    private val binding by lazy {
        FragmentMusicBinding.inflate(layoutInflater)
    }
    private var musicService: MusicService? = null
    private var mSongs: MutableList<Song> = mutableListOf()
    private var mSongsLove: MutableList<Song> = mutableListOf()
    private var mSongsDefault: MutableList<Song> = mutableListOf()
    private var position = 0
    private var isServiceBound = false // kiểm tra kết nối service
    private var listener: OnChangeListener? = null

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnChangeListener) {
            listener = context
        }
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
            musicService?.musicShared(sharedPreferences)
            checkTab()
            initMusicView()
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
            btnMusicPlaylist.isEnabled = boolean
            seekBar.isEnabled = boolean
            btnAddPlaylist.isEnabled = boolean
        }
    }

    private fun checkTab() {
        /**
         * 0 - vị trí của dành cho bạn
         * 1 - vị trí của playlist
         */
        val isCheck = sharedPreferences.getInt(KEY_POSITION_TAB, 0)

        when (isCheck) {
            0 -> {
                viewModel.songs.observe(viewLifecycleOwner) {
                    mSongs = it
                    mSongsDefault = it
                    initValueSong()
                    if (isServiceBound) {
                        initFunc()
                    }
                    binding.btnMusicPlaylist.setTextColor(resources.getColor(R.color.black_mix))
                    binding.btnMusicMe.setTextColor(resources.getColor(R.color.black))
                    sharedPreferences.edit().putInt(KEY_POSITION_TAB, 0).apply()
                }
            }

            1 -> {
                binding.btnMusicPlaylist.setTextColor(resources.getColor(R.color.black))
                binding.btnMusicMe.setTextColor(resources.getColor(R.color.black_mix))
                getListSongIntent()
                sharedPreferences.edit().putInt(KEY_POSITION_TAB, 1).apply()
            }
        }

        if (isCheckMusicTab()) {
            binding.btnMusicPlaylist.visibility = View.VISIBLE
            binding.btnMusicPlaylist.text = sharedPreferences.getString(KEY_NAME_TAB, "")
        }
    }

    private fun setUpViewModel() {
        binding.musicViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initViewModel() {
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
            binding.btnAddLove.isEnabled = true
        }
    }

    private fun isCheckMusicTab(): Boolean = sharedPreferences.getBoolean(KEY_TAB_MUSIC, false)

    private fun getListSongIntent() {
        sharedPreferences.edit().putInt(KEY_POSITION_TAB, 1).apply()
        val jsonSongs = sharedPreferences.getString(KEY_LIST_SONG, "")
        if (!jsonSongs.isNullOrEmpty()) {
            val myListType = object : TypeToken<ArrayList<Song>>() {}.type
            val songs: ArrayList<Song> = Gson().fromJson(jsonSongs, myListType)
            mSongs = songs
            mSongsDefault = songs
            initValueSong()
            if (isServiceBound) {
                initFunc()
            }
        }
    }

    private fun getPosition(): Int {
        /**
         * 0 - vị trí của dành cho bạn
         * 1 - vị trí của playlist
         */
        val isCheck = sharedPreferences.getInt(KEY_POSITION_TAB, 0)
        return if (isCheck == 0) {
            sharedPreferences.getInt(KEY_POSITION, 0)
        } else {
            sharedPreferences.getInt(KEY_POSITION_SONG, 0)
        }
    }

    private fun putPosition(position: Int) {
        /**
         * 0 - vị trí của dành cho bạn
         * 1 - vị trí của playlist
         */
        val isCheck = sharedPreferences.getInt(KEY_POSITION_TAB, 0)
        if (isCheck == 0) {
            sharedPreferences.edit().putInt(KEY_POSITION, position).apply()
        } else {
            sharedPreferences.edit().putInt(KEY_POSITION_SONG, position).apply()
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
        binding.btnMusicPlaylist.setOnClickListener {
            binding.btnMusicPlaylist.setTextColor(resources.getColor(R.color.yellow_mix))
            binding.btnMusicMe.setTextColor(resources.getColor(R.color.black))
            getListSongIntent()
            setFuncTabMusic()
            ProgressBarManager.showProgressBarPlay(
                binding.progressBarPlay,
                binding.layoutPlay,
                binding.btnPlay
            )
        }
        binding.btnMusicMe.setOnClickListener {
            viewModel.songs.observe(viewLifecycleOwner) {
                mSongs = it
                mSongsDefault = it
                initValueSong()
                if (isServiceBound) {
                    initFunc()
                }
                binding.btnMusicPlaylist.setTextColor(resources.getColor(R.color.black))
                binding.btnMusicMe.setTextColor(resources.getColor(R.color.yellow_mix))
                sharedPreferences.edit().putInt(KEY_POSITION_TAB, 0).apply()
                ProgressBarManager.showProgressBarPlay(
                    binding.progressBarPlay,
                    binding.layoutPlay,
                    binding.btnPlay
                )
                setFuncTabMusic()
            }
        }
    }

    private fun putLyrics() {
        position = getPosition()
        val intent = Intent(requireContext(), LyricActivity::class.java)
        intent.putExtra(KEY_INTENT_ITEM, mSongs.getOrNull(position))
        startActivity(intent)
    }

    private fun openBottomSheet() {
        position = getPosition()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val song = mSongs.getOrNull(position)
            song?.let {
                val bottomSheet = BottomSheetAddSongPlaylist(it, binding.btnPlay)
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
            }
        } else {
            openBottomSheetLogin()
        }
    }

    private fun openBottomSheetLogin(){
        val bottomSheetLogin = BottomSheetLogin()
        bottomSheetLogin.show(parentFragmentManager, bottomSheetLogin.tag)
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
                    // khi bài hát đã được chuẩn bị -> khi bấm qua fragment khác
                    setTimeTotal()
                    updateTimeSong()
                }
            }
            saveSong()
            initViewButton()
            checkSongLove()
            enableButton(true)
        }
    }

    private fun checkMusicServiceToPlay(song: Song): Boolean {
        return if (!musicService?.isMediaPrepared()!!) {
            musicService?.playFromUrl(song.url)
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

    // phát nhạc
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

    // next sang bài nhạc tiếp
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
        listener?.onSongChanged()
    }

    // quay lại bài nhạc
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
        sharedPreferences.edit().putBoolean(KEY_LYRIC_NEW, true).apply()
        senBroadcastInitValueLyric()
    }

    private fun setFuncTabMusic() {
        musicService?.stop()
        musicService?.setMediaPrepared(false)
        sharedPreferences.edit().putBoolean(KEY_PLAY_CLICK, false).apply()
        initValueSong()
        checkSongLove()
        sharedPreferences.edit().putBoolean(KEY_LYRIC_NEW, true).apply()
    }

    private fun senBroadcastInitValueLyric() {
        val isCheck = sharedPreferences.getBoolean(Constant.KEY_ACTIVITY_LYRIC, false)
        if (isCheck) {
            val intent = Intent(Constant.UPDATE_LYRIC)
            intent.putExtra(KEY_INTENT_ITEM, sharedPreferences.getString(KEY_SONG, ""))
            requireContext().sendBroadcast(intent)
        }
    }

    // nghe lại bài nhạc
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

    // nghe ngẫu nhiên
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

    // set thời gian tổng cho tv và gán max của skbar = time của bài hát
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
        mSongs.getOrNull(position)?.let { DownloadMusic.downloadMusic(requireContext(), it) }
        Toast.makeText(requireContext(), KEY_DOWN, Toast.LENGTH_SHORT).show()
    }

    private fun saveSong() {
        val jsonSong = Gson().toJson(mSongs.getOrNull(position))
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
        val intent = Intent(activity, MusicService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    companion object {
        const val NOT_LOGIN = "Bạn chưa đăng nhập"
        const val ADD_SONG_LOVE = "Đã thêm vào bài hát yêu thích"
        const val DELETE_SONG_LOVE = "Đã xóa bài hát khỏi yêu thích"
    }
}

