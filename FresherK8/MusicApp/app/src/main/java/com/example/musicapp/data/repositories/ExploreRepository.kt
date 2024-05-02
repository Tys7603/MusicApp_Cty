package com.example.musicapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.repositories.PlaylistRepository
import com.example.musicapp.data.source.remote.ApiClient
import com.example.musicapp.data.source.remote.ApiService
import com.example.musicapp.shared.utils.constant.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreRepository(private val apiService: ApiService) {

    fun getListPlaylist() : ArrayList<Playlist> {

        val result = ArrayList<Playlist>()

        apiService.getListPlaylist().enqueue(object :
            Callback<PlaylistRepository> {
            override fun onResponse(
                call: Call<PlaylistRepository>,
                response: Response<PlaylistRepository>
            ) {
                if (response.isSuccessful) {
                    if (Constant.STATUS == response.body()?.status) {
                        result.addAll(response.body()!!.playlists)
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                } else {
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<PlaylistRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
        return result
    }
}