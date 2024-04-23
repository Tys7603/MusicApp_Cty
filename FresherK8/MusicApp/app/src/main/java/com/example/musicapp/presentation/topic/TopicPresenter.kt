package com.example.musicapp.presentation.topic

import android.util.Log
import com.example.musicapp.contants.Constant
import com.example.musicapp.data.repositories.CategoriesRepository
import com.example.musicapp.data.repositories.TopicRepository
import com.example.musicapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicPresenter : TopicContract.Presenter{

    private var mView : TopicContract.View? = null

    override fun getListTopicByIdCategory(id: Int) {
        ApiClient.getApiService()?.getListTopicByIdCategory(id)?.enqueue(object :
            Callback<TopicRepository> {
            override fun onResponse(
                call: Call<TopicRepository>,
                response: Response<TopicRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        response.body()?.topics?.let { mView?.onListTopicByIdCategory(it) }
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

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: TopicContract.View?) {
        this.mView = view
    }

    override fun onDestroy() {
        this.mView = null
    }
}