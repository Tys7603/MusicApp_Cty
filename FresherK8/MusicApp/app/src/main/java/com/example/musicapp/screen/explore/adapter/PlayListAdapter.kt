package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemPlayListBinding
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class PlayListAdapter(
    private var mListener: (Playlist) -> Unit
) : ListAdapter<Playlist, PlayListAdapter.PlayListViewHolder>(GenericDiffCallback<Playlist>()) {

    private var itemsEnabled: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val binding =
            ItemPlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size.coerceAtMost(5)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setEnableItem(enabled: Boolean) {
        itemsEnabled = enabled
    }

    inner class PlayListViewHolder(private val binding: ItemPlayListBinding) :
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