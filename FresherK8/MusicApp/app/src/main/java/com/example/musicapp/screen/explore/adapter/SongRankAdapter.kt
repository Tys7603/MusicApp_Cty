package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ItemSongRankBinding
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongRankAdapter(
    private val mListener: (ArrayList<Song>, Int, String) -> Unit
) : ListAdapter<SongRank, SongRankAdapter.SongRankViewHolder>(GenericDiffCallback<SongRank>()) {

    private var itemsEnabled: Boolean = true
    private val adapter = SubSongRankAdapter(::onItemSubClick)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongRankViewHolder {
        val binding =
            ItemSongRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongRankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongRankViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setEnableItem(enabled: Boolean) {
        itemsEnabled = enabled
        adapter.setEnableItemSub(enabled)
    }

    inner class SongRankViewHolder(val binding: ItemSongRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(songRank: SongRank) {
            binding.rcvSocialRank.setAdapterLinearVertical(adapter)
            adapter.submitList(songRank.songs)

            binding.songRank = songRank
            binding.root.isEnabled = itemsEnabled
            binding.btnPlaySongRank.setOnClickListener {
                if (itemsEnabled) mListener.invoke(songRank.songs, 0, songRank.rankName)
            }
        }
    }

    private fun onItemSubClick(song: ArrayList<Song>, position: Int) {
        mListener.invoke(song, position, compareSongLists(currentList, song))
    }

    private fun compareSongLists(mainList: List<SongRank>, subList: List<Song>): String {
        val subSet = subList.toSet()

        for (songRank in mainList) {
            val songSet = songRank.songs.toSet()
            if (songSet == subSet) {
                return songRank.rankName
            }
        }
        return ""
    }
}