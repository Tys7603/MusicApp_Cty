package com.example.musicapp.screen.search.fragment

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
import androidx.preference.PreferenceManager
import com.example.musicapp.R
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.databinding.FragmentAlbumBinding
import com.example.musicapp.databinding.FragmentMusicVideoSubBinding
import com.example.musicapp.screen.musicVideoDetail.MusicVideoDetailActivity
import com.example.musicapp.screen.musicVideoDetail.adapter.MusicVideoDetailAdapter
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant

class MusicVideoSubFragment : Fragment() {
    private var musicService: MusicService? = null
    private var isServiceBound = false
    private var musicVideos = arrayListOf<MusicVideo>()
    private val musicVideoAdapter = MusicVideoDetailAdapter(::onItemClick)

    private val binding by lazy {
        FragmentMusicVideoSubBinding.inflate(layoutInflater)
    }

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        musicVideos = arguments?.getParcelableArrayList<MusicVideo>("KEY_DATA") as ArrayList<MusicVideo>
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        binding.rcvMusicVideo.setAdapterLinearVertical(musicVideoAdapter)
        if (musicVideos.isEmpty()) binding.tvEmpty.visibility = View.VISIBLE else binding.tvEmpty.visibility = View.GONE
        musicVideoAdapter.submitList(musicVideos)
    }

    private fun onItemClick(musicVideo: MusicVideo, position: Int) {
        musicService?.pause()
        sharedPreferences.edit().putBoolean(Constant.KEY_PLAY_CLICK, false).apply()
        val intent = Intent(requireContext(), MusicVideoDetailActivity::class.java)
        intent.putExtra(Constant.KEY_INTENT_ITEM, musicVideo)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(activity, MusicService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            requireContext().unbindService(serviceConnection)
            isServiceBound = false
        }
        musicService = null
    }
}