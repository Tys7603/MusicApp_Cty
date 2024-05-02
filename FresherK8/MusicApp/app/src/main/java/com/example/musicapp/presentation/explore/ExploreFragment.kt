package com.example.musicapp.presentation.explore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_BUNDLE_ITEM
import com.example.musicapp.data.model.Album
import com.example.musicapp.presentation.explore.adapter.AlbumAdapter
import com.example.musicapp.presentation.explore.adapter.AdapterCategories
import com.example.musicapp.presentation.explore.adapter.AdapterPlayList
import com.example.musicapp.presentation.explore.adapter.AdapterSongAgain
import com.example.musicapp.presentation.explore.adapter.AdapterSongRank
import com.example.musicapp.presentation.explore.adapter.AdapterTopic
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.presentation.music.SongActivity
import com.example.musicapp.presentation.songList.SongListActivity
import com.example.musicapp.presentation.topic.TopicActivity
import com.example.musicapp.shared.utils.GetValue
import com.example.musicapp.shared.utils.OnItemClickListener
import java.util.Random
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : Fragment(), ExploreContract.View, OnItemClickListener {
    private val viewModel: ExploreViewModel by viewModel()
    private val adapterPlayList by lazy {
        AdapterPlayList(arrayListOf(), this)
    }
    private val binding by lazy {
        FragmentExploreBinding.inflate(layoutInflater)
    }

    private val mPresenter by lazy {
        ExplorePresenter()
    }

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserLogin()
        setViewModel()
        setAdapterPlaylistView()
    }


    private fun setViewModel() {
        viewModel.playlist.observe(viewLifecycleOwner) { playlists ->
            adapterPlayList.setPlaylist(playlists.shuffled(Random()) as ArrayList<Playlist>)
        }
    }

    private fun setAdapterPlaylistView() {
        binding.rcvPlaylist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvPlaylist.adapter = adapterPlayList
    }

    override fun onStart() {
        super.onStart()
        initSongView()
    }

    // kiểm tra xem người dùng đã đăng nhập chưa
    private fun checkUserLogin() {
        val user = true
        if (user) {
            mPresenter.getListListenAgain(1)
        } else {
            binding.tvListenAgain.visibility = View.GONE
            binding.rcvListenAgain.visibility = View.GONE
        }
    }

    override fun onListPlaylistMoodToday(playlists: ArrayList<Playlist>) {
        val adapterPlayList =
            AdapterPlayList(playlists.shuffled(Random()) as ArrayList<Playlist>, this)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvAlbumMoodToday.layoutManager = linearLayoutManager
        binding.rcvAlbumMoodToday.adapter = adapterPlayList
    }

    override fun onListTopic(topics: ArrayList<Topic>) {
        val adapter = AdapterTopic(topics.shuffled(Random()) as ArrayList<Topic>, this)
        binding.rcvTopic.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvTopic.adapter = adapter
    }

    override fun onListCategory(categories: ArrayList<Category>) {
        val adapter = AdapterCategories(categories.shuffled(Random()) as ArrayList<Category>, this)
        binding.rcvCategory.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        binding.rcvCategory.adapter = adapter
    }

    override fun onListListenAgain(songAgain: ArrayList<SongAgain>) {
        // kiểm tra xem có data không để hiển thị tv, rcv
        if (songAgain.size > 0) {
            binding.tvListenAgain.visibility = View.VISIBLE
            binding.rcvListenAgain.visibility = View.VISIBLE
        }

        val adapter = AdapterSongAgain(songAgain, this)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvListenAgain.layoutManager = linearLayoutManager
        binding.rcvListenAgain.adapter = adapter
    }

    override fun onListAlbumLove(albumLove: ArrayList<Album>) {
        val adapter = AlbumAdapter(albumLove, this)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvAlbumLove.layoutManager = linearLayoutManager
        binding.rcvAlbumLove.adapter = adapter
    }

    override fun onListAlbumNew(albumNew: ArrayList<Album>) {
        val adapter = AlbumAdapter(albumNew.shuffled(Random()) as ArrayList<Album>, this)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvAlbumNew.layoutManager = linearLayoutManager
        binding.rcvAlbumNew.adapter = adapter
    }

    override fun onListSongRank(songRanks: ArrayList<SongRank>) {
        val snapHelper = LinearSnapHelper()
        val adapter = AdapterSongRank(songRanks)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvSongRank.layoutManager = linearLayoutManager
        binding.rcvSongRank.adapter = adapter
        snapHelper.attachToRecyclerView(binding.rcvSongRank)
    }

    private fun initSongView() {
        val song = GetValue.getSong(sharedPreferences)
//        binding.includeLayout.imgLayoutBottom.loadImageUrl(song.url)
        Glide.with(binding.root).load(song?.image).centerCrop()
            .placeholder(R.drawable.img_placeholder).into(binding.includeLayout.imgLayoutBottom)

        binding.includeLayout.tvLayoutBottomNameSong.text = song?.name
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun onItemClick(item: Any) {
        when (item) {
            is Playlist -> {
                val intent = Intent(requireContext(), SongListActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable(Constant.KEY_INTENT_ITEM, item)
                }
                intent.putExtra(KEY_BUNDLE_ITEM, bundle)
                startActivity(intent)
            }

            is Category -> {
                val intent = Intent(requireContext(), TopicActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable(Constant.KEY_INTENT_ITEM, item)
                }
                intent.putExtra(KEY_BUNDLE_ITEM, bundle)
                startActivity(intent)
            }

            is Topic -> {
                val intent = Intent(requireContext(), SongListActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable(Constant.KEY_INTENT_ITEM, item)
                }
                intent.putExtra(KEY_BUNDLE_ITEM, bundle)
                startActivity(intent)
            }

            is SongAgain -> {
                val intent = Intent(requireContext(), SongActivity::class.java)
                startActivity(intent)
            }

            is Album -> {
                val intent = Intent(requireContext(), SongListActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable(Constant.KEY_INTENT_ITEM, item)
                }
                intent.putExtra(KEY_BUNDLE_ITEM, bundle)
                startActivity(intent)
            }
        }
    }
}

