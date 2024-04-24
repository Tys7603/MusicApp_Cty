package com.example.musicapp.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Album
import com.example.musicapp.databinding.ItemAlbumNewBinding
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.OnItemClickListener
import kotlin.math.min

class AlbumAdapter(
    private val albumNew: ArrayList<Album>,
    private var mListener: OnItemClickListener
) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAlbumNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return min(albumNew.size, 8)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumNew[position])
        holder.itemView.setOnClickListener {
            mListener.onItemClick(albumNew[position])
        }
    }

    class ViewHolder(private val binding: ItemAlbumNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.imgAlbumNew.loadImageUrl(album.albumImage)
            binding.tvNameAlbumNew.text = album.albumName
            binding.tvNameArtist.text = album.nameArtist
        }
    }

}