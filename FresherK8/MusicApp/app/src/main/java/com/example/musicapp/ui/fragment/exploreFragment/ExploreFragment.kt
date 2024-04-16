package com.example.musicapp.ui.fragment.exploreFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.apdater.AdapterPlayList
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.model.Playlist
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryTopicAndCategory

class ExploreFragment : Fragment(), ExploreContract.View {
    private lateinit var mPresenter : ExplorePresenter
    private lateinit var binding: FragmentExploreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = ExplorePresenter(this)
        mPresenter.getListPlaylist()
    }

    override fun onListPlaylist(playlists: ArrayList<Playlist>) {
        val adapterPlayList = AdapterPlayList(playlists)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvPlaylist.layoutManager = linearLayoutManager
        binding.rcvPlaylist.adapter = adapterPlayList
    }

    override fun onListTopicAndCategory(topicAndCategory: RepositoryTopicAndCategory) {
        TODO("Not yet implemented")
    }


}