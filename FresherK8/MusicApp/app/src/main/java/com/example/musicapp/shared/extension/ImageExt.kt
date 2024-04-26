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

fun ImageView.loadDingUrl(){
    Glide.with(context)
        .load("https://cdn.pixabay.com/animation/2023/03/20/02/45/02-45-27-186_512.gif")
        .centerCrop()
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}