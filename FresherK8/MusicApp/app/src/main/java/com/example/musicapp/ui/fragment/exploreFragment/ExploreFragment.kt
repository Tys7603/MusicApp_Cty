package com.example.musicapp.ui.fragment.exploreFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.apdater.AdapterAlbumLove
import com.example.musicapp.apdater.AdapterAlbumNew
import com.example.musicapp.apdater.AdapterCategories
import com.example.musicapp.apdater.AdapterPlayList
import com.example.musicapp.apdater.AdapterSongAgain
import com.example.musicapp.apdater.AdapterTopic
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.model.AlbumLove
import com.example.musicapp.model.AlbumNew
import com.example.musicapp.model.Category
import com.example.musicapp.model.Playlist
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongAgain
import com.example.musicapp.model.Topic
import java.util.Random

class ExploreFragment : Fragment(), ExploreContract.View {
    private lateinit var mPresenter: ExplorePresenter
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
        mPresenter.getListPlaylistMoodToday()
        mPresenter.getListTopic()
        mPresenter.getListCategory()
        mPresenter.getListAlbumLove()
        mPresenter.getListAlbumNew()
        mPresenter.getListSong()
        checkUserLogin()
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
        val adapterPlayList = AdapterPlayList(playlists.shuffled(Random()) as ArrayList<Playlist>)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvPlaylist.layoutManager = linearLayoutManager
        binding.rcvPlaylist.adapter = adapterPlayList
    }

    override fun onListPlaylistMoodToday(playlists: ArrayList<Playlist>) {
        val adapterPlayList = AdapterPlayList(playlists.shuffled(Random()) as ArrayList<Playlist>)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvAlbumMoodToday.layoutManager = linearLayoutManager
        binding.rcvAlbumMoodToday.adapter = adapterPlayList
    }

    override fun onListTopic(topics: ArrayList<Topic>) {
        val adapter = AdapterTopic(topics.shuffled(Random()) as ArrayList<Topic>)
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        binding.rcvTopic.layoutManager = gridLayoutManager
        binding.rcvTopic.adapter = adapter
    }

    override fun onListCategory(categories: ArrayList<Category>) {
        val adapter = AdapterCategories(categories.shuffled(Random()) as ArrayList<Category>)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvCategory.layoutManager = linearLayoutManager
        binding.rcvCategory.adapter = adapter
    }

    override fun onListListenAgain(songAgain: ArrayList<SongAgain>) {
        // kiểm tra xem có data không để hiển thị tv, rcv
        if (songAgain.size > 0){
            binding.tvListenAgain.visibility = View.VISIBLE
            binding.rcvListenAgain.visibility = View.VISIBLE
        }

        val adapter = AdapterSongAgain(songAgain)
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

    override fun onListSong(songs: ArrayList<Song>) {

    }
}

