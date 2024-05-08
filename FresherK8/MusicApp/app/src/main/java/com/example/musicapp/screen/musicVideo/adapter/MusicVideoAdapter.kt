package com.example.musicapp.screen.musicVideo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.databinding.ItemMvBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class MusicVideoAdapter(
    private val mListener: (MusicVideo) -> Unit
) : ListAdapter<MusicVideo, MusicVideoAdapter.ViewHolder>(GenericDiffCallback<MusicVideo>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener { mListener.invoke(currentList[position]) }
    }

    class ViewHolder(private val binding: ItemMvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(musicVideo: MusicVideo) {
            binding.musicVideo = musicVideo
        }
    }
}