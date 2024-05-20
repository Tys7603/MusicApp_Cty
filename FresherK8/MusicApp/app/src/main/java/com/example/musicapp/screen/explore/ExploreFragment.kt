package com.example.musicapp.screen.explore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Handler.Callback
import android.os.IBinder
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.musicapp.R
import com.example.musicapp.broadcast.MusicBroadcastReceiver
import com.example.musicapp.shared.utils.constant.Constant
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
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.screen.exploreDetail.ExploreDetailActivity
import com.example.musicapp.screen.song.SongActivity
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.screen.topic.TopicActivity
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.setAdapterGrid
import com.example.musicapp.shared.extension.setAdapterLinearHorizontal
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.GetValue
import com.example.musicapp.shared.utils.ListDefault
import com.example.musicapp.shared.utils.constant.Constant.KEY_INTENT_ITEM
import com.example.musicapp.shared.utils.constant.Constant.KEY_NAME
import com.example.musicapp.shared.utils.constant.Constant.KEY_NAME_TAB
import java.util.Random
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : Fragment() {
    private val viewModel: ExploreViewModel by viewModel()
    private var musicService: MusicService? = null
    private var isServiceBound = false
    private val playListAdapter = PlayListAdapter(::onItemClick, 1)
    private val playListMoodAdapter = PlayListAdapter(::onItemClick, 1)
    private val topicAdapter = TopicAdapterLinear(::onItemClick)
    private val categoriesAdapter = CategoriesAdapter(::onItemClick, 1)
    private val songAgainAdapter = SongAgainAdapter(::onItemClick)
    private val albumNewAdapter = AlbumAdapter(::onItemClick, 1)
    private val albumLoveAdapter = AlbumAdapter(::onItemClick, 1)
    private val songRankAdapter = SongRankAdapter(::onItemClickSongRank)
    private var isSnapHelperAttached = false

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
        initAdapterDefault()
    }

    private fun initAdapterDefault(){
        playListAdapter.submitList(ListDefault.initListPlaylist())
        playListAdapter.setEnableItem(false)
        playListMoodAdapter.submitList(ListDefault.initListPlaylist())
        playListMoodAdapter.setEnableItem(false)
        topicAdapter.submitList(ListDefault.initListTopic())
        topicAdapter.setEnableItem(false)
        categoriesAdapter.submitList(ListDefault.initListCategories())
        categoriesAdapter.setEnableItem(false)
        albumNewAdapter.submitList(ListDefault.initListAlbum())
        albumNewAdapter.setEnableItem(false)
        albumLoveAdapter.submitList(ListDefault.initListAlbum())
        albumLoveAdapter.setEnableItem(false)
    }

    private fun initViewModel() {
        binding.exploreViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initMusicView() {
        val isPlaying = sharedPreferences.getBoolean(Constant.KEY_PLAY_CLICK, false)
        if (isPlaying) {
            binding.includeLayout.btnLayoutBottomPause.setImageResource(R.drawable.ic_pause_)
        } else {
            binding.includeLayout.btnLayoutBottomPause.setImageResource(R.drawable.ic_play_bottom_sheet)
        }
    }

    private fun setViewModel() {
        viewModel.playlist.observe(viewLifecycleOwner) {
            handlerPostDelay {
                playListAdapter.submitList(it.shuffled(Random()) as ArrayList<Playlist>)
                playListAdapter.setEnableItem(true)
            }
        }

        viewModel.playlistsMood.observe(viewLifecycleOwner) {
            handlerPostDelay {
                playListMoodAdapter.submitList(it.shuffled(Random()) as ArrayList<Playlist>)
                playListMoodAdapter.setEnableItem(true)
            }
        }

        viewModel.topics.observe(viewLifecycleOwner) {
            handlerPostDelay {
                topicAdapter.submitList(it.shuffled() as ArrayList<Topic>)
                topicAdapter.setEnableItem(true)
            }
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            handlerPostDelay {
                categoriesAdapter.submitList(it.shuffled(Random()) as ArrayList<Category>)
                categoriesAdapter.setEnableItem(true)
            }
        }

        viewModel.songAgain.observe(viewLifecycleOwner) { songAgain ->
            songAgainAdapter.submitList(songAgain)
        }

        viewModel.albumLove.observe(viewLifecycleOwner) {
            handlerPostDelay {
                albumLoveAdapter.submitList(it.shuffled() as ArrayList<Album>)
                albumLoveAdapter.setEnableItem(true)
            }
        }

        viewModel.albumNew.observe(viewLifecycleOwner) {
            handlerPostDelay {
                albumNewAdapter.submitList(it.shuffled() as ArrayList<Album>)
                albumNewAdapter.setEnableItem(true)
            }
        }

        viewModel.songRank.observe(viewLifecycleOwner) {
            songRankAdapter.submitList(it)
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
            // Thiết lập SnapHelper cho RecyclerView songRank
            if (!isSnapHelperAttached) {
                LinearSnapHelper().attachToRecyclerView(rcvSongRank)
                isSnapHelperAttached = true
            }
        }
    }

    fun initSongView() {
        val song = GetValue.getSong(sharedPreferences)
        binding.includeLayout.song = song
    }

    private fun handlerEvent() {
        binding.includeLayout.btnLayoutBottomPause.setOnClickListener { onCheckPlayMusic() }
        binding.tvAddListenAgain.setOnClickListener { onStartActivity(Constant.PLAYLIST) }
        binding.tvAddAlbum.setOnClickListener { onStartActivity(Constant.ALBUM_NEW) }
        binding.tvAddTopic.setOnClickListener { onStartActivity(Constant.CATEGORIES) }
        binding.tvAddLoving.setOnClickListener { onStartActivity(Constant.ALBUM_LOVE) }
        binding.tvAddMood.setOnClickListener { onStartActivity(Constant.MOOD_TODAY) }
    }

    private fun onStartActivity(nameData: String) {
        val intent = Intent(requireContext(), ExploreDetailActivity::class.java)
        when(nameData){
            Constant.PLAYLIST -> {
                intent.putExtra(KEY_INTENT_ITEM, Constant.PLAYLIST)
                intent.putExtra(KEY_NAME, binding.tvTitlePlaylist.text)
            }
            Constant.CATEGORIES -> {
                intent.putExtra(KEY_INTENT_ITEM, Constant.CATEGORIES)
                intent.putExtra(KEY_NAME, binding.tvTitleCategory.text)
            }
            Constant.MOOD_TODAY -> {
                intent.putExtra(KEY_INTENT_ITEM, Constant.MOOD_TODAY)
                intent.putExtra(KEY_NAME, binding.tvTitleMood.text)
            }
            Constant.ALBUM_NEW -> {
                intent.putExtra(KEY_INTENT_ITEM, Constant.ALBUM_NEW)
                intent.putExtra(KEY_NAME, binding.tvTitleNew.text)
            }
            Constant.ALBUM_LOVE -> {
                intent.putExtra(KEY_INTENT_ITEM, Constant.ALBUM_LOVE)
                intent.putExtra(KEY_NAME, binding.tvTitleLoved.text)
            }
        }
        startActivity(intent)
    }

    private fun onCheckPlayMusic() {
        var isPlaySelected: Boolean by BooleanProperty(
            sharedPreferences,
            Constant.KEY_PLAY_CLICK,
            false
        )
        isPlaySelected = if (musicService?.isPlaying()!!) {
            musicService?.pause()
            binding.includeLayout.btnLayoutBottomPause.setImageResource(R.drawable.ic_play_bottom_sheet)
            false
        } else {
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

    private fun onItemClickSongRank(song: ArrayList<Song>, position: Int, name: String) {
        sharedPreferences.edit().putString(KEY_NAME_TAB, name).apply()
        val intent = Intent(requireContext(), SongActivity::class.java)
        intent.putExtra(Constant.KEY_POSITION_SONG, position)
        intent.putParcelableArrayListExtra(KEY_INTENT_ITEM, song)
        startActivity(intent)
    }

    private fun handlerPostDelay(listener : () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed({
            listener.invoke()
        }, 500)
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

