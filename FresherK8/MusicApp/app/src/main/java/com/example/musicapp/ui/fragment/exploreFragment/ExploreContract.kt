package com.example.musicapp.ui.fragment.exploreFragment

import com.example.musicapp.model.Playlist
import com.example.musicapp.ui.fragment.exploreFragment.repository.RepositoryTopicAndCategory

interface ExploreContract {
    interface View {
        fun onListPlaylist(playlists : ArrayList<Playlist>)
        fun onListTopicAndCategory(topicAndCategory : RepositoryTopicAndCategory)
    }

    interface Presenter {
        fun getListPlaylist()
        fun getListTopicAndCategory()
    }
}