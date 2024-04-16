package com.example.musicapp.ui.fragment.exploreFragment

import android.util.Log
import com.example.musicapp.network.ApiClient
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryPlaylist
import com.example.musicapp.until.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExplorePresenter (val mView : ExploreContract.View) : ExploreContract.Presenter {
    override fun getListPlaylist() {
        ApiClient.getApiService()?.getListPlaylist()?.enqueue(object :
            Callback<RepositoryPlaylist> {
            override fun onResponse(
                call: Call<RepositoryPlaylist>,
                response: Response<RepositoryPlaylist>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListPlaylist(response.body()!!.playlists)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call api status ")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure ")
                }
            }

            override fun onFailure(call: Call<RepositoryPlaylist>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListTopicAndCategory() {
        TODO("Not yet implemented")
    }
}