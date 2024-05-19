package com.example.musicapp.screen.user.playlistLove

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.databinding.FragmentPlaylistLoveBinding
import com.example.musicapp.screen.user.adapter.BottomSheetSelect
import com.example.musicapp.screen.user.adapter.PlaylistLoveAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistLoveFragment : Fragment() {

    private val viewModel: PlaylistLoveViewModel by viewModel()
    private val playlistUserAdapter = PlaylistLoveAdapter(::onItemClick, 2)
    private val binding by lazy {
        FragmentPlaylistLoveBinding.inflate(layoutInflater)
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
        handleEvent()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun handleEvent() {
        binding.btnOpenBottomSheetLove.setOnClickListener { openBottomSheet() }
    }

    private fun initRecyclerView() {
        binding.rcvPlaylistLove.setAdapterLinearVertical(playlistUserAdapter)
    }

    private fun handleEventViewModel() {
        viewModel.playlists.observe(viewLifecycleOwner) {
            playlistUserAdapter.submitList(it)
        }
    }

    private fun openBottomSheet() {
        val bottomSheet = BottomSheetSelect(::onItemClickBottomSheet, "playlist_love")
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun onItemClickBottomSheet() {
        viewModel.fetchPlaylists()
    }

    private fun onItemClick(boolean: Boolean, any: Any) {

    }
}


