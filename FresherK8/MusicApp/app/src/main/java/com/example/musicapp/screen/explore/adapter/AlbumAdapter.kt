package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Album
import com.example.musicapp.databinding.ItemAlbumNewBinding
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.OnItemClickListener
import kotlin.math.min

class AlbumAdapter(
    private var albums: ArrayList<Album>,
    private var mListener: OnItemClickListener
) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    fun setAlbums(albums: ArrayList<Album>) {
        this.albums = albums
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAlbumNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return min(albums.size, 8)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.itemView.setOnClickListener {
            mListener.onItemClick(albums[position])
        }
    }

    class ViewHolder(private val binding: ItemAlbumNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.albumLove = album
        }
    }

}