package com.example.musicapp.screen.user.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.databinding.ItemPlaylistListLoveBinding
import com.example.musicapp.databinding.ItemPlaylistListUserBinding
import com.example.musicapp.databinding.ItemSelectPlaylistBinding
import com.example.musicapp.databinding.ItemSelectPlaylistLoveBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class PlaylistLoveAdapter(
    private var mListener: (Boolean, Any) -> Unit,
    private var type: Int
) : ListAdapter<Playlist, RecyclerView.ViewHolder>(GenericDiffCallback<Playlist>()) {

    companion object {
        private const val TYPE_SELECT = 1
        private const val TYPE_FRAGMENT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SELECT -> {
                val binding =
                    ItemSelectPlaylistLoveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                PlaylistLoveSelectViewHolder(binding)
            }

            TYPE_FRAGMENT -> {
                val binding =
                    ItemPlaylistListLoveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                PlaylistLoveFragmentViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlaylistLoveFragmentViewHolder -> holder.bind(currentList[position])
            is PlaylistLoveSelectViewHolder -> holder.bind(currentList[position])
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

    inner class PlaylistLoveFragmentViewHolder(val binding: ItemPlaylistListLoveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.playlist = playlist
            binding.root.setOnClickListener {
                mListener.invoke(true, playlist)
            }
        }
    }

    inner class PlaylistLoveSelectViewHolder(val binding: ItemSelectPlaylistLoveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.playlist = playlist
            binding.checkBox2.isChecked = playlist.isSelected
            binding.checkBox2.setOnCheckedChangeListener { _, isChecked ->
                playlist.isSelected = isChecked
                mListener.invoke(checkBoxSelectAll(), 0)
                mListener.invoke(isChecked, playlist)
            }
        }
    }
}