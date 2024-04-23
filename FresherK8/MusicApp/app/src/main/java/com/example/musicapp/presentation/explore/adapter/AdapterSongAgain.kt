package com.example.musicapp.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemSongAgianBinding
import com.example.musicapp.data.model.SongAgain
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.OnItemClickListener

class AdapterSongAgain(
    private val songAgain: ArrayList<SongAgain>,
    private var mListener: OnItemClickListener
) :
    RecyclerView.Adapter<AdapterSongAgain.ViewHolder>() {

    private lateinit var binding: ItemSongAgianBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSongAgianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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