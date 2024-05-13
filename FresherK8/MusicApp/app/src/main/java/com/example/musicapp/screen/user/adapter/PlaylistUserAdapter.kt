package com.example.musicapp.screen.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ItemPlaylistListUserBinding
import com.example.musicapp.databinding.ItemSongListBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class PlaylistUserAdapter(
    private var mListener: (PlaylistUser) -> Unit
) : ListAdapter<PlaylistUser, PlaylistUserAdapter.ViewHolder>(GenericDiffCallback<PlaylistUser>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(val binding: ItemPlaylistListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlistUser: PlaylistUser) {
            binding.playlistUser = playlistUser
            binding.root.setOnClickListener {
                mListener.invoke(playlistUser)
            }
        }
    }
}