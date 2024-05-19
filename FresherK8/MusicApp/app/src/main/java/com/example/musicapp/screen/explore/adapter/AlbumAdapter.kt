package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Album
import com.example.musicapp.databinding.ItemAlbumBinding
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class AlbumAdapter(
    private var mListener: (Album) -> Unit
) : ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(GenericDiffCallback<Album>()) {

    private var itemsEnabled: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding =
            ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size.coerceAtMost(6)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setEnableItem(enabled: Boolean) {
        itemsEnabled = enabled
    }

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.albumLove = album
            binding.root.isEnabled = itemsEnabled
            binding.root.setOnClickListener {
                if (itemsEnabled) mListener.invoke(album)
            }
        }
    }
}