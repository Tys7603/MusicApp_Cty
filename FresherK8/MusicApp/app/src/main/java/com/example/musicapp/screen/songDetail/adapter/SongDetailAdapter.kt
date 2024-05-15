package com.example.musicapp.screen.songDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ItemSongListBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongDetailAdapter(
    private var mListener: (Song, Int) -> Unit
) : ListAdapter<Song, SongDetailAdapter.SongDetailViewHolder>(GenericDiffCallback<Song>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongDetailViewHolder {
        val binding =
            ItemSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SongDetailViewHolder(val binding: ItemSongListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.root.setOnClickListener {
                mListener.invoke(song, layoutPosition)
            }
        }
    }
}