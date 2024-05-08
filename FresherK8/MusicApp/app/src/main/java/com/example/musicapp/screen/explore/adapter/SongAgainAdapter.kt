package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSongAgianBinding
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongAgainAdapter(
    private var mListener: (SongAgain) -> Unit
) : ListAdapter<SongAgain, SongAgainAdapter.ViewHolder>(GenericDiffCallback<SongAgain>()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSongAgianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            mListener.invoke(currentList[position])
        }
    }

    class ViewHolder(private val binding: ItemSongAgianBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(songAgain: SongAgain) {
            binding.songAgain = songAgain
        }
    }
}