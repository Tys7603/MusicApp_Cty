package com.example.musicapp.ui.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentMusicBinding
import com.example.musicapp.until.MusicService

class MusicFragment : Fragment() {
    private lateinit var binding: FragmentMusicBinding
    private var musicService: MusicService? = null
    private var isServiceBound = false
    private val URL_SONG = "https://storage.googleapis.com/ikara-storage/tmp/beat.mp3"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playIntent = Intent(requireContext(), MusicFragment::class.java)
        requireActivity().bindService(playIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        binding.btnPlay.setOnClickListener {
            if (isServiceBound){
                musicService?.play(URL_SONG)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (isServiceBound) {
            requireContext().unbindService(serviceConnection)
            isServiceBound = false
        }
    }

}