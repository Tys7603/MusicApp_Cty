package com.example.musicapp.data.repositories

import android.util.Log
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.repositories.SongRepository
import com.example.musicapp.data.source.remote.ApiClient
import com.example.musicapp.data.source.remote.ApiService
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.scheduler.DataResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicRepository {
    fun getListSong() : DataResult<ArrayList<Song>>{
        return try {
            val response = ApiClient.apiService.getListSong()
            DataResult.Success(response.body()!!.songs)
        }catch (){
            DataResult.Error()
        }

//        ApiClient.apiService.getListSong().enqueue(object : Callback<SongRepository> {
//            override fun onResponse(
//                call: Call<SongRepository>,
//                response: Response<SongRepository>
//            ) {
//                if (response.isSuccessful) {
//                    if (Constant.STATUS == response.body()?.status) {
//                        result.addAll(response.body()!!.songs)
//                    } else {
//                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
//                    }
//                } else {
//                    Log.e(Constant.TAG_ERROR, "Call api failure")
//                }
//            }
//
//            override fun onFailure(call: Call<SongRepository>, t: Throwable) {
//                Log.e(Constant.TAG_ERROR, t.toString())
//            }
//
//        })
    }
}