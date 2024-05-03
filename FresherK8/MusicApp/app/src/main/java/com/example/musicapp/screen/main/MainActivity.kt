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
import com.example.musicapp.screen.music.MusicFragment
import com.example.musicapp.screen.user.UserFragment
import com.example.musicapp.service.MusicService


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

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

        switchFragment()
        fragmentManager(MusicFragment())
        createService()
        if (!checkPermission(this)) {
            requestPermission(this)
        }
        if (!checkNotificationPermission(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requestNotificationPermission(this)
            }
        }
    }


    // Kiểm tra xem quyền đã được cấp hay chưa
    private fun checkPermission(activity: Activity?): Boolean {
        val result =
            ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    // Hiển thị hộp thoại yêu cầu cấp quyền
    private fun requestPermission(activity: Activity?) {
        ActivityCompat.requestPermissions(
            activity!!, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        )
    }

    private fun checkNotificationPermission(activity: Activity?): Boolean {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.notificationChannels.any { it.importance != NotificationManager.IMPORTANCE_NONE }
        } else {
            // For older Android versions, simply return true as they don't have notification channels
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestNotificationPermission(activity: Activity?) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(intent)
    }


    private fun fragmentManager(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commit()
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
        sharedPreferences.edit().clear().apply()
    }
}