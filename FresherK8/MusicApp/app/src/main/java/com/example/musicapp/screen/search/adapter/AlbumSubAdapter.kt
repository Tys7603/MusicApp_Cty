package com.example.musicapp.screen.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SearchAll
import com.example.musicapp.databinding.ItemAlbumSubBinding
import com.example.musicapp.databinding.ItemPlaylistListSubBinding
import com.example.musicapp.databinding.ItemSearchBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class AlbumSubAdapter(
    private var mListener: (Album, Int) -> Unit,
) : ListAdapter<Album, AlbumSubAdapter.AlbumViewHolder>(GenericDiffCallback<Album>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding =
            ItemAlbumSubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class AlbumViewHolder(private val binding: ItemAlbumSubBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.album = album
            binding.root.setOnClickListener { mListener.invoke(album, layoutPosition) }
        }
    }
}
