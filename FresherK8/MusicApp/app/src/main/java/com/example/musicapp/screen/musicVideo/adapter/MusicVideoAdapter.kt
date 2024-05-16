package com.example.musicapp.screen.musicVideo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.databinding.ItemMvBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class MusicVideoAdapter(
    private val mListener: (MusicVideo) -> Unit,
) : ListAdapter<MusicVideo, MusicVideoAdapter.MusicVideoViewHolder>(GenericDiffCallback<MusicVideo>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicVideoViewHolder {
        val binding =
            ItemMvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicVideoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class MusicVideoViewHolder(val binding: ItemMvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(musicVideo: MusicVideo) {
            binding.musicVideo = musicVideo
            binding.root.setOnClickListener { mListener.invoke(musicVideo) }
        }
    }
}