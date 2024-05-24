package com.example.musicapp.screen.search.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.databinding.FragmentAlbumBinding
import com.example.musicapp.databinding.FragmentMusicVideoSubBinding
import com.example.musicapp.screen.musicVideoDetail.adapter.MusicVideoDetailAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical

class MusicVideoSubFragment : Fragment() {
    private var musicVideos = arrayListOf<MusicVideo>()
    private val musicVideoAdapter = MusicVideoDetailAdapter(::onItemClick)

    private val binding by lazy {
        FragmentMusicVideoSubBinding.inflate(layoutInflater)
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
        musicVideoAdapter.submitList(musicVideos)
    }

    private fun onItemClick(musicVideo: MusicVideo, position: Int) {
//        onStartPosition(position, false)
    }
}