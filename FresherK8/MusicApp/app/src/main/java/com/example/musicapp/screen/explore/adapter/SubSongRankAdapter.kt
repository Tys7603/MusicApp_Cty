package com.example.musicapp.screen.explore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemSocialRankBinding
import com.example.musicapp.data.model.Song
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class SubSongRankAdapter(
    private val mListener: (ArrayList<Song>, Int) -> Unit
) : ListAdapter<Song, SubSongRankAdapter.SubSongRankViewHolder>(GenericDiffCallback<Song>()) {

    private var itemsEnabled: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubSongRankViewHolder {
        val binding =
            ItemSocialRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubSongRankViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size.coerceAtMost(5)
    }

    override fun onBindViewHolder(holder: SubSongRankViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    fun setEnableItemSub(enabled: Boolean) {
        itemsEnabled = enabled
    }

    inner class SubSongRankViewHolder(private val binding: ItemSocialRankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(song: Song, position: Int) {
            binding.tvSttSocialRank.text = (position + 1).toString()
            binding.song = song
            val songList = ArrayList(currentList)
            binding.root.isEnabled = itemsEnabled
            binding.root.setOnClickListener {
                if (itemsEnabled) mListener.invoke(songList, layoutPosition)
            }
        }
    }
}