package com.example.musicapp.screen.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.SearchAll
import com.example.musicapp.databinding.ItemPlaylistListSubBinding
import com.example.musicapp.databinding.ItemSearchBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class PlaylistSubAdapter(
    private var mListener: (Playlist, Int) -> Unit,
) : ListAdapter<Playlist, PlaylistSubAdapter.PlaylistViewHolder>(GenericDiffCallback<Playlist>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding =
            ItemPlaylistListSubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlaylistViewHolder(private val binding: ItemPlaylistListSubBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: Playlist) {
            binding.playlist = playlist
            binding.root.setOnClickListener { mListener.invoke(playlist, layoutPosition) }
        }
    }
}
