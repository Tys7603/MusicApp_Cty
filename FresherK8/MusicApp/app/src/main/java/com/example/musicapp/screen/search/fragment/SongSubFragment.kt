package com.example.musicapp.screen.search.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ActivitySongDetailBinding
import com.example.musicapp.databinding.FragmentSongBinding
import com.example.musicapp.screen.search.base.BaseFragment
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.adapter.SongDetailAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import java.util.Random

class SongSubFragment : BaseFragment() {
    private var songs = arrayListOf<Song>()
    private val songAdapter = SongDetailAdapter(::onItemClick)

    private val binding by lazy {
        FragmentSongBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        songs = arguments?.getParcelableArrayList<Song>("KEY_DATA") as ArrayList<Song>
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        binding.rcvSong.setAdapterLinearVertical(songAdapter)
        if (songs.isEmpty()) binding.tvEmpty.visibility = View.VISIBLE else binding.tvEmpty.visibility = View.GONE
        songAdapter.submitList(songs)
    }

    private fun onItemClick(song: Song, position: Int) {
        val intent = Intent(requireContext(), SongActivity::class.java)
        intent.putExtra(Constant.KEY_POSITION_SONG, position)
        intent.putExtra(Constant.KEY_NAME_TAB, "Bài hát tương tự")
        intent.putParcelableArrayListExtra(Constant.KEY_INTENT_ITEM, songs)
        startActivity(intent)
    }

}