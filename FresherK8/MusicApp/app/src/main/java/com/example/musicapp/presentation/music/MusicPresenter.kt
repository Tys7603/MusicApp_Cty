package com.example.musicapp.presentation.music

import android.util.Log
import com.example.musicapp.network.ApiClient
import com.example.musicapp.data.repositories.SongRepository
import com.example.musicapp.contants.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicPresenter : MusicContract.Presenter {

    private var mView: MusicContract.View? = null

    override fun getListSong() {
        ApiClient.getApiService()?.getListSong()?.enqueue(object : Callback<SongRepository> {
            override fun onResponse(
                call: Call<SongRepository>,
                response: Response<SongRepository>
            ) {
                if (response.isSuccessful) {
                    if (Constant.STATUS == response.body()?.status) {
                        mView?.onListSong(response.body()!!.songs)
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                } else {
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<SongRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: MusicContract.View?) {
        this.mView = view
    }

    override fun onDestroy() {
        this.mView = null
    }
}