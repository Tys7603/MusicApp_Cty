package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Album
import com.example.musicapp.databinding.ItemAlbumBinding
import com.example.musicapp.databinding.ItemAlbumGridBinding
import com.example.musicapp.databinding.ItemCategoryBinding
import com.example.musicapp.databinding.ItemCategoryGridBinding
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class AlbumAdapter(
    private var mListener: (Album) -> Unit,
    private var type: Int
) : ListAdapter<Album, RecyclerView.ViewHolder>(GenericDiffCallback<Album>()) {

    private var itemsEnabled: Boolean = true

    companion object {
        const val GRID = 0
        const val LINEAR = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            GRID -> {
                val binding =
                    ItemAlbumGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AlbumViewGridHolder(binding)
            }
            LINEAR -> {
                val binding =
                    ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AlbumViewLinearHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewGridHolder -> holder.bind(currentList[position])
            is AlbumViewLinearHolder -> holder.bind(currentList[position])
        }
    }

    fun setEnableItem(enabled: Boolean) {
        itemsEnabled = enabled
    }

    override fun getItemCount(): Int {
        return if (type == PlayListAdapter.GRID) {
            currentList.size
        } else {
            currentList.size.coerceAtMost(6)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == GRID) {
            GRID
        } else {
            LINEAR
        }
    }

    inner class AlbumViewGridHolder(private val binding: ItemAlbumGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.albumLove = album
            binding.root.isEnabled = itemsEnabled
            binding.root.setOnClickListener {
                if (itemsEnabled) mListener.invoke(album)
            }
        }
    }

    inner class AlbumViewLinearHolder(private val binding: ItemAlbumBinding) :
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