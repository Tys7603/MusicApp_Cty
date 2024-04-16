package com.example.musicapp.apdater

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemPlayListBinding
import com.example.musicapp.model.Playlist

class AdapterPlayList(private var listPlayList: ArrayList<Playlist>) : RecyclerView.Adapter<AdapterPlayList.ViewHolder>() {
    private lateinit var binding : ItemPlayListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPlayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = listPlayList[position]
        holder.bind(playlist)
    }

    class ViewHolder(private val binding: ItemPlayListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist){
            binding.tvNamePlaylist.text = playlist.name
            Handler(Looper.getMainLooper()!!).postDelayed({
                Glide
                    .with(binding.root.context)
                    .load(playlist.image)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .into(binding.imgPlaylist);
            },3000)

        }
    }

}