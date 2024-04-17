package com.example.musicapp.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemAlbumNewBinding
import com.example.musicapp.model.AlbumNew
import kotlin.math.min

class AdapterAlbumNew(private val albumNew: ArrayList<AlbumNew>) :
    RecyclerView.Adapter<AdapterAlbumNew.ViewHolder>() {

    private lateinit var binding: ItemAlbumNewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAlbumNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            Glide.with(binding.root).load(albumNew.albumImage).centerCrop()
                .placeholder(R.drawable.img_placeholder).into(binding.imgAlbumNew)
            binding.tvNameAlbumNew.text = albumNew.albumName
            binding.tvNameArtist.text = albumNew.nameArtist
        }
    }

}