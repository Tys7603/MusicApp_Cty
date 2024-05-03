package com.example.musicapp.data.repositories

import android.util.Log
import com.example.musicapp.data.model.Topic
import com.example.musicapp.data.model.repositories.TopicRepository
import com.example.musicapp.data.source.remote.ApiService
import com.example.musicapp.shared.utils.constant.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicRepository(private val apiService: ApiService) {

    fun getListTopicByIdCategory(id: Int) : ArrayList<Topic> {
        val result = ArrayList<Topic>()
        apiService.getListTopicByIdCategory(id).enqueue(object :
            Callback<TopicRepository> {
            override fun onResponse(
                call: Call<TopicRepository>,
                response: Response<TopicRepository>
            ) {
                if (response.isSuccessful){
                    if (Constant.STATUS == response.body()?.status){
                        result.addAll(response.body()!!.topics)
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
        return result
    }

}