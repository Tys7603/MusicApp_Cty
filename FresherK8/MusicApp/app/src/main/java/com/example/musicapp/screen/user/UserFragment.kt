package com.example.musicapp.screen.user

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.musicapp.R
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.FragmentUserBinding
import com.example.musicapp.screen.base.BaseService
import com.example.musicapp.screen.main.MainActivity
import com.example.musicapp.screen.music.MusicViewModel
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
import com.example.musicapp.shared.utils.ListDefault
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserFragment : Fragment(), BaseService {
    private val viewModelMusic: MusicViewModel by viewModel()
    private var musicService: MusicService? = null
    private var isServiceBound = false
    private val viewModel: UserViewModel by viewModel()
    private val viewModelUser: PlaylistUserViewModel by viewModel()
    private val viewModelLove: PlaylistLoveViewModel by viewModel()
    private var songsLove: MutableList<Song> = mutableListOf()
    private val playlistLoveAdapter = PlaylistLoveAdapter(::onItemClick, 2)
    private val playlistUserAdapter = PlaylistUserAdapter(::onItemClick, 2)

    private val binding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    companion object{
        const val NO_SONG = "0 bài hát"
    }

    private val serviceConnection = object : ServiceConnection {
        // kết nối thành công lấy được đối tượng IBinder để try cập music service
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true
            musicService?.musicService(this@UserFragment)
            mediaPrepared()
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
        initRecyclerView()
        handleEventViewModel()
        showLoading()
        showProgressBar(true)
    }

    private fun showProgressBar(boolean: Boolean){
        if (boolean){
            binding.includeLayout1.progressBar4.visibility = View.VISIBLE
            binding.includeLayout1.btnLayoutBottomPause.visibility = View.INVISIBLE
        }else{
            binding.includeLayout1.progressBar4.visibility = View.INVISIBLE
            binding.includeLayout1.btnLayoutBottomPause.visibility = View.VISIBLE
        }
    }

    private fun mediaPrepared(){
        if (musicService?.isMediaPrepared() == true){
            binding.includeLayout1.progressBar4.visibility = View.INVISIBLE
            binding.includeLayout1.btnLayoutBottomPause.visibility = View.VISIBLE
        }
    }

    private fun fetchData() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            viewModelUser.fetchPlaylistsUser(user.uid)
            viewModelLove.fetchPlaylists(user.uid)
        }else{
            binding.layoutPlaylistUserLoading.visibility = View.INVISIBLE
            binding.layoutPlaylistUserEmpty.visibility = View.VISIBLE
            binding.layoutPlaylistLoveLoading.visibility = View.INVISIBLE
            binding.layoutPlaylistLoveEmpty.visibility = View.VISIBLE
            binding.layoutSongUserEmpty.visibility = View.INVISIBLE
        }
    }

    fun scrollTop() {
        binding.scroll.post {
            binding.scroll.smoothScrollTo(0, binding.imgAvatar.top)
        }
    }

    private fun showLoading() {
        binding.layoutPlaylistLoveLoading.visibility = View.VISIBLE
        binding.layoutPlaylistUserLoading.visibility = View.VISIBLE
        binding.layoutPlaylistUserEmpty.visibility = View.INVISIBLE
        binding.layoutPlaylistLoveEmpty.visibility = View.INVISIBLE
        binding.layoutSongUserEmpty.visibility = View.INVISIBLE
    }

    private fun handleEventViewModel() {
        viewModel.songsLove.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                songsLove = it.shuffled().toMutableList()
                binding.imgLove.loadImageUrl(songsLove[0].image)
            }
        }

        viewModelLove.playlists.observe(viewLifecycleOwner) {
            handlerPostDelay {
                binding.layoutPlaylistLoveLoading.visibility = View.INVISIBLE
                if (it.isNotEmpty()) {
                    playlistLoveAdapter.submitList(it)
                    binding.layoutSongUserEmpty.visibility = View.GONE
                    binding.layoutPlaylistLoveEmpty.visibility = View.GONE
                    binding.rcvPlaylistLove.visibility = View.VISIBLE
                } else {
                    binding.rcvPlaylistLove.visibility = View.INVISIBLE
                    binding.layoutSongUserEmpty.visibility = View.VISIBLE
                }
            }
        }

        viewModelUser.playlistUser.observe(viewLifecycleOwner) {
            handlerPostDelay {
                binding.layoutPlaylistUserLoading.visibility = View.INVISIBLE
                if (it.isNotEmpty()) {
                    playlistUserAdapter.submitList(it)
                    binding.layoutPlaylistUserEmpty.visibility = View.GONE
                    binding.rcvPlaylistUser.visibility = View.VISIBLE

                } else {
                    binding.rcvPlaylistUser.visibility = View.INVISIBLE
                    binding.layoutPlaylistUserEmpty.visibility = View.VISIBLE
                }
            }
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
        binding.btnLogout.setOnClickListener {logout()}
        binding.btnTrackDown.setOnClickListener {
            startSongUser(
                Constant.DOWN,
                binding.tvDown.text.toString()
            )
        }
        binding.btnListenAgain.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                startSongUser(
                    Constant.AGAIN,
                    binding.tvAgain.text.toString()
                )
            } else {
                openBottomSheetLogin()
            }
        }
        binding.btnAddPlaylistUserFragment.setOnClickListener { openBottomSheetCreatePlaylistFragment() }
        binding.btnExplore.setOnClickListener { startExplore() }
        binding.btnOpenBottomSheet.setOnClickListener { checkUserLogin(0) }
        binding.btnOpenBottomSheetSelect.setOnClickListener { checkUserLogin(1) }
        binding.btnOpenBottomSheetLove.setOnClickListener { openBottomSheetCheckUser() }
        binding.btnLove.setOnClickListener { startSongDetail() }
        binding.btnLogin.setOnClickListener { openBottomSheetLogin() }
        binding.includeLayout1.btnLayoutBottomPause.setOnClickListener { onCheckPlayMusic() }
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        binding.layoutPlaylistUserEmpty.visibility = View.VISIBLE
        binding.layoutPlaylistLoveEmpty.visibility = View.VISIBLE
        binding.layoutSongUserEmpty.visibility = View.GONE
        binding.rcvPlaylistUser.visibility = View.GONE
        binding.rcvPlaylistLove.visibility = View.GONE
        binding.tvAgainQuantity.text = NO_SONG
        binding.tvQuantityTrackDown.text = NO_SONG
        binding.tvLoveQuantity.text = NO_SONG
        binding.imgLoveSub.setBackgroundResource(R.drawable.ic_heart)
        binding.imgLove.visibility = View.INVISIBLE
        viewModel.initValueUser()
    }

    private fun startExplore() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(Constant.KEY_SONG_USER, true)
        startActivity(intent)
        requireActivity().finish()
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
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            if (songsLove.isNotEmpty()){
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, songsLove.getOrNull(0))
                startActivity(intent)
            }else{
                SnackBarManager.showMessage(binding.imageView20, "Không có bài hát hát yêu thích")
            }
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

    private fun openBottomSheetCreatePlaylistFragment() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            openBottomSheetCreatePlaylist()
        }else{
            openBottomSheetLogin()
        }
    }

    private fun openBottomSheetCheckUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            openBottomSheet()
        }else{
            openBottomSheetLogin()
        }
    }

    private fun openBottomSheetLogin() {
        val bottomSheetLogin = BottomSheetLogin(::onLoginGoogle)
        bottomSheetLogin.show(parentFragmentManager, bottomSheetLogin.tag)
    }

    private fun onLoginGoogle(boolean: Boolean) {
       if (boolean){
           binding.layoutLoginGoogle.visibility = View.VISIBLE
           binding.scroll.visibility = View.INVISIBLE
           binding.includeLayout1.root.visibility = View.INVISIBLE
       }else{
           viewModel.initValueUser()
           fetchData()
           viewModel.fetchSongLocal()
           viewModel.fetchSongAgain()
           viewModel.fetchSongLove()
           binding.layoutLoginGoogle.visibility = View.GONE
           binding.scroll.visibility = View.VISIBLE
           binding.includeLayout1.root.visibility = View.VISIBLE
           binding.layoutPlaylistLoveEmpty.visibility = View.GONE
       }
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
        if (song != null){
            binding.includeLayout1.song = song
        }else{
            binding.includeLayout1.song = ListDefault.initSong()
        }
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
            insertSongAgain()
            binding.includeLayout1.btnLayoutBottomPause.setImageResource(R.drawable.ic_pause_)
            true
        }
    }

    private fun onItemClickBottomSheetLove() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            viewModelLove.fetchPlaylists(user.uid)
        }
    }

    private fun onItemClickBottomSheetUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            viewModelUser.fetchPlaylistsUser(user.uid)
        }
    }

    private fun onItemClick(boolean: Boolean, any: Any) {
        when(any){
            is Playlist -> {
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, any)
                startActivity(intent)
            }

            is PlaylistUser -> {
                val intent = Intent(requireContext(), SongDetailActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, any)
                startActivity(intent)
            }
        }
    }

    private fun insertSongAgain(){
        val user = FirebaseAuth.getInstance().currentUser
        val song = GetValue.getSong(sharedPreferences)
        user?.let {
            viewModelMusic.addSongAgain(user.uid, song!!.id)
        }
    }

    private fun handlerPostDelay(listener: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            listener.invoke()
        }, 500)
    }

    override fun onStart() {
        super.onStart()
        initSongView()
        val intent = Intent(activity, MusicService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        viewModel.initValueUser()
        fetchData()
        viewModel.fetchSongLocal()
    }

    override fun onMediaPrepared() {
        showProgressBar(false)
        musicService?.start()
    }

    override fun onNextMusic() = Unit

    override fun onBackMusic() = Unit

    override fun onPlayMusic() = Unit

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            requireContext().unbindService(serviceConnection)
            isServiceBound = false
        }
        musicService = null
    }
}