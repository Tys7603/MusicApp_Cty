package com.example.musicapp.screen.songDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ItemSongListBinding
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.OnItemClickListener

class SongDetailAdapter(
    private val songs: ArrayList<Song>,
    private var mListener: OnItemClickListener
) : RecyclerView.Adapter<SongDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    class ViewHolder(val binding: ItemSongListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.imgSongItem.loadImageUrl(song.image)
            binding.tvNameSongItem.text = song.name
            binding.tvNameArtistSongItem.text = song.nameArtis
        }
    }
}