package com.example.musicapp.screen.songUser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.source.local.dao.SongDao
import com.example.musicapp.databinding.ItemSongDownBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class SongDownAdapter(
    private var mListener: (Song, Int) -> Unit,
    private var mListenerDelete: (List<Song>, Int) -> Unit
) : ListAdapter<Song, SongDownAdapter.SongDetailViewHolder>(GenericDiffCallback<Song>()) {
    private lateinit var binding: ItemSongDownBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongDetailViewHolder {
        binding =
            ItemSongDownBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun deleteItem(position: Int){
        val songs = currentList.toMutableList()
        val song = songs.removeAt(position)
        submitList(songs)
        notifyItemRemoved(position)
        SongDao(binding.root.context).deleteSong(song.id)
        mListenerDelete.invoke(songs, position)
    }

    inner class SongDetailViewHolder(val binding: ItemSongDownBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.root.setOnClickListener {
                mListener.invoke(song, layoutPosition)
            }
        }
    }
}