package com.example.musicapp.screen.songDetail

class SongListPresenter {

//    private var mView : SongListContract.View? = null
//
//    override fun getListSongTopic(id : Int) {
//        ApiClient.getApiService()?.getListSongTopicById(id)?.enqueue(object :
//            Callback<SongRepository> {
//            override fun onResponse(
//                call: Call<SongRepository>,
//                response: Response<SongRepository>
//            ) {
//                if (response.isSuccessful){
//                    if (Constant.STATUS == response.body()?.status){
//                        response.body()?.songs?.let { mView?.onListSong(it) }
//                    }else{
//                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
//                    }
//                }else{
//                    Log.e(Constant.TAG_ERROR, "Call api failure")
//                }
//            }
//
//            override fun onFailure(call: Call<SongRepository>, t: Throwable) {
//                Log.e(Constant.TAG_ERROR, t.toString())
//            }
//
//        })
//    }
//
//    override fun getListSongPlaylist(id: Int) {
//        ApiClient.getApiService()?.getListSongPlaylistById(id)?.enqueue(object :
//            Callback<SongRepository> {
//            override fun onResponse(
//                call: Call<SongRepository>,
//                response: Response<SongRepository>
//            ) {
//                if (response.isSuccessful){
//                    if (Constant.STATUS == response.body()?.status){
//                        response.body()?.songs?.let { mView?.onListSong(it) }
//                    }else{
//                        Log.e(Constant.TAG_ERROR, "Call other api status 200")
//                    }
//                }else{
//                    Log.e(Constant.TAG_ERROR, "Call api failure")
//                }
//            }
//
//            override fun onFailure(call: Call<SongRepository>, t: Throwable) {
//                Log.e(Constant.TAG_ERROR, t.toString())
//            }
//
//        })
//    }
//
//    override fun onStart() = Unit
//
//    override fun onStop() = Unit
//
//    override fun setView(view: SongListContract.View?) {
//        this.mView = view
//    }
//
//    override fun onDestroy() {
//       this.mView = null
//    }
}