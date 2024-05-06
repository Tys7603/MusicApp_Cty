package com.example.musicapp.shared.extension

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.musicapp.R

@BindingAdapter("imageUrl")
fun ImageView.loadImageUrl(url : String){
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}
// test
@BindingAdapter("imageUrl")
fun ImageView.loadImageUrlUser(uri : Uri){
    Glide.with(context)
        .load(uri)
        .centerCrop()
        .placeholder(R.drawable.avatar)
        .into(this)
}

fun ImageView.loadDingUrl(){
    Glide.with(context)
        .load("https://cdn.pixabay.com/animation/2023/03/20/02/45/02-45-27-186_512.gif")
        .centerCrop()
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}