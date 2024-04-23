package com.example.musicapp.presentation.songList

import android.util.Log
import com.example.musicapp.contants.Constant
import com.example.musicapp.data.repositories.SongRepository
import com.example.musicapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongListPresenter : SongListContract.Presenter {

    private var mView : SongListContract.View? = null

    override fun getListSongTopic(id : Int) {
        ApiClient.getApiService()?.getListSongTopicById(id)?.enqueue(object :
            Callback<SongRepository> {
            override fun onResponse(
                call: Call<SongRepository>,
                response: Response<SongRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        response.body()?.songs?.let { mView?.onListSong(it) }
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<SongRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListSongPlaylist(id: Int) {
        ApiClient.getApiService()?.getListSongPlaylistById(id)?.enqueue(object :
            Callback<SongRepository> {
            override fun onResponse(
                call: Call<SongRepository>,
                response: Response<SongRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        response.body()?.songs?.let { mView?.onListSong(it) }
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
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

    override fun setView(view: SongListContract.View?) {
        this.mView = view
    }

    override fun onDestroy() {
       this.mView = null
    }
}