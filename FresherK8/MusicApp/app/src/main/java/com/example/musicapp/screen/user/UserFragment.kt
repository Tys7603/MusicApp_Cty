package com.example.musicapp.screen.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.databinding.FragmentUserBinding
import com.example.musicapp.screen.songDown.SongDownActivity
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.shared.utils.GetValue
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserFragment : Fragment() {

    private val viewModel : UserViewModel by viewModel()

    private val binding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvent()
        handleEventViewModel()
        initViewModel()
    }

    private fun initViewModel() {
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun handleEventViewModel() {
    }


    private fun handleEvent() {
        binding.btnTrackDown.setOnClickListener {startActivity(Intent(requireContext(), SongDownActivity::class.java))}
        binding.btnLogin.setOnClickListener { openBottomSheetLogin() }
    }

    private fun openBottomSheetLogin() {
        val bottomSheetLogin = BottomSheetLogin()
        bottomSheetLogin.show(parentFragmentManager, bottomSheetLogin.tag)
    }

    private fun initSongView(){
        val song = GetValue.getSong(sharedPreferences)
        binding.includeLayout1.song = song
    }

    override fun onStart() {
        super.onStart()
        initSongView()
    }

}