package com.example.musicapp.presentation.explore


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.contants.Constant
import com.example.musicapp.contants.Constant.KEY_BUNDLE_ITEM
import com.example.musicapp.presentation.explore.adapter.AdapterAlbumLove
import com.example.musicapp.presentation.explore.adapter.AdapterAlbumNew
import com.example.musicapp.presentation.explore.adapter.AdapterCategories
import com.example.musicapp.presentation.explore.adapter.AdapterPlayList
import com.example.musicapp.presentation.explore.adapter.AdapterSongAgain
import com.example.musicapp.presentation.explore.adapter.AdapterSongRank
import com.example.musicapp.presentation.explore.adapter.AdapterTopic
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.data.model.AlbumLove
import com.example.musicapp.data.model.AlbumNew
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.presentation.music.MusicFragment
import com.example.musicapp.presentation.song.SongActivity
import com.example.musicapp.presentation.songList.SongListActivity
import com.example.musicapp.presentation.topic.TopicActivity
import com.example.musicapp.shared.utils.OnItemClickListener
import com.google.gson.Gson
import java.util.Random

class ExploreFragment : Fragment(), ExploreContract.View, OnItemClickListener {

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
        mPresenter.run {
            getListPlaylist()
            getListPlaylistMoodToday()
            getListTopic()
            getListCategory()
            getListAlbumLove()
            getListAlbumNew()
            getListSongRank()
        }
        checkUserLogin()
        initSongView()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.setView(this@ExploreFragment)
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

    override fun onListPlaylist(playlists: ArrayList<Playlist>) {
        val adapterPlayList =
            AdapterPlayList(playlists.shuffled(Random()) as ArrayList<Playlist>, this)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvPlaylist.layoutManager = linearLayoutManager
        binding.rcvPlaylist.adapter = adapterPlayList
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
        binding.rcvTopic.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvTopic.adapter = adapter
    }

    override fun onListCategory(categories: ArrayList<Category>) {
        val adapter = AdapterCategories(categories.shuffled(Random()) as ArrayList<Category>, this)
        binding.rcvCategory.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
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

    override fun onListAlbumLove(albumLove: ArrayList<AlbumLove>) {
        val adapter = AdapterAlbumLove(albumLove)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvAlbumLove.layoutManager = linearLayoutManager
        binding.rcvAlbumLove.adapter = adapter
    }

    override fun onListAlbumNew(albumNew: ArrayList<AlbumNew>) {
        val adapter = AdapterAlbumNew(albumNew.shuffled(Random()) as ArrayList<AlbumNew>)
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
        val jsonSong = sharedPreferences.getString(Constant.KEY_SONG, "")
        if (jsonSong.isNullOrEmpty()) {
            return
        }
        val song = Gson().fromJson(jsonSong, Song::class.java)
//        binding.includeLayout.imgLayoutBottom.loadImageUrl(song.url)
        Glide.with(binding.root).load(song.image).centerCrop()
            .placeholder(R.drawable.img_placeholder).into(binding.includeLayout.imgLayoutBottom)

        binding.includeLayout.tvLayoutBottomNameSong.text = song.name
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
        }
    }
}

