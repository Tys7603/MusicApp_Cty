package com.example.musicapp.screen.explore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_BUNDLE_ITEM
import com.example.musicapp.data.model.Album
import com.example.musicapp.screen.explore.adapter.AlbumAdapter
import com.example.musicapp.screen.explore.adapter.CategoriesAdapter
import com.example.musicapp.screen.explore.adapter.PlayListAdapter
import com.example.musicapp.screen.explore.adapter.SongAgainAdapter
import com.example.musicapp.screen.explore.adapter.SongRankAdapter
import com.example.musicapp.screen.explore.adapter.TopicAdapterLinear
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.screen.musicVideoDetail.MusicVideoDetailActivity
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.screen.topic.TopicActivity
import com.example.musicapp.shared.extension.setAdapterGrid
import com.example.musicapp.shared.extension.setAdapterLinearHorizontal
import com.example.musicapp.shared.utils.GetValue
import com.example.musicapp.shared.utils.constant.Constant.KEY_INTENT_ITEM
import java.util.Random
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : Fragment() {
    private val viewModel: ExploreViewModel by viewModel()

    private val playListAdapter = PlayListAdapter(::onItemClick)
    private val playListMoodAdapter = PlayListAdapter( ::onItemClick)
    private val topicAdapter = TopicAdapterLinear( ::onItemClick)
    private val categoriesAdapter = CategoriesAdapter( ::onItemClick)
    private val songAgainAdapter = SongAgainAdapter( ::onItemClick)
    private val albumNewAdapter = AlbumAdapter(::onItemClick)
    private val albumLoveAdapter = AlbumAdapter(::onItemClick)
    private val songRankAdapter = SongRankAdapter()

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
            playListAdapter.submitList(playlists.shuffled(Random()) as ArrayList<Playlist>)
        }

        viewModel.playlistsMood.observe(viewLifecycleOwner) { playlistsMood ->
            playListMoodAdapter.submitList(playlistsMood.shuffled(Random()) as ArrayList<Playlist>)
        }

        viewModel.topics.observe(viewLifecycleOwner) { topics ->
            topicAdapter.submitList(topics.shuffled() as ArrayList<Topic>)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.submitList(categories.shuffled(Random()) as ArrayList<Category>)
        }

        viewModel.songAgain.observe(viewLifecycleOwner) { songAgain ->
            songAgainAdapter.submitList(songAgain)
        }

        viewModel.albumLove.observe(viewLifecycleOwner) { albumLove ->
           albumLoveAdapter.submitList(albumLove.shuffled() as ArrayList<Album>)
        }

        viewModel.albumNew.observe(viewLifecycleOwner) { albumNew ->
            albumNewAdapter.submitList(albumNew.shuffled() as ArrayList<Album>)
        }

        viewModel.songRank.observe(viewLifecycleOwner) { songRank ->
            songRankAdapter.submitList(songRank)
        }
    }
    private fun setAdapterView() {
        with(binding) {
            rcvCategory.setAdapterGrid(categoriesAdapter)
            rcvPlaylist.setAdapterLinearHorizontal(playListAdapter)
            rcvAlbumLove.setAdapterLinearHorizontal(albumLoveAdapter)
            rcvAlbumNew.setAdapterLinearHorizontal(albumNewAdapter)
            rcvAlbumMoodToday.setAdapterLinearHorizontal(playListMoodAdapter)
            rcvTopic.setAdapterLinearHorizontal(topicAdapter)
            rcvListenAgain.setAdapterLinearHorizontal(songAgainAdapter)
            rcvSongRank.setAdapterLinearHorizontal(songRankAdapter)
        }
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

    private fun onItemClick(item: Any) {
        when (item) {
            is Playlist -> {
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
                intent.putExtra(KEY_INTENT_ITEM, item)
                startActivity(intent)
            }

            is Category -> {
                val intent = Intent(requireContext(), TopicActivity::class.java)
                intent.putExtra(KEY_INTENT_ITEM, item)
                startActivity(intent)
            }

            is Topic -> {
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
                intent.putExtra(KEY_INTENT_ITEM, item)
                startActivity(intent)
            }

            is SongAgain -> {
                val intent = Intent(requireContext(), SongActivity::class.java)
                startActivity(intent)
            }

            is Album -> {
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
                intent.putExtra(KEY_INTENT_ITEM, item)
                startActivity(intent)
            }
        }
    }
}

