package com.example.musicapp.shared.utils.format

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object FormatUtils {
    @SuppressLint("SimpleDateFormat")
    fun formatTime(date: Int) : String{
        val simpleDateFormat = SimpleDateFormat("mm:ss")
        return simpleDateFormat.format(date)
    }
}