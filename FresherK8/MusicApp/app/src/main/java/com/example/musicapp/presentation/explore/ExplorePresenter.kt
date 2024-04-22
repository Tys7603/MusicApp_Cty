package com.example.musicapp.presentation.explore

import android.util.Log
import com.example.musicapp.network.ApiClient
import com.example.musicapp.data.repositories.AlbumLoveRepository
import com.example.musicapp.data.repositories.AlbumNewRepository
import com.example.musicapp.data.repositories.CategoriesRepository
import com.example.musicapp.data.repositories.PlaylistRepository
import com.example.musicapp.data.repositories.SongAgainRepository
import com.example.musicapp.data.repositories.SongRankRepository
import com.example.musicapp.data.repositories.TopicRepository
import com.example.musicapp.contants.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExplorePresenter : ExploreContract.Presenter {

    private var mView : ExploreContract.View? = null

    override fun getListPlaylist() {
        ApiClient.getApiService()?.getListPlaylist()?.enqueue(object :
            Callback<PlaylistRepository> {
            override fun onResponse(
                call: Call<PlaylistRepository>,
                response: Response<PlaylistRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListPlaylist(response.body()!!.playlists)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<PlaylistRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListPlaylistMoodToday() {
        ApiClient.getApiService()?.getListPlaylistMoodToday()?.enqueue(object :
            Callback<PlaylistRepository> {
            override fun onResponse(
                call: Call<PlaylistRepository>,
                response: Response<PlaylistRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListPlaylistMoodToday(response.body()!!.playlists)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<PlaylistRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListTopic() {
        ApiClient.getApiService()?.getListTopic()?.enqueue(object : Callback<TopicRepository> {
            override fun onResponse(
                call: Call<TopicRepository>,
                response: Response<TopicRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListTopic(response.body()!!.topics)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<TopicRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListCategory() {
        ApiClient.getApiService()?.getListCategory()?.enqueue(object : Callback<CategoriesRepository> {
            override fun onResponse(
                call: Call<CategoriesRepository>,
                response: Response<CategoriesRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListCategory(response.body()!!.categories)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<CategoriesRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListListenAgain(userID: Int) {
        ApiClient.getApiService()?.getListSongAgain(userID)?.enqueue(object : Callback<SongAgainRepository> {
            override fun onResponse(
                call: Call<SongAgainRepository>,
                response: Response<SongAgainRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListListenAgain(response.body()!!.songAgain)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<SongAgainRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListAlbumLove() {
        ApiClient.getApiService()?.getListAlbumLove()?.enqueue(object : Callback<AlbumLoveRepository> {
            override fun onResponse(
                call: Call<AlbumLoveRepository>,
                response: Response<AlbumLoveRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListAlbumLove(response.body()!!.albumLoves)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<AlbumLoveRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListAlbumNew() {
        ApiClient.getApiService()?.getListAlbumNew()?.enqueue(object : Callback<AlbumNewRepository> {
            override fun onResponse(
                call: Call<AlbumNewRepository>,
                response: Response<AlbumNewRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListAlbumNew(response.body()!!.albumNews)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<AlbumNewRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun getListSongRank() {
        ApiClient.getApiService()?.getListSongRank()?.enqueue(object : Callback<SongRankRepository> {
            override fun onResponse(
                call: Call<SongRankRepository>,
                response: Response<SongRankRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        mView?.onListSongRank(response.body()!!.songRanks)
                    }else{
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                }else{
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<SongRankRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: ExploreContract.View?) {
        this.mView = view
    }

    override fun onDestroy() {
        this.mView = null
    }


}