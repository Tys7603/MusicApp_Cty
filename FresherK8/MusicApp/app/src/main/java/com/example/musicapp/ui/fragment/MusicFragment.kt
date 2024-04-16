package com.example.musicapp.ui.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentMusicBinding
import com.example.musicapp.service.MusicService
import com.example.musicapp.until.BooleanProperty
import com.example.musicapp.until.SharedPreferencesManager

class MusicFragment : Fragment() {
    private lateinit var binding: FragmentMusicBinding
    private lateinit var musicService: MusicService
    private var isServiceBound = false // kiểm tra kết nối service
    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val URL_SONG =
        "https://ia800304.us.archive.org/33/items/muon-anh-dau-winno-hustlang-robber-13672897_202404/BuonHayVuiFeatRptMckObitoRonboogz-VSOULRPTMCKObitoRonboogz-13159599.mp3"

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
        }

        // ngắt kết nối music service
        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        initView()
    }

    private fun initView() {
        if (sharedPreferences.getBoolean(KEY_PLAY_CLICK, false)) {
            binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
        } else {
            binding.btnPlay.setImageResource(R.drawable.ic_play_button)
        }
    }

    private fun onClick() {
        binding.btnPlay.setOnClickListener {
            var isPlaySelected: Boolean by BooleanProperty(sharedPreferences, KEY_PLAY_CLICK, false)
            var isPlayFirtSelected: Boolean by BooleanProperty(
                sharedPreferences,
                KEY_IS_PLAY_CLICK,
                false
            )

            Log.d("TAG", "isPlaySelected: " + isPlaySelected)
            Log.d("TAG", "isPlayFirtSelected: " + isPlayFirtSelected)

            if (isServiceBound) { // kiểm tra đã kết nối chưa
                isPlaySelected = if (!musicService.isPlaying()) { // kiểm tra xem đã play chưa
                    if (!isPlayFirtSelected) { // kiểm tra xem đã play đọc từ internet chưa
                        musicService.playFromUrl(URL_SONG)
                        isPlayFirtSelected = false
                    } else {
                        musicService.start()
                    }
                    binding.btnPlay.setImageResource(R.drawable.ic_pause_music)
                    true
                } else {
                    musicService.pause()
                    binding.btnPlay.setImageResource(R.drawable.ic_play_button)
                    false
                }

            }
        }
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

    companion object {
        const val KEY_PLAY_CLICK = "play_music"
        const val KEY_IS_PLAY_CLICK = "is_play_music"
    }
}

