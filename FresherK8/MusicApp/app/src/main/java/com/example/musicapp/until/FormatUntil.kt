package com.example.musicapp.until

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object FormatUntil {
    @SuppressLint("SimpleDateFormat")
    fun formatTime(date: Int) : String{
        val simpleDateFormat = SimpleDateFormat("mm:ss")
        return simpleDateFormat.format(date)
    }
}