package com.example.musicapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.musicapp.data.model.Song
import com.example.musicapp.screen.lyrics.LyricActivity
import com.example.musicapp.shared.utils.constant.Constant

class MusicBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Constant.UPDATE_LYRIC) {
            val data = intent.getParcelableExtra<Song>(Constant.KEY_INTENT_ITEM)

            val activityIntent = Intent(
                context,
                LyricActivity::class.java
            ).apply {
                putExtra(Constant.KEY_INTENT_ITEM, data)
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context!!.startActivity(activityIntent)
        }
    }
}
