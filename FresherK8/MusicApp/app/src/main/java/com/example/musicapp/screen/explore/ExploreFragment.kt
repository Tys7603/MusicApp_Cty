package com.example.musicapp.screen.explore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_BUNDLE_ITEM
import com.example.musicapp.data.model.Album
import com.example.musicapp.screen.explore.adapter.AlbumAdapter
import com.example.musicapp.screen.explore.adapter.AdapterCategories
import com.example.musicapp.screen.explore.adapter.AdapterPlayList
import com.example.musicapp.screen.explore.adapter.AdapterSongAgain
import com.example.musicapp.screen.explore.adapter.AdapterSongRank
import com.example.musicapp.screen.explore.adapter.AdapterTopic
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.screen.topic.TopicActivity
import com.example.musicapp.shared.extension.setAdapterGrid
import com.example.musicapp.shared.extension.setAdapterLinearHorizontal
import com.example.musicapp.shared.utils.GetValue
import com.example.musicapp.shared.utils.OnItemClickListener
import java.util.Random
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : Fragment(), OnItemClickListener {
    private val viewModel: ExploreViewModel by viewModel()

    private val playListAdapter by lazy {
        AdapterPlayList(arrayListOf(), this)
    }

    private val playListMoodAdapter by lazy {
        AdapterPlayList(arrayListOf(), this)
    }

    private val topicAdapter by lazy {
        AdapterTopic(arrayListOf(), this)
    }

    private val categoriesAdapter by lazy {
        AdapterCategories(arrayListOf(), this)
    }

    private val songAgainAdapter by lazy {
        AdapterSongAgain(arrayListOf(), this)
    }

    private val albumNewAdapter by lazy {
        AlbumAdapter(arrayListOf(), this)
    }

    private val albumLoveAdapter by lazy {
        AlbumAdapter(arrayListOf(), this)
    }

    private val songRankAdapter by lazy {
        AdapterSongRank(arrayListOf())
    }

    private val binding by lazy {
        FragmentExploreBinding.inflate(layoutInflater)
    }
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setViewModel()
        setAdapterView()
    }

    private fun initViewModel() {
        binding.exploreViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setViewModel() {
        viewModel.playlist.observe(viewLifecycleOwner) { playlists ->
            playListAdapter.setPlaylist(playlists.shuffled(Random()) as ArrayList<Playlist>)
        }

        viewModel.playlistsMood.observe(viewLifecycleOwner) { playlistsMood ->
            playListMoodAdapter.setPlaylist(playlistsMood.shuffled(Random()) as ArrayList<Playlist>)
        }

        viewModel.topics.observe(viewLifecycleOwner) { topics ->
            topicAdapter.setTopics(topics.shuffled() as ArrayList<Topic>)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategories(categories.shuffled(Random()) as ArrayList<Category>)
        }

        viewModel.songAgain.observe(viewLifecycleOwner) { songAgain ->
            songAgainAdapter.setSongAgain(songAgain)
        }

        viewModel.albumLove.observe(viewLifecycleOwner) { albumLove ->
           albumLoveAdapter.setAlbums(albumLove.shuffled() as ArrayList<Album>)
        }

        viewModel.albumNew.observe(viewLifecycleOwner) { albumNew ->
            albumNewAdapter.setAlbums(albumNew.shuffled() as ArrayList<Album>)
        }

        viewModel.songRank.observe(viewLifecycleOwner) { songRank ->
            songRankAdapter.setSongRank(songRank)
        }
    }
    private fun setAdapterView() {
        binding.rcvCategory.setAdapterGrid(categoriesAdapter)
        binding.rcvPlaylist.setAdapterLinearHorizontal(playListAdapter)
        binding.rcvAlbumLove.setAdapterLinearHorizontal(albumLoveAdapter)
        binding.rcvAlbumNew.setAdapterLinearHorizontal(albumNewAdapter)
        binding.rcvAlbumMoodToday.setAdapterLinearHorizontal(playListMoodAdapter)
        binding.rcvTopic.setAdapterLinearHorizontal(topicAdapter)
        binding.rcvListenAgain.setAdapterLinearHorizontal(songAgainAdapter)
        binding.rcvSongRank.setAdapterLinearHorizontal(songRankAdapter)
        // Thiết lập SnapHelper cho RecyclerView songRank
        LinearSnapHelper().attachToRecyclerView(binding.rcvSongRank)
    }

    override fun onStart() {
        super.onStart()
        initSongView()
    }

    private fun initSongView() {
        val song = GetValue.getSong(sharedPreferences)
        binding.includeLayout.song = song
    }

    override fun onItemClick(item: Any) {
        when (item) {
            is Playlist -> {
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
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
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
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
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable(Constant.KEY_INTENT_ITEM, item)
                }
                intent.putExtra(KEY_BUNDLE_ITEM, bundle)
                startActivity(intent)
            }
        }
    }
}

