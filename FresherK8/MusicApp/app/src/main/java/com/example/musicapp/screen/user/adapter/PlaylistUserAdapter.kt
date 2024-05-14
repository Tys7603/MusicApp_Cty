package com.example.musicapp.screen.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ItemPlaylistListUserBinding
import com.example.musicapp.databinding.ItemSelectPlaylistBinding
import com.example.musicapp.databinding.ItemSongListBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class PlaylistUserAdapter(
    private var mListener: (PlaylistUser) -> Unit,
    private var type : Int
) : ListAdapter<PlaylistUser, RecyclerView.ViewHolder>(GenericDiffCallback<PlaylistUser>()) {

    companion object {
        private const val TYPE_SELECT = 1
        private const val TYPE_FRAGMENT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_SELECT -> {
                val binding =
                    ItemSelectPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolderPlaylistUserSelect(binding)
            }
            TYPE_FRAGMENT -> {
                val binding =
                    ItemPlaylistListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolderPlaylistUserFragment(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderPlaylistUserFragment -> holder.bind(currentList[position])
            is ViewHolderPlaylistUserSelect -> holder.bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == TYPE_SELECT){
            TYPE_SELECT
        }else{
            TYPE_FRAGMENT
        }
    }

    inner class ViewHolderPlaylistUserFragment(val binding: ItemPlaylistListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlistUser: PlaylistUser) {
            binding.playlistUser = playlistUser
            binding.root.setOnClickListener {
                mListener.invoke(playlistUser)
            }
        }
    }

    inner class ViewHolderPlaylistUserSelect(val binding: ItemSelectPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlistUser: PlaylistUser) {
            binding.playlistUser = playlistUser
            binding.root.setOnClickListener {
                mListener.invoke(playlistUser)
            }
        }
    }
}