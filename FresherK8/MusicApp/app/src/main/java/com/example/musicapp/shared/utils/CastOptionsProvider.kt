package com.example.musicapp.shared.utils

import android.content.Context
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.SessionProvider


class CastOptionsProvider : com.google.android.gms.cast.framework.OptionsProvider {

    override fun getCastOptions(p0: Context): CastOptions {
        val receiverId = "CC1AD845" // Your Receiver ID here
        return CastOptions.Builder()
            .setReceiverApplicationId(receiverId)
            .build()
    }

    override fun getAdditionalSessionProviders(p0: Context): MutableList<SessionProvider>? {
        return null
    }
}