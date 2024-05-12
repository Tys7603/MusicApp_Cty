package com.example.musicapp.screen.musicVideoDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.databinding.ItemMvDetailBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class MusicVideoDetailAdapter(
    private val mListener: (MusicVideo) -> Unit,
) : ListAdapter<MusicVideo, MusicVideoDetailAdapter.ViewHolder>(GenericDiffCallback<MusicVideo>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMvDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(val binding: ItemMvDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(musicVideo: MusicVideo) {
            binding.musicVideo = musicVideo
            binding.root.setOnClickListener {
                mListener.invoke(musicVideo)

                // Xóa mục khỏi danh sách dữ liệu
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val updatedList = currentList.toMutableList()
                    updatedList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}