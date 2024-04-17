package com.example.musicapp.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemAlbumLoveBinding
import com.example.musicapp.model.AlbumLove
import kotlin.math.min

class AdapterAlbumLove(private val albumLove: ArrayList<AlbumLove>) :
    RecyclerView.Adapter<AdapterAlbumLove.ViewHolder>() {

    private lateinit var binding: ItemAlbumLoveBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAlbumLoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return min(albumLove.size, 8)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumLove[position])
    }

    class ViewHolder(private val binding: ItemAlbumLoveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albumLove: AlbumLove) {
            Glide.with(binding.root).load(albumLove.albumImage).centerCrop()
                .placeholder(R.drawable.img_placeholder).into(binding.imgAlbumLove)
            binding.tvNameAlbumLove.text = albumLove.albumName
        }
    }

}