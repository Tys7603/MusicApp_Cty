package com.example.musicapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.model.repositories.AlbumRepository
import com.example.musicapp.data.model.repositories.CategoriesRepository
import com.example.musicapp.data.model.repositories.PlaylistRepository
import com.example.musicapp.data.model.repositories.SongAgainRepository
import com.example.musicapp.data.model.repositories.SongRankRepository
import com.example.musicapp.data.model.repositories.TopicRepository
import com.example.musicapp.data.source.remote.ApiService
import com.example.musicapp.shared.utils.constant.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreRepository(private val apiService: ApiService) {

    fun getListPlaylist(): ArrayList<Playlist> {

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

    fun getListPlaylistMoodToday(): ArrayList<Playlist> {
        val result = ArrayList<Playlist>()
        apiService.getListPlaylistMoodToday().enqueue(object :
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

    fun getListTopic(): ArrayList<Topic> {
        val result = ArrayList<Topic>()
        apiService.getListTopic().enqueue(object : Callback<TopicRepository> {
            override fun onResponse(
                call: Call<TopicRepository>,
                response: Response<TopicRepository>
            ) {
                if (response.isSuccessful) {
                    if (Constant.STATUS == response.body()?.status) {
                        result.addAll(response.body()!!.topics)
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                } else {
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<TopicRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
        return result
    }

    fun getListCategory(): ArrayList<Category> {
        val result = ArrayList<Category>()
        apiService.getListCategory().enqueue(object : Callback<CategoriesRepository> {
            override fun onResponse(
                call: Call<CategoriesRepository>,
                response: Response<CategoriesRepository>
            ) {
                if (response.isSuccessful) {
                    if (Constant.STATUS == response.body()?.status) {
                        result.addAll(response.body()!!.categories)
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                } else {
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<CategoriesRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
        return result
    }

    fun getListListenAgain(userID: Int): ArrayList<SongAgain> {
        val result = ArrayList<SongAgain>()
        apiService.getListSongAgain(userID).enqueue(object : Callback<SongAgainRepository> {
            override fun onResponse(
                call: Call<SongAgainRepository>,
                response: Response<SongAgainRepository>
            ) {
                if (response.isSuccessful) {
                    if (Constant.STATUS == response.body()?.status) {
                        result.addAll(response.body()!!.songAgain)
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                } else {
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<SongAgainRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
        return result
    }

    fun getListAlbumLove(): ArrayList<Album> {
        val result = ArrayList<Album>()
        apiService.getListAlbumLove().enqueue(object : Callback<AlbumRepository> {
            override fun onResponse(
                call: Call<AlbumRepository>,
                response: Response<AlbumRepository>
            ) {
                if (response.isSuccessful) {
                    if (Constant.STATUS == response.body()?.status) {
                        result.addAll(response.body()!!.albums)
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                } else {
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<AlbumRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
        return result
    }

    fun getListAlbumNew(): ArrayList<Album> {
        val result = ArrayList<Album>()
        apiService.getListAlbumNew().enqueue(object : Callback<AlbumRepository> {
            override fun onResponse(
                call: Call<AlbumRepository>,
                response: Response<AlbumRepository>
            ) {
                if (response.isSuccessful) {
                    if (Constant.STATUS == response.body()?.status) {
                        result.addAll(response.body()!!.albums)
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
                    }
                } else {
                    Log.e(Constant.TAG_ERROR, "Call api failure")
                }
            }

            override fun onFailure(call: Call<AlbumRepository>, t: Throwable) {
                Log.e(Constant.TAG_ERROR, t.toString())
            }

        })
        return result
    }

    fun getListSongRank(): ArrayList<SongRank> {
        val result = ArrayList<SongRank>()
        apiService.getListSongRank()
            .enqueue(object : Callback<SongRankRepository> {
                override fun onResponse(
                    call: Call<SongRankRepository>,
                    response: Response<SongRankRepository>
                ) {
                    if (response.isSuccessful) {
                        if (Constant.STATUS == response.body()?.status) {
                            result.addAll(response.body()!!.songRanks)
                        } else {
                            Log.e(Constant.TAG_ERROR, "Call other api status 200")
                        }
                    } else {
                        Log.e(Constant.TAG_ERROR, "Call api failure")
                    }
                }

                override fun onFailure(call: Call<SongRankRepository>, t: Throwable) {
                    Log.e(Constant.TAG_ERROR, t.toString())
                }

            })
        return result
    }
}