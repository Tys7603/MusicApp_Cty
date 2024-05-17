package com.example.musicapp.shared.extension

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.musicapp.R

@BindingAdapter("imageUrl")
fun ImageView.loadImageUrl(url : String){
    if (url.isEmpty()) {
        this.setImageResource(R.drawable.img_placeholder)
    } else {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.img_placeholder)
            .into(this)
    }
}

@BindingAdapter("imageUrlUser")
fun ImageView.loadImageUrlUser(url : String){
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.img)
        .into(this)
}


@BindingAdapter("imageUrlMV")
fun ImageView.loadImageMVUrl(url : String){
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.img_placeholder_mv)
        .into(this)
}

@BindingAdapter("imageUri")
fun ImageView.loadImageUrlUser(uri : Uri){
    Glide.with(context)
        .load(uri)
        .centerCrop()
        .placeholder(R.drawable.avatar)
        .into(this)
}

fun ImageView.loadDingUrl(){
    Glide.with(context)
        .load("https://thebowlcut.com/cdn/shop/t/41/assets/loading.gif?v=157493769327766696621701744369")
        .centerCrop()
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}