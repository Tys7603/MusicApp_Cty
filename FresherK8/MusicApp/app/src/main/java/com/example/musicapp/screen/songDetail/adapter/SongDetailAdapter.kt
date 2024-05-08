package com.example.musicapp.screen.songDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ItemSongListBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongDetailAdapter(
    private var mListener: (Song) -> Unit
) : ListAdapter<Song, SongDetailAdapter.ViewHolder>(GenericDiffCallback<Song>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            mListener.invoke(currentList[position])
        }
    }

    class ViewHolder(val binding: ItemSongListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
        }
    }
}