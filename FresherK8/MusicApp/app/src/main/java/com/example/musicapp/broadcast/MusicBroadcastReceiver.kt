package com.example.musicapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.screen.explore.ExploreFragment
import com.example.musicapp.screen.lyrics.LyricActivity
import com.example.musicapp.shared.utils.constant.Constant

class MusicBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Constant.UPDATE_LYRIC -> {
                val data = intent.getParcelableExtra<Song>(Constant.KEY_INTENT_ITEM)
                val activityIntent = Intent(context, LyricActivity::class.java).apply {
                    putExtra(Constant.KEY_INTENT_ITEM, data)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context?.startActivity(activityIntent)
            }
            Constant.UPDATE_SONG -> {
                Log.d("TAG", "onReceive: UPDATE_SONG received")
                (context as? AppCompatActivity)?.let { activity ->
                    val fragmentManager = activity.supportFragmentManager
                    val fragment = fragmentManager.findFragmentById(R.id.frame_layout) as? ExploreFragment
                    fragment?.initSongView()
                }
            }
        }
    }
}

