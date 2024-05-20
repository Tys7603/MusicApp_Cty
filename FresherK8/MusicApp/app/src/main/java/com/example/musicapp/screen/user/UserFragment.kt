package com.example.musicapp.screen.user

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.databinding.FragmentUserBinding
import com.example.musicapp.screen.account.adapter.AccountPageAdapter
import com.example.musicapp.screen.account.information.InformationActivity
import com.example.musicapp.screen.songDown.SongDownActivity
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.screen.user.adapter.PlaylistPageAdapter
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.utils.BooleanProperty
import com.example.musicapp.shared.utils.GetValue
import com.example.musicapp.shared.utils.constant.Constant
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserFragment : Fragment() {

    private var musicService: MusicService? = null
    private var isServiceBound = false
    private val viewModel : UserViewModel by viewModel()

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
        handleEventViewModel()
        initViewModel()
        initTabLayout()
        initMusicView()
    }

    private fun initViewModel() {
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun handleEventViewModel() {
        binding.btnInformation.setOnClickListener { startActivity(Intent(requireContext(), InformationActivity::class.java)) }
    }

    private fun handleEvent() {
        binding.btnTrackDown.setOnClickListener {startActivity(Intent(requireContext(), SongDownActivity::class.java))}
        binding.btnLogin.setOnClickListener { openBottomSheetLogin() }
        binding.includeLayout1.btnLayoutBottomPause.setOnClickListener { onCheckPlayMusic() }
    }

    private fun openBottomSheetLogin() {
        val bottomSheetLogin = BottomSheetLogin()
        bottomSheetLogin.show(parentFragmentManager, bottomSheetLogin.tag)
    }

    private fun initTabLayout() {
        val pagerAdapter = PlaylistPageAdapter(requireActivity())
        binding.viewPagerUser.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayoutUser, binding.viewPagerUser) { tab, position ->
            when (position) {
                0 -> tab.text = Constant.PLAYLIST_USER
                1 -> tab.text = Constant.PLAYLIST_LOVE
            }
        }.attach()

        binding.viewPagerUser.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateViewPagerHeightForCurrentPage(position)
            }
        })
    }

    private fun updateViewPagerHeightForCurrentPage(position: Int) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val view = binding.viewPagerUser.findViewWithTag<View>("f$position") ?: return@post
            view.post {
                val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                view.measure(wMeasureSpec, hMeasureSpec)
                if (binding.viewPagerUser.layoutParams.height != view.measuredHeight) {
                    binding.viewPagerUser.layoutParams = binding.viewPagerUser.layoutParams.also { lp ->
                        lp.height = view.measuredHeight
                    }
                }
            }
        }
    }


    private fun initSongView(){
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