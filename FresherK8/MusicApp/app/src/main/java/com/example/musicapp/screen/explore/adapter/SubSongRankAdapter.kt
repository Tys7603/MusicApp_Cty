package com.example.musicapp.screen.explore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSocialRankBinding
import com.example.musicapp.data.model.Song
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class SubSongRankAdapter : ListAdapter<Song, SubSongRankAdapter.ViewHolder>(GenericDiffCallback<Song>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSocialRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(currentList.size, 5)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
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