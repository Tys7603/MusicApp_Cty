package com.example.musicapp.screen.main

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    private val musicFragment = MusicFragment()
    private val exploreFragment = ExploreFragment()
    private val musicVideoFragment = MusicVideoFragment()
    private val userFragment = MusicFragment()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private var currentFragmentTag = ""

    companion object {
        const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1
        const val MUSIC = "Music"
        const val EXPLORE = "Explore"
        const val MV = "MV"
        const val USER = "User"
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
        fragmentManager(MUSIC)
        switchFragment()
        createService()
        switchUserIntent()

        if (!checkPermission(this)) {
            requestPermission(this)
        }

        if (!isNotificationPermissionGranted(this)) {
            requestNotificationPermission(this, NOTIFICATION_PERMISSION_REQUEST_CODE);
        }

    }

    private fun switchUserIntent() {
        val checkUser = intent.getBooleanExtra(Constant.KEY_USER, false)
        val checkSongUser = intent.getBooleanExtra(Constant.KEY_SONG_USER, false)

        if (checkUser) {
            fragmentManager(USER)
            binding.bottomNavigationView.selectedItemId = R.id.user_menu
        }
        if (checkSongUser) {
            fragmentManager(EXPLORE)
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

    private fun isNotificationPermissionGranted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            return notificationManager.areNotificationsEnabled()
        }
        return true // Các phiên bản Android thấp hơn tự động có quyền thông báo
    }

    private fun requestNotificationPermission(activity: Activity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    private fun fragmentManager(tag: String) {
        if (currentFragmentTag != tag) {
            currentFragmentTag = tag
            val fragment = when (tag) {
                MUSIC -> MusicFragment()
                EXPLORE -> ExploreFragment()
                MV -> MusicVideoFragment()
                USER -> UserFragment()
                else -> MusicFragment()
            }

            supportFragmentManager
                .beginTransaction()
                .replace(binding.frameLayout.id, fragment, tag)
                .commitNow()
        } else {
            scrollTop(tag)
        }
    }

    private fun scrollTop(tag: String) {
        when (val fragment = supportFragmentManager.findFragmentByTag(tag)) {
            is ExploreFragment -> fragment.scrollTop()
            is MusicVideoFragment -> fragment.scrollToFirstItem()
            is UserFragment -> fragment.scrollTop()
            else -> return
        }
    }

    private fun switchFragment() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.music_menu -> {
                    fragmentManager(MUSIC)
                    true
                }

                R.id.explore_menu -> {
                    fragmentManager(EXPLORE)
                    true
                }

                R.id.mv_menu -> {
                    fragmentManager(MV)
                    true
                }

                R.id.user_menu -> {
                    fragmentManager(USER)
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