package com.example.musicapp.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemSongRankBinding
import com.example.musicapp.model.AlbumLove
import com.example.musicapp.model.SongRank
import kotlin.math.min

class AdapterSongRank(private val songRanks: ArrayList<SongRank>) :
    RecyclerView.Adapter<AdapterSongRank.ViewHolder>() {

    private lateinit var binding: ItemSongRankBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSongRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songRanks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songRanks[position])
    }

    class ViewHolder(private val binding: ItemSongRankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(songRank: SongRank) {
            binding.tvNameRank.text = songRank.rankName
            val adapter  = AdapterSubSongRank(songRank.songs)
            binding.rcvSocialRank.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rcvSocialRank.adapter = adapter
        }
    }

}