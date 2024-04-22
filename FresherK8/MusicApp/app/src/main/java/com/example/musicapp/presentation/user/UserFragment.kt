package com.example.musicapp.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.contants.Constant
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.databinding.FragmentUserBinding
import com.example.musicapp.presentation.explore.ExplorePresenter
import com.google.gson.Gson


class UserFragment : Fragment() {

    private val binding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }

    private val mPresenter by lazy {
        ExplorePresenter()
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
        initSongView()
    }

    private fun initSongView(){
        val jsonSong = sharedPreferences.getString(Constant.KEY_SONG, "")
        if (jsonSong.isNullOrEmpty()){
            return
        }
        val song = Gson().fromJson(jsonSong, Song::class.java)
//        binding.includeLayout.imgLayoutBottom.loadImageUrl(song.url)
        Glide.with(binding.root).load(song.image).centerCrop()
            .placeholder(R.drawable.img_placeholder).into(binding.includeLayout1.imgLayoutBottom)
        binding.includeLayout1.tvLayoutBottomNameSong.text = song.name
    }

}