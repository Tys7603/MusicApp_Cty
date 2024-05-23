package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSongAgianBinding
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.databinding.ItemAlbumBinding
import com.example.musicapp.databinding.ItemAlbumGridBinding
import com.example.musicapp.databinding.ItemSongAgianUserBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongAgainAdapter(
    private var mListener: (SongAgain, Int) -> Unit,
    private val type: Int
) : ListAdapter<SongAgain, RecyclerView.ViewHolder>(GenericDiffCallback<SongAgain>()) {

    companion object {
        const val VERTICAL = 0
        const val HORIZONTAL = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HORIZONTAL -> {
                val binding =
                    ItemSongAgianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SongAgainViewHolderHorizontal(binding)
            }

            VERTICAL -> {
                val binding =
                    ItemSongAgianUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SongAgainViewHolderVertical(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SongAgainViewHolderHorizontal -> holder.bind(currentList[position])
            is SongAgainViewHolderVertical -> holder.bind(currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return if (type == VERTICAL) {
            currentList.size
        } else {
            currentList.size.coerceAtMost(8)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == VERTICAL) {
            VERTICAL
        } else {
            HORIZONTAL
        }
    }

    inner class SongAgainViewHolderHorizontal(private val binding: ItemSongAgianBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(songAgain: SongAgain) {
            binding.songAgain = songAgain
            binding.root.setOnClickListener {
                mListener.invoke(songAgain, layoutPosition)
            }
        }
    }

    inner class SongAgainViewHolderVertical(private val binding: ItemSongAgianUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(songAgain: SongAgain) {
            binding.songAgain = songAgain
            binding.root.setOnClickListener {
                mListener.invoke(songAgain, layoutPosition)
            }
        }
    }
}