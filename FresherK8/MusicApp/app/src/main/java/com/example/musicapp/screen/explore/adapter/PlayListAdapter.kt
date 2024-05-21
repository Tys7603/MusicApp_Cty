package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemPlayListBinding
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.databinding.ItemPlayListGridBinding
import com.example.musicapp.screen.user.adapter.PlaylistLoveAdapter
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class PlayListAdapter(
    private var mListener: (Playlist) -> Unit,
    private var type: Int
) : ListAdapter<Playlist, RecyclerView.ViewHolder>(GenericDiffCallback<Playlist>()) {

    companion object {
        const val GRID = 0
        const val LINEAR = 1
    }

    private var itemsEnabled: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            GRID -> {
                val binding =
                    ItemPlayListGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlayListGridViewHolder(binding)
            }
            LINEAR -> {
                val binding =
                    ItemPlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlayListLinearViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return if (type == GRID) {
            currentList.size
        } else {
            currentList.size.coerceAtMost(5)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlayListGridViewHolder -> holder.bind(currentList[position])
            is PlayListLinearViewHolder -> holder.bind(currentList[position])
        }
    }

    fun setEnableItem(enabled: Boolean) {
        itemsEnabled = enabled
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == GRID) {
            GRID
        } else {
            LINEAR
        }
    }

    inner class PlayListLinearViewHolder(private val binding: ItemPlayListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.playlist = playlist
            binding.root.isEnabled = itemsEnabled
            binding.root.setOnClickListener {
                if (itemsEnabled) mListener.invoke(playlist)
            }
        }
    }

    inner class PlayListGridViewHolder(private val binding: ItemPlayListGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.playlist = playlist
            binding.root.isEnabled = itemsEnabled
            binding.root.setOnClickListener {
                if (itemsEnabled) mListener.invoke(playlist)
            }
        }
    }
}