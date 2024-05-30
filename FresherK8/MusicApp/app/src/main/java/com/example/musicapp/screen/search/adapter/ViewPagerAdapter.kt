package com.example.musicapp.screen.search.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Song
import com.example.musicapp.screen.search.fragment.AlbumSubFragment
import com.example.musicapp.screen.search.fragment.MusicVideoSubFragment
import com.example.musicapp.screen.search.fragment.PlaylistSubFragment
import com.example.musicapp.screen.search.fragment.SongSubFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val playlists: List<Playlist>,
    private val albums: List<Album>,
    private val songs: List<Song>,
    private val musicVideos: List<MusicVideo>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> SongSubFragment()
            1 -> PlaylistSubFragment()
            2 -> AlbumSubFragment()
            3 -> MusicVideoSubFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }

        fragment.arguments = Bundle().apply {
            when (position) {
                0 -> putParcelableArrayList("KEY_DATA", ArrayList(songs))
                1 -> putParcelableArrayList("KEY_DATA", ArrayList(playlists))
                2 -> putParcelableArrayList("KEY_DATA", ArrayList(albums))
                3 -> putParcelableArrayList("KEY_DATA", ArrayList(musicVideos))
            }
        }

        return fragment
    }
}