package com.example.musicapp.screen.search.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.FragmentPlaylistBinding
import com.example.musicapp.databinding.FragmentSongBinding
import com.example.musicapp.screen.search.adapter.PlaylistSubAdapter
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.screen.songDetail.adapter.SongDetailAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant

class PlaylistSubFragment : Fragment() {
    private var playlists = arrayListOf<Playlist>()
    private val playlistAdapter = PlaylistSubAdapter(::onItemClick)

    private val binding by lazy {
        FragmentPlaylistBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        playlists = arguments?.getParcelableArrayList<Playlist>("KEY_DATA") as ArrayList<Playlist>
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        binding.rcvPlaylist.setAdapterLinearVertical(playlistAdapter)
        if (playlists.isEmpty()) binding.tvEmpty.visibility = View.VISIBLE else binding.tvEmpty.visibility = View.GONE
        playlistAdapter.submitList(playlists)
    }

    private fun onItemClick(playlist: Playlist, position: Int) {
        val intent = Intent(requireContext(), SongDetailActivity::class.java)
        intent.putExtra(Constant.KEY_INTENT_ITEM, playlist)
        startActivity(intent)
    }

}