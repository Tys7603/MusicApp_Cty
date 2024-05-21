package com.example.musicapp.shared.widget

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout

object ProgressBarManager {
    fun showProgressBar(progressBar: ProgressBar, linearLayout: LinearLayout){
        progressBar.visibility = View.VISIBLE
        linearLayout.visibility = View.VISIBLE
    }

    fun dismissProgressBar(progressBar: ProgressBar, linearLayout: LinearLayout){
        progressBar.visibility = View.GONE
        linearLayout.visibility = View.GONE
    }

    fun showProgressBarPlay(progressBar: ProgressBar, linearLayout: LinearLayout, imageView: ImageView){
        progressBar.visibility = View.VISIBLE
        linearLayout.visibility = View.VISIBLE
        imageView.visibility = View.INVISIBLE
    }

    fun dismissProgressBarPlay(progressBar: ProgressBar, linearLayout: LinearLayout, imageView: ImageView){
        progressBar.visibility = View.GONE
        linearLayout.visibility = View.GONE
        imageView.visibility = View.VISIBLE
    }
}