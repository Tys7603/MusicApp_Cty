package com.example.musicapp.screen.user.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.databinding.ItemPlaylistListUserBinding
import com.example.musicapp.databinding.ItemSelectPlaylistBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class PlaylistUserAdapter(
    private var mListener: (Boolean, Any) -> Unit,
    private var type: Int
) : ListAdapter<PlaylistUser, RecyclerView.ViewHolder>(GenericDiffCallback<PlaylistUser>()) {

    companion object {
        private const val TYPE_SELECT = 1
        private const val TYPE_FRAGMENT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SELECT -> {
                val binding =
                    ItemSelectPlaylistBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                PlaylistUserSelectViewHolder(binding)
            }

            TYPE_FRAGMENT -> {
                val binding =
                    ItemPlaylistListUserBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                PlaylistUserFragmentViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlaylistUserFragmentViewHolder -> holder.bind(currentList[position])
            is PlaylistUserSelectViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == TYPE_SELECT) {
            TYPE_SELECT
        } else {
            TYPE_FRAGMENT
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectAllItem() {
        currentList.forEach { it.isSelected = true }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun noSelectAllItem() {
        currentList.forEach { it.isSelected = false }
        notifyDataSetChanged()
    }

    fun checkBoxSelectAll(): Boolean {
        for (plU in currentList) {
            if (!plU.isSelected) {
                return false
            }
        }
        return true
    }

    inner class PlaylistUserFragmentViewHolder(val binding: ItemPlaylistListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlistUser: PlaylistUser) {
            binding.playlistUser = playlistUser
            binding.root.setOnClickListener {
                mListener.invoke(true, playlistUser)
            }
        }
    }

    inner class PlaylistUserSelectViewHolder(val binding: ItemSelectPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlistUser: PlaylistUser) {
            binding.playlistUser = playlistUser
            binding.cbSelectUser.isChecked = playlistUser.isSelected
            binding.cbSelectUser.setOnCheckedChangeListener { _, isChecked ->
                playlistUser.isSelected = isChecked
                mListener.invoke(checkBoxSelectAll(), 0)
                mListener.invoke(isChecked, playlistUser)
            }
        }
    }
}