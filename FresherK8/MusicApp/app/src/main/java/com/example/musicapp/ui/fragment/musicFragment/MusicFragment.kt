package com.example.musicapp.ui.fragment.musicFragment

import SharedPreferencesDelegate
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.Build
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
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentMusicBinding
import com.example.musicapp.model.Song
import com.example.musicapp.service.MusicService
import com.example.musicapp.until.BooleanProperty
import com.example.musicapp.until.Constant
import com.example.musicapp.until.FormatUntil

class MusicFragment : Fragment(), MusicContract.View {
    private lateinit var binding: FragmentMusicBinding
    private lateinit var musicService: MusicService
    private lateinit var musicPresenter: MusicPresenter
    private lateinit var mSongs: ArrayList<Song>
    private var position = 0
    private var isServiceBound = false // kiểm tra kết nối service

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    companion object {
        const val KEY_PLAY_CLICK = "play_music"
        const val VALUE_DEFAULT = "00:00"
        const val KEY_POSITION = "position"
    }

//    var myIntPreference by SharedPreferencesDelegate(sharedPreferences, "my_int_key", 0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val serviceConnection = object : ServiceConnection {
        // kết nối thành công lấy được đối tượng IBinder để try cập music service
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true
            musicService.musicService(this@MusicFragment)
        }

        // ngắt kết nối music service
        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicPresenter = MusicPresenter(this)
        musicPresenter.getListSong()
        onClick()
    }

    // hiển thị tên, ảnh bài hát, tên ca sĩ, bg
    private fun initValueSong() {
        position = sharedPreferences.getInt(KEY_POSITION, 0)
        Glide.with(requireContext()).load(mSongs[position].image).centerCrop()
            .placeholder(R.drawable.img_placeholder).into(binding.imgSong)
        binding.tvNameArtistSong.text = mSongs[position].nameArtis
        binding.tvNameSong.text = mSongs[position].name
        binding.tvTotalTimeSong.text = VALUE_DEFAULT
        Glide.with(requireContext()).load(mSongs[position].image).centerCrop()
            .placeholder(R.drawable.img_placeholder).into(binding.imgBg)
        if (isServiceBound) {
            if (!musicService.isMediaPrepared()) {
                musicService.playFromUrl(mSongs[position].url)
                musicService.setOnCompletionListener {
                    // Xử lý khi bài hát kết thúc, chuyển sang bài hát tiếp theo
                    nextMusic()
                }
            }else{
                // khi bài hát đã được chuẩn bị -> khi bấm qua fragment khác
                setTimeTotal()
                updateTimeSong()
            }
        }
        initViewButton()
    }

    // kiểm tra xem nhạc có đang phát không -> hiển thị nút play, pause
    private fun initViewButton() {
        if (sharedPreferences.getBoolean(KEY_PLAY_CLICK, false)) {
            binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
        } else {
            binding.btnPlay.setImageResource(R.drawable.ic_play_button)
        }
    }

    private fun onClick() {
        binding.btnPlay.setOnClickListener { playMusic() }
        binding.btnNext.setOnClickListener { nextMusic() }
        binding.btnBack.setOnClickListener { backMusic() }
        binding.btnLoop.setOnClickListener { autoRestart() }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (isServiceBound) {
                    musicService.seekTo(binding.seekBar.progress)
                }
            }
        })
    }

    // next sang bài nhạc tiếp
    private fun nextMusic() {
        position++
        if (position > mSongs.size - 1) {
            position = 0
        }
        sharedPreferences.edit().putInt(KEY_POSITION, position).apply()
        setFuncMusic()
        musicService.setNextMusic(true)
    }

    //    // quay lại bài nhạc
    private fun backMusic() {
        position--
        if (position < 0) {
            position = mSongs.size - 1
        }
        sharedPreferences.edit().putInt(KEY_POSITION, position).apply()
        setFuncMusic()
    }

    private fun setFuncMusic() {
        musicService.stop()
        musicService.setMediaPrepared(false)
        var isPlaySelected: Boolean by BooleanProperty(sharedPreferences, KEY_PLAY_CLICK, false)
        isPlaySelected = true
        initValueSong()
    }

    // phát nhạc
    private fun playMusic() {
        var isPlaySelected: Boolean by BooleanProperty(sharedPreferences, KEY_PLAY_CLICK, false)

        if (isServiceBound) { // kiểm tra đã kết nối chưa
            isPlaySelected = if (!musicService.isPlaying()) { // kiểm tra xem đã play chưa
                musicService.start()
                updateTimeSong()
                binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
                true
            } else {
                musicService.pause()
                binding.btnPlay.setImageResource(R.drawable.ic_play_button)
                false
            }

        }
    }

    // nghe lại bài nhạc
    private fun autoRestart(){
       if (musicService.isAutoRestart()){
           musicService.setAutoRestart(false)
           binding.btnLoop.setImageResource(R.drawable.ic_repeat_black)
       }else{
           musicService.setAutoRestart(true)
           binding.btnLoop.setImageResource(R.drawable.ic_loop_color)

       }
    }

    // set thời gian tổng cho tv và gán max của skbar = time của bài hát
    private fun setTimeTotal() {
        if (isServiceBound) {
            binding.tvTotalTimeSong.text = FormatUntil.formatTime(musicService.getDuration())
            // gán max cho skbar
            binding.seekBar.max = musicService.getDuration()
        }
    }

    private fun updateTimeSong() {
        // Tạo một Handler liên kết với Looper của luồng chính
        val handler = Handler(Looper.getMainLooper())

        // Đặt một hành động trì hoãn để cập nhật UI sau 100 mili giây
        handler.postDelayed({

            // Cập nhật UI với vị trí hiện tại của trình phát nhạc
            binding.tvTimeSong.text = FormatUntil.formatTime(musicService.getCurrentPosition())

            // set progress cho seekbar
            binding.seekBar.progress = musicService.getCurrentPosition()

            // Đặt một hành động trì hoãn khác để gọi lại updateTimeSong sau 500 mili giây
            handler.postDelayed({ updateTimeSong() }, 500)

        }, 100)
    }
    


    override fun onStart() {
        super.onStart()
        // khởi tạo và liên kết tới music service
        val intent = Intent(activity, MusicService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            // ngắt kiên kết
            requireContext().unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    override fun onListSong(songs: ArrayList<Song>) {
        mSongs = songs
        if (isServiceBound){
            initValueSong()
        }
    }

    override fun onMediaPrepared() {
        setTimeTotal()
        if (musicService.isNextMusic()){
            musicService.start()
            updateTimeSong()
        }
    }

}

