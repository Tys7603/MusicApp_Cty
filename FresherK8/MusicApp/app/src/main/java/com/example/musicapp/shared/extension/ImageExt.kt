package com.example.musicapp.shared.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.musicapp.R

fun ImageView.loadImageUrl(url : String){
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}