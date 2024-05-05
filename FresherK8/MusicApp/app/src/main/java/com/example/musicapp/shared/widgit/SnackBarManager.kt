package com.example.musicapp.shared.widgit

import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarManager {
    fun showMessage(view: View?, message: String?) {
        Handler(Looper.getMainLooper()).post {
            Snackbar.make(
                view!!,
                message!!, Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}