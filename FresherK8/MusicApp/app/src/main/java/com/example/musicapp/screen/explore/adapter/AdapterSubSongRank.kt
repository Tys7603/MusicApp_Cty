package com.example.musicapp.screen.explore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemSocialRankBinding
import com.example.musicapp.data.model.Song
import kotlin.math.min

class AdapterSubSongRank(private val songs: ArrayList<Song>) :
    RecyclerView.Adapter<AdapterSubSongRank.ViewHolder>() {

    private lateinit var binding: ItemSocialRankBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSocialRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(songs.size, 5)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position], position)
    }

    class ViewHolder(private val binding: ItemSocialRankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(song: Song, position: Int) {
            Glide.with(binding.root).load(song.image).centerCrop()
                .placeholder(R.drawable.img_placeholder).into(binding.ingSocialRank)
//            binding.ingSocialRank.loadImageUrl(song.url)
            binding.tvNameSongSocialRank.text = song.name

            binding.tvSttSocialRank.text = (position + 1).toString()
            binding.tvNameArtistSocialRank.text = song.nameArtis
        }
    }

}