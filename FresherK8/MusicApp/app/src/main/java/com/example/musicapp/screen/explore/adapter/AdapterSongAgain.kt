package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemSongAgianBinding
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.shared.utils.OnItemClickListener

class AdapterSongAgain(
    private var songAgain: ArrayList<SongAgain>,
    private var mListener: OnItemClickListener
) :
    RecyclerView.Adapter<AdapterSongAgain.ViewHolder>() {

    fun setSongAgain(songAgain : ArrayList<SongAgain>) {
        this.songAgain = songAgain
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongAgianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songAgain.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songAgain[position])
        holder.itemView.setOnClickListener {
            mListener.onItemClick(songAgain[position])
        }
    }

    class ViewHolder(private val binding: ItemSongAgianBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(songAgain: SongAgain) {
            Glide.with(binding.root).load(songAgain.image).centerCrop()
                .placeholder(R.drawable.img_placeholder).into(binding.imgSongAgain)
//            binding.imgSongAgain.loadImageUrl(songAgain.url)
            binding.tvSongAgain.text = songAgain.name
        }
    }

}