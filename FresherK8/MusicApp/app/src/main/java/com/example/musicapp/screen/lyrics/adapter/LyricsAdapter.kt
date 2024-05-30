package com.example.musicapp.screen.lyrics.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Lyric
import com.example.musicapp.databinding.ItemLyricBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class LyricsAdapter :
    ListAdapter<Lyric, LyricsAdapter.LyricViewHolder>(GenericDiffCallback<Lyric>()) {

    var highlightedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricViewHolder {
        val binding = ItemLyricBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LyricViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LyricViewHolder, position: Int) {
        val currentLyric = currentList[position]
        holder.bind(currentLyric)
    }

    inner class LyricViewHolder(private val binding: ItemLyricBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lyric: Lyric) {
            binding.lyric = lyric
            if (layoutPosition == highlightedPosition) {
                binding.textViewLyric.setTextColor(Color.YELLOW)
            } else {
                binding.textViewLyric.setTextColor(Color.WHITE)
            }
        }
    }
}
