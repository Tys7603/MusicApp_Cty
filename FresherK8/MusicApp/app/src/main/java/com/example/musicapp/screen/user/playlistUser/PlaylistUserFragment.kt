package com.example.musicapp.screen.user.playlistUser

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.databinding.FragmentPlaylistUserBinding
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.screen.user.adapter.BottomSheetPlaylist
import com.example.musicapp.screen.user.adapter.BottomSheetSelect
import com.example.musicapp.screen.user.adapter.PlaylistUserAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistUserFragment : Fragment() {

    private val viewModel: PlaylistUserViewModel by viewModel()
    private val playlistUserAdapter = PlaylistUserAdapter(::onItemClick, 2)
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
        handleEvent()
    }

    private fun handleEvent() {
        binding.btnOpenBottomSheet.setOnClickListener { checkUserLogin(0) }
        binding.btnOpenBottomSheetSelect.setOnClickListener { checkUserLogin(1) }
    }

    private fun checkUserLogin(id: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            when (id) {
                0 -> openBottomSheetCreatePlaylist()
                1 -> openBottomSheetSelectPlaylist()
            }
        } else {
            openBottomSheetLogin()
        }
    }

    private fun initRecyclerView() {
        binding.rcvPlaylistUser.setAdapterLinearVertical(playlistUserAdapter)
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun handleEventViewModel() {
        viewModel.playlistUser.observe(viewLifecycleOwner) {
            playlistUserAdapter.submitList(it)
        }
    }

    private fun openBottomSheetCreatePlaylist() {
        val bottomSheet = BottomSheetPlaylist(::onItemClickBottomSheet)
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun openBottomSheetSelectPlaylist() {
        val bottomSheet = BottomSheetSelect(::onItemClickBottomSheet, "playlist_user")
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun openBottomSheetLogin() {
        val bottomSheetLogin = BottomSheetLogin()
        bottomSheetLogin.show(parentFragmentManager, bottomSheetLogin.tag)
    }

    private fun onItemClickBottomSheet() {
        viewModel.fetchPlaylistsUser()
    }

    private fun onItemClick(boolean: Boolean, any: Any) {

    }
}