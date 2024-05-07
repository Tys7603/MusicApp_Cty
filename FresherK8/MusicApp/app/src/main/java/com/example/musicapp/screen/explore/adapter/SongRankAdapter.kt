package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSongRankBinding
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.shared.extension.setAdapterLinearVertical

class SongRankAdapter : ListAdapter<SongRank, SongRankAdapter.ViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSongRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ViewHolder(val binding: ItemSongRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(songRank: SongRank) {
            binding.songRank = songRank
            binding.rcvSocialRank.setAdapterLinearVertical(SubSongRankAdapter(songRank.songs))
        }
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<SongRank>() {
        override fun areItemsTheSame(oldItem: SongRank, newItem: SongRank): Boolean {
            return oldItem.rankName == newItem.rankName
        }

        override fun areContentsTheSame(oldItem: SongRank, newItem: SongRank): Boolean {
            return oldItem == newItem
        }
    }

}