package com.example.musicapp.screen.user

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.FragmentUserBinding
import com.example.musicapp.screen.account.information.InformationActivity
import com.example.musicapp.screen.music.MusicFragment
import com.example.musicapp.screen.songDetail.SongDetailActivity
import com.example.musicapp.screen.songUser.SongUserActivity
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.screen.user.adapter.BottomSheetPlaylist
import com.example.musicapp.screen.user.adapter.BottomSheetSelect
import com.example.musicapp.screen.user.adapter.PlaylistLoveAdapter
import com.example.musicapp.screen.user.adapter.PlaylistUserAdapter
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.GetValue
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserFragment : Fragment() {

    private var musicService: MusicService? = null
    private var isServiceBound = false
    private val viewModel: UserViewModel by viewModel()
    private val viewModelUser: PlaylistUserViewModel by viewModel()
    private val viewModelLove: PlaylistLoveViewModel by viewModel()
    private var songsLove: MutableList<Song> = mutableListOf()
    private val user = FirebaseAuth.getInstance().currentUser
    private val playlistLoveAdapter = PlaylistLoveAdapter(::onItemClick, 2)
    private val playlistUserAdapter = PlaylistUserAdapter(::onItemClick, 2)

    private val binding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvent()
        initViewModel()
        initMusicView()
        handleEventViewModel()
        initRecyclerView()
    }

    private fun handleEventViewModel() {
        viewModel.songsLove.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.imgLove.loadImageUrl(it[0].image)
                songsLove = it
            }
        }

        viewModelLove.playlists.observe(viewLifecycleOwner) {
            playlistLoveAdapter.submitList(it)
        }

        viewModelUser.playlistUser.observe(viewLifecycleOwner) {
            playlistUserAdapter.submitList(it)
        }
    }

    private fun initViewModel() {
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        binding.rcvPlaylistUser.setAdapterLinearVertical(playlistUserAdapter)
        binding.rcvPlaylistLove.setAdapterLinearVertical(playlistLoveAdapter)
    }

    private fun handleEvent() {
        binding.tabPlaylistUser.setOnClickListener {
            binding.scroll.post {
                binding.scroll.smoothScrollTo(0, binding.layoutPlaylistUser.top)
                binding.tvUnderlinedUser.visibility = View.VISIBLE
                binding.tvUnderlinedLove.visibility = View.INVISIBLE
            }
        }
        binding.tabPlaylistLove.setOnClickListener {
            binding.scroll.post {
                binding.scroll.smoothScrollTo(0, binding.layoutPlaylistLove.top)
                binding.tvUnderlinedUser.visibility = View.INVISIBLE
                binding.tvUnderlinedLove.visibility = View.VISIBLE
            }
        }
        binding.btnInformation.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    InformationActivity::class.java
                )
            )
        }
        binding.btnTrackDown.setOnClickListener {
            startSongUser(
                Constant.DOWN,
                binding.tvDown.text.toString()
            )
        }
        binding.btnListenAgain.setOnClickListener {
            if (user != null) {
                startSongUser(
                    Constant.AGAIN,
                    binding.tvAgain.text.toString()
                )
            } else {
                SnackBarManager.showMessage(binding.imageView19, MusicFragment.NOT_LOGIN)
            }
        }
        binding.btnOpenBottomSheet.setOnClickListener { checkUserLogin(0) }
        binding.btnOpenBottomSheetSelect.setOnClickListener { checkUserLogin(1) }
        binding.btnOpenBottomSheetLove.setOnClickListener { openBottomSheet() }
        binding.btnLove.setOnClickListener { startSongDetail() }
        binding.btnLogin.setOnClickListener { openBottomSheetLogin() }
        binding.includeLayout1.btnLayoutBottomPause.setOnClickListener { onCheckPlayMusic() }
    }

    private fun checkUserLogin(id: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            when (id) {
                0 -> openBottomSheetCreatePlaylist()
                1 -> openBottomSheetSelectPlaylist()
            }
        } else {
            openBottomSheetLogin()
        }
    }

    private fun startSongDetail() {
        if (user != null) {
            val intent = Intent(requireContext(), SongDetailActivity::class.java)
            intent.putExtra(Constant.KEY_INTENT_ITEM, songsLove.getOrNull(0))
            startActivity(intent)
        } else {
            openBottomSheetLogin()
        }
    }

    private fun startSongUser(s: String, name: String) {
        val intent = Intent(requireContext(), SongUserActivity::class.java)
        intent.putExtra(Constant.KEY_INTENT_ITEM, s)
        intent.putExtra(Constant.KEY_NAME, name)
        startActivity(intent)
    }

    private fun openBottomSheetLogin() {
        val bottomSheetLogin = BottomSheetLogin()
        bottomSheetLogin.show(parentFragmentManager, bottomSheetLogin.tag)
    }

    private fun openBottomSheet() {
        val bottomSheet = BottomSheetSelect(::onItemClickBottomSheetLove, "playlist_love")
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun openBottomSheetCreatePlaylist() {
        val bottomSheet = BottomSheetPlaylist(::onItemClickBottomSheetUser)
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun openBottomSheetSelectPlaylist() {
        val bottomSheet = BottomSheetSelect(::onItemClickBottomSheetUser, "playlist_user")
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    fun initSongView() {
        val song = GetValue.getSong(sharedPreferences)
        binding.includeLayout1.song = song
    }

    private fun initMusicView() {
        val isPlaying = sharedPreferences.getBoolean(Constant.KEY_PLAY_CLICK, false)
        if (isPlaying) {
            binding.includeLayout1.btnLayoutBottomPause.setImageResource(R.drawable.ic_pause_)
        } else {
            binding.includeLayout1.btnLayoutBottomPause.setImageResource(R.drawable.ic_play_bottom_sheet)
        }
    }

    private fun onCheckPlayMusic() {
        var isPlaySelected: Boolean by BooleanProperty(
            sharedPreferences,
            Constant.KEY_PLAY_CLICK,
            false
        )
        isPlaySelected = if (musicService?.isPlaying()!!) {
            musicService?.pause()
            binding.includeLayout1.btnLayoutBottomPause.setImageResource(R.drawable.ic_play_bottom_sheet)
            false
        } else {
            musicService?.start()
            binding.includeLayout1.btnLayoutBottomPause.setImageResource(R.drawable.ic_pause_)
            true
        }
    }

    private fun onItemClickBottomSheetLove() {
        viewModelLove.fetchPlaylists()
    }

    private fun onItemClickBottomSheetUser() {
        viewModelUser.fetchPlaylistsUser()
    }

    private fun onItemClick(boolean: Boolean, any: Any) {

    }

    override fun onStart() {
        super.onStart()
        initSongView()
        val intent = Intent(activity, MusicService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        viewModel.initValueUser()
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