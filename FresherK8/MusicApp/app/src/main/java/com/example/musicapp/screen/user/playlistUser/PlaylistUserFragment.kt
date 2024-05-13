package com.example.musicapp.screen.user.playlistUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.databinding.FragmentPlaylistUserBinding
import com.example.musicapp.screen.user.adapter.PlaylistUserAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistUserFragment : Fragment() {

    private val viewModel : PlaylistUserViewModel by viewModel()
    private val playlistUserAdapter = PlaylistUserAdapter(::onItemClick)

    private val binding by lazy {
        FragmentPlaylistUserBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEventViewModel()
        initViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rcvPlaylistUser.setAdapterLinearVertical(playlistUserAdapter)
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun handleEventViewModel() {
        viewModel.playlistUser.observe(viewLifecycleOwner){
            playlistUserAdapter.submitList(it)
        }
    }

    private fun onItemClick(playlistUser: PlaylistUser){

    }

}