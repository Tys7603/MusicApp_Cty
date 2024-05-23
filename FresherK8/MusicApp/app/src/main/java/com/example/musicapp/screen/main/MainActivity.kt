package com.example.musicapp.screen.main

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.screen.explore.ExploreFragment
import com.example.musicapp.screen.lyrics.LyricActivity
import com.example.musicapp.screen.music.MusicFragment
import com.example.musicapp.screen.musicVideo.MusicVideoFragment
import com.example.musicapp.screen.user.UserFragment
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.utils.OnChangeListener
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.KEY_REFRESH_LYRIC


class MainActivity : AppCompatActivity(), OnChangeListener {
    private val musicFragment by lazy {
        MusicFragment()
    }
    private val musicVideoFragment by lazy {
        MusicVideoFragment()
    }
    private val exploreFragment by lazy {
        ExploreFragment()
    }
    private val userFragment by lazy {
        UserFragment()
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private var currentFragment: Fragment? = null

    companion object {
        const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fragmentManager(musicFragment)
        switchFragment()
        createService()
        switchUserIntent()

        if (!checkPermission(this)) {
            requestPermission(this)
        }
        if (!checkNotificationPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requestNotificationPermission()
            }
        }

    }

    private fun switchUserIntent() {
        val checkUser = intent.getBooleanExtra(Constant.KEY_USER, false)
        val checkSongUser = intent.getBooleanExtra(Constant.KEY_SONG_USER, false)

        if (checkUser) {
            fragmentManager(UserFragment())
            binding.bottomNavigationView.selectedItemId = R.id.user_menu
        }
        if (checkSongUser) {
            fragmentManager(ExploreFragment())
            binding.bottomNavigationView.selectedItemId = R.id.explore_menu
        }
    }

    private fun checkPermission(activity: Activity?): Boolean {
        val result =
            ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(activity: Activity?) {
        ActivityCompat.requestPermissions(
            activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        )
    }

    private fun checkNotificationPermission(): Boolean {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.notificationChannels.any { it.importance != NotificationManager.IMPORTANCE_NONE }
        } else {
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestNotificationPermission() {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(intent)
    }

    private fun fragmentManager(fragment: Fragment) {
        if (currentFragment != fragment) {
            currentFragment = fragment
            supportFragmentManager
                .beginTransaction()
                .replace(binding.frameLayout.id, fragment)
                .commit()
        } else {
            scrollTop()
        }
    }

    private fun scrollTop() {
        when (currentFragment) {
            is ExploreFragment -> {
                (currentFragment as ExploreFragment).scrollTop()
            }

            is MusicVideoFragment -> {
                (currentFragment as MusicVideoFragment).scrollToFirstItem()
            }

            is UserFragment -> {
                (currentFragment as UserFragment).scrollTop()
            }
        }
    }

    private fun switchFragment() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.music_menu -> {
                    fragmentManager(MusicFragment())
                    true
                }

                R.id.explore_menu -> {
                    fragmentManager(ExploreFragment())
                    true
                }

                R.id.mv_menu -> {
                    fragmentManager(MusicVideoFragment())
                    true
                }

                R.id.user_menu -> {
                    fragmentManager(UserFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun createService() {
        val serviceIntent = Intent(this, MusicService::class.java)
        startService(serviceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.edit().putBoolean(Constant.KEY_PLAY_CLICK, false).apply()
        sharedPreferences.edit().putBoolean(Constant.KEY_TAB_MUSIC, false).apply()
        sharedPreferences.edit().putBoolean(Constant.KEY_SHUFFLE, false).apply()
        sharedPreferences.edit().putBoolean(Constant.KEY_AUTO_RESTART, false).apply()
        sharedPreferences.edit().putBoolean(Constant.KEY_SONG_LOCAL, false).apply()
    }

    override fun onSongChanged() {
        val exploreFragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout) as? ExploreFragment
        val userFragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout) as? UserFragment
        exploreFragment?.initSongView()
        userFragment?.initSongView()
    }

    override fun onInitValueSong() {
        val intent = Intent(this, LyricActivity::class.java)
        intent.putExtra(
            Constant.KEY_INTENT_ITEM,
            sharedPreferences.getString(Constant.KEY_SONG, "")
        )
        intent.putExtra(KEY_REFRESH_LYRIC, "Fragment")
        startActivity(intent)
    }
}