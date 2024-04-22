package com.example.musicapp.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemAlbumNewBinding
import com.example.musicapp.data.model.AlbumNew
import com.example.musicapp.shared.extension.loadImageUrl
import kotlin.math.min

class AdapterAlbumNew(private val albumNew: ArrayList<AlbumNew>) :
    RecyclerView.Adapter<AdapterAlbumNew.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return min(albumNew.size, 8)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumNew[position])
    }

    class ViewHolder(private val binding: ItemAlbumNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albumNew: AlbumNew) {
            binding.imgAlbumNew.loadImageUrl(albumNew.albumImage)
            binding.tvNameAlbumNew.text = albumNew.albumName
            binding.tvNameArtist.text = albumNew.nameArtist
        }
    }

}