package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSongRankBinding
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongRankAdapter : ListAdapter<SongRank, SongRankAdapter.SongRankViewHolder>(GenericDiffCallback<SongRank>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongRankViewHolder {
        val binding =
            ItemSongRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongRankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongRankViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class SongRankViewHolder(val binding: ItemSongRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(songRank: SongRank) {
            val adapter = SubSongRankAdapter()
            binding.songRank = songRank
            binding.rcvSocialRank.setAdapterLinearVertical(adapter)
            adapter.submitList(songRank.songs)
        }
    }
}