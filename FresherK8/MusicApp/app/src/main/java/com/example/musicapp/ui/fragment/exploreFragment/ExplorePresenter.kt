package com.example.musicapp.ui.fragment.exploreFragment

import android.util.Log
import com.example.musicapp.network.ApiClient
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryAlbumLove
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryAlbumNew
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryCategories
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryPlaylist
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositorySongAgain
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositorySongRank
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryTopic
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
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositoryPlaylist>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListPlaylistMoodToday() {
        ApiClient.getApiService()?.getListPlaylistMoodToday()?.enqueue(object :
            Callback<RepositoryPlaylist> {
            override fun onResponse(
                call: Call<RepositoryPlaylist>,
                response: Response<RepositoryPlaylist>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListPlaylistMoodToday(response.body()!!.playlists)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositoryPlaylist>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListTopic() {
        ApiClient.getApiService()?.getListTopic()?.enqueue(object : Callback<RepositoryTopic> {
            override fun onResponse(
                call: Call<RepositoryTopic>,
                response: Response<RepositoryTopic>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListTopic(response.body()!!.topics)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositoryTopic>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListCategory() {
        ApiClient.getApiService()?.getListCategory()?.enqueue(object : Callback<RepositoryCategories> {
            override fun onResponse(
                call: Call<RepositoryCategories>,
                response: Response<RepositoryCategories>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListCategory(response.body()!!.categories)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositoryCategories>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListListenAgain(userID: Int) {
        ApiClient.getApiService()?.getListSongAgain(userID)?.enqueue(object : Callback<RepositorySongAgain> {
            override fun onResponse(
                call: Call<RepositorySongAgain>,
                response: Response<RepositorySongAgain>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListListenAgain(response.body()!!.songAgain)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositorySongAgain>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListAlbumLove() {
        ApiClient.getApiService()?.getListAlbumLove()?.enqueue(object : Callback<RepositoryAlbumLove> {
            override fun onResponse(
                call: Call<RepositoryAlbumLove>,
                response: Response<RepositoryAlbumLove>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListAlbumLove(response.body()!!.albumLoves)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositoryAlbumLove>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListAlbumNew() {
        ApiClient.getApiService()?.getListAlbumNew()?.enqueue(object : Callback<RepositoryAlbumNew> {
            override fun onResponse(
                call: Call<RepositoryAlbumNew>,
                response: Response<RepositoryAlbumNew>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListAlbumNew(response.body()!!.albumNews)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositoryAlbumNew>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListSongRank() {
        ApiClient.getApiService()?.getListSongRank()?.enqueue(object : Callback<RepositorySongRank> {
            override fun onResponse(
                call: Call<RepositorySongRank>,
                response: Response<RepositorySongRank>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView.onListSongRank(response.body()!!.songRanks)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<RepositorySongRank>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }


}