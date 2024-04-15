package com.example.musicapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.ui.fragment.ExploreFragment
import com.example.musicapp.ui.fragment.MusicFragment
import com.example.musicapp.ui.fragment.UserFragment
import com.example.musicapp.service.MusicService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        switchFragment()
        fragmentManager(MusicFragment())
        createService()
    }

    private fun fragmentManager(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commit()
    }

    private fun switchFragment(){
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

    private fun createService(){
        val serviceIntent = Intent(this, MusicService::class.java)
        startService(serviceIntent)
    }
}