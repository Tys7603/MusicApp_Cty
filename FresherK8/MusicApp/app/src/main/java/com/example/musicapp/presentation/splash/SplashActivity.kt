package com.example.musicapp.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.widget.Button
import android.widget.RemoteViews
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.MyApplication.Companion.CHANMEL_ID
import com.example.musicapp.R
import com.example.musicapp.presentation.main.MainActivity
import com.example.musicapp.service.MusicService.Companion.CHANNEL_ID

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Handler(Looper.getMainLooper()!!).postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        },3000)

        findViewById<Button>(R.id.button).setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.song1)
//            RemoteViews(packageName, R.layout.activity_splash)
            val notification = NotificationCompat.Builder(this, CHANMEL_ID)
                .setSmallIcon(R.drawable.ic_avatar)
                .setContentTitle("Chạy Ngay Đi")
                .setContentText("Sơn Tùng M-TP")
                .setLargeIcon(bitmap)
                .setSound(null)
                .addAction(R.drawable.ic_skip_back, "Play", null)
                .addAction(R.drawable.ic_play_black, "Play", null)
                .addAction(R.drawable.ic_skip_next, "Play", null)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0)
                        .setMediaSession(MediaSessionCompat(this, "tag").sessionToken)
                )
                .build()
            NotificationManagerCompat.from(this).notify(1,notification)
        }

    }
}