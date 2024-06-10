package com.example.musicapp.screen.follow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Follow
import com.example.musicapp.databinding.ItemArtistBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class ArtistAdapter(
    private val mListener : (Follow) -> Unit
) : ListAdapter<Follow, ArtistAdapter.ArtistViewModel>(GenericDiffCallback<Follow>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewModel {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewModel(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewModel, position: Int) {
        holder.bind(currentList[position])
    }

    class ArtistViewModel(private val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(follow: Follow){
            binding.follow = follow
        }
    }
}