package com.example.musicapp.screen.explore.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSongRankBinding
import com.example.musicapp.data.model.SongRank
import com.example.musicapp.shared.extension.setAdapterLinearVertical

class AdapterSongRank(private var songRanks: ArrayList<SongRank>) :
    RecyclerView.Adapter<AdapterSongRank.ViewHolder>() {

    fun setSongRank(songRanks: ArrayList<SongRank>) {
        this.songRanks = songRanks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSongRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songRanks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songRanks[position])
    }

    class ViewHolder(val binding: ItemSongRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(songRank: SongRank) {
            binding.songRank = songRank
            binding.rcvSocialRank.setAdapterLinearVertical(AdapterSubSongRank(songRank.songs))
        }
    }

}