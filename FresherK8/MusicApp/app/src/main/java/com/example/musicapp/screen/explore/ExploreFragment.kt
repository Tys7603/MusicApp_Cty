package com.example.musicapp.screen.explore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.musicapp.R
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
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.screen.musicVideoDetail.MusicVideoDetailActivity
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.screen.topic.TopicActivity
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.setAdapterGrid
import com.example.musicapp.shared.extension.setAdapterLinearHorizontal
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.GetValue
import com.example.musicapp.shared.utils.constant.Constant.KEY_INTENT_ITEM
import com.example.musicapp.shared.utils.constant.Constant.KEY_NAME_TAB
import java.util.Random
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("UNCHECKED_CAST")
class ExploreFragment : Fragment() {
    private val viewModel: ExploreViewModel by viewModel()
    private var musicService: MusicService? = null
    private var isServiceBound = false
    private val playListAdapter = PlayListAdapter(::onItemClick)
    private val playListMoodAdapter = PlayListAdapter( ::onItemClick)
    private val topicAdapter = TopicAdapterLinear( ::onItemClick)
    private val categoriesAdapter = CategoriesAdapter( ::onItemClick)
    private val songAgainAdapter = SongAgainAdapter( ::onItemClick)
    private val albumNewAdapter = AlbumAdapter(::onItemClick)
    private val albumLoveAdapter = AlbumAdapter(::onItemClick)
    private val songRankAdapter = SongRankAdapter(::onItemClickSongRank)

    private val binding by lazy {
        FragmentExploreBinding.inflate(layoutInflater)
    }
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val serviceConnection = object : ServiceConnection {
        // kết nối thành công lấy được đối tượng IBinder để try cập music service
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true
        }

        // ngắt kết nối music service
        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
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
        handlerEvent()
        initMusicView()
    }

    private fun initViewModel() {
        binding.exploreViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initMusicView(){
        val isPlaying = sharedPreferences.getBoolean(Constant.KEY_PLAY_CLICK, false)
        if (isPlaying){
            binding.includeLayout.btnLayoutBottomPause.setImageResource(R.drawable.ic_pause_)
        }else{
            binding.includeLayout.btnLayoutBottomPause.setImageResource(R.drawable.ic_play_bottom_sheet)
        }
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

    private fun initSongView() {
        val song = GetValue.getSong(sharedPreferences)
        binding.includeLayout.song = song
    }

    private fun handlerEvent() {
        binding.includeLayout.btnLayoutBottomPause.setOnClickListener { onCheckPlayMusic() }
    }

    private fun onCheckPlayMusic() {
        var isPlaySelected: Boolean by BooleanProperty(sharedPreferences, Constant.KEY_PLAY_CLICK, false)
        isPlaySelected =  if (musicService?.isPlaying()!!){
            musicService?.pause()
            binding.includeLayout.btnLayoutBottomPause.setImageResource(R.drawable.ic_play_bottom_sheet)
            false
        }else{
            musicService?.start()
            binding.includeLayout.btnLayoutBottomPause.setImageResource(R.drawable.ic_pause_)
            true
        }
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

    private fun onItemClickSongRank(song: ArrayList<Song> ,position: Int, name: String){
        sharedPreferences.edit().putString(KEY_NAME_TAB, name).apply()
        val intent = Intent(requireContext(), SongActivity::class.java)
        intent.putExtra(Constant.KEY_POSITION_SONG, position)
        intent.putParcelableArrayListExtra(KEY_INTENT_ITEM, song)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(activity, MusicService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        initSongView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            requireContext().unbindService(serviceConnection)
            isServiceBound = false
        }
        musicService = null
    }
}

