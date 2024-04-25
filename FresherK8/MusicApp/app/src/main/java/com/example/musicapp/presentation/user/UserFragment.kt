package com.example.musicapp.presentation.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.contants.Constant
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.databinding.FragmentUserBinding
import com.example.musicapp.presentation.explore.ExplorePresenter
import com.example.musicapp.presentation.songDown.SongDownActivity
import com.example.musicapp.shared.utils.DownloadMusic
import com.example.musicapp.shared.utils.GetValue
import com.google.gson.Gson


class UserFragment : Fragment() {

    private val binding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }

    private val songDao by lazy {
        SongDao(requireContext())
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
        initQuantityView()
        handleEvent()
    }

    private fun handleEvent() {
        binding.btnTrackDown.setOnClickListener {startActivity(Intent(requireContext(), SongDownActivity::class.java))}
    }

    @SuppressLint("SetTextI18n")
    private fun initQuantityView() {
        binding.tvQuantityTrackDown.text = songDao.readSongs().size.toString() + SONG
    }

    private fun initSongView(){
        val song = GetValue.getSong(sharedPreferences)
//        binding.includeLayout.imgLayoutBottom.loadImageUrl(song.url)
        Glide.with(binding.root).load(song?.image).centerCrop()
            .placeholder(R.drawable.img_placeholder).into(binding.includeLayout1.imgLayoutBottom)
        binding.includeLayout1.tvLayoutBottomNameSong.text = song?.name
    }

    override fun onStart() {
        super.onStart()
        initSongView()
    }

    companion object{
        const val SONG = " bài hát"
    }

}