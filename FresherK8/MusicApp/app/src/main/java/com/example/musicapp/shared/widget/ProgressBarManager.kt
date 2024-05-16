package com.example.musicapp.shared.widget

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar

object ProgressBarManager {
    fun showProgressBar(progressBar: ProgressBar, linearLayout: LinearLayout){
        progressBar.visibility = View.VISIBLE
        linearLayout.visibility = View.VISIBLE
    }

    fun dismissProgressBar(progressBar: ProgressBar, linearLayout: LinearLayout){
        progressBar.visibility = View.GONE
        linearLayout.visibility = View.GONE
    }
}