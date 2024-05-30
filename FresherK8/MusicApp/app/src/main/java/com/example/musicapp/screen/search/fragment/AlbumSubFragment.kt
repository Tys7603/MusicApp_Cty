package com.example.musicapp.screen.search.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.databinding.FragmentAlbumBinding
import com.example.musicapp.databinding.FragmentPlaylistBinding
import com.example.musicapp.screen.search.adapter.AlbumSubAdapter
import com.example.musicapp.screen.search.adapter.PlaylistSubAdapter
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant

class AlbumSubFragment : Fragment() {
    private var albums = arrayListOf<Album>()
    private val albumAdapter = AlbumSubAdapter(::onItemClick)

    private val binding by lazy {
        FragmentAlbumBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        albums = arguments?.getParcelableArrayList<Album>("KEY_DATA") as ArrayList<Album>
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        binding.rcvAlbum.setAdapterLinearVertical(albumAdapter)
        if (albums.isEmpty()) binding.tvEmpty.visibility = View.VISIBLE else binding.tvEmpty.visibility = View.GONE
        albumAdapter.submitList(albums)
    }

    private fun onItemClick(album: Album, position: Int) {
        val intent = Intent(requireContext(), SongDetailActivity::class.java)
        intent.putExtra(Constant.KEY_INTENT_ITEM, album)
        startActivity(intent)
    }

}