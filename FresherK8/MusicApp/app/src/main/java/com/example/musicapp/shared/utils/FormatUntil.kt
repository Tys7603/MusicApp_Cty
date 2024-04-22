package com.example.musicapp.shared.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object FormatUntil {
    @SuppressLint("SimpleDateFormat")
    fun formatTime(date: Int) : String{
        val simpleDateFormat = SimpleDateFormat("mm:ss")
        return simpleDateFormat.format(date)
    }
}