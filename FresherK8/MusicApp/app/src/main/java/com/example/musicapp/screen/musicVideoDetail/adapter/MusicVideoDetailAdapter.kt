package com.example.musicapp.screen.musicVideoDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.databinding.ItemMvDetailBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class MusicVideoDetailAdapter(
    private val mListener: (MusicVideo, Int) -> Unit,
) : ListAdapter<MusicVideo, MusicVideoDetailAdapter.MusicVideoDetailViewHolder>(GenericDiffCallback<MusicVideo>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicVideoDetailViewHolder {
        val binding =
            ItemMvDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicVideoDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicVideoDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class MusicVideoDetailViewHolder(val binding: ItemMvDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(musicVideo: MusicVideo) {
            binding.musicVideo = musicVideo
            binding.root.setOnClickListener { mListener.invoke(musicVideo, layoutPosition) }
        }
    }
}