package com.example.musicapp.screen.songUser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ItemSongAgianBinding
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.databinding.ItemSongAgianUserBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongAgainUserAdapter(
    private var mListener: (SongAgain, Int) -> Unit
) : ListAdapter<SongAgain, SongAgainUserAdapter.SongAgainViewHolder>(GenericDiffCallback<SongAgain>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAgainViewHolder {
        val binding =
            ItemSongAgianUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongAgainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongAgainViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SongAgainViewHolder(private val binding: ItemSongAgianUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(songAgain: SongAgain) {
            binding.songAgain = songAgain
            binding.root.setOnClickListener {
                mListener.invoke(songAgain, layoutPosition)
            }
        }
    }
}