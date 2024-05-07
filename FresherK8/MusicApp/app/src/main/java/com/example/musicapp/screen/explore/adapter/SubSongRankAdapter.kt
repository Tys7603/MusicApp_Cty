package com.example.musicapp.screen.explore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSocialRankBinding
import com.example.musicapp.data.model.Song
import kotlin.math.min

class SubSongRankAdapter(private var songs: ArrayList<Song>) :
    RecyclerView.Adapter<SubSongRankAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSocialRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(songs.size, 5)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position], position)
    }

    class ViewHolder(private val binding: ItemSocialRankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(song: Song, position: Int) {
            binding.tvSttSocialRank.text = (position + 1).toString()
            binding.song = song
        }
    }

}