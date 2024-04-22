package com.example.musicapp.ui.fragment.musicFragment

import android.util.Log
import com.example.musicapp.network.ApiClient
import com.example.musicapp.ui.fragment.musicFragment.repository.RepositorySong
import com.example.musicapp.constant.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicPresenter(private val mView : MusicContract.View) : MusicContract.Presenter {
    override fun getListSong() {
        ApiClient.getApiService()?.getListSong()?.enqueue(object : Callback<RepositorySong> {
            override fun onResponse(
                call: Call<RepositorySong>,
                response: Response<RepositorySong>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListSong(response.body()!!.songs)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositorySong>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }
}