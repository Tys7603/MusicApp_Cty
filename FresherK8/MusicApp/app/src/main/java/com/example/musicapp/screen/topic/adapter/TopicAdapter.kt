package com.example.musicapp.screen.topic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ItemCategoriesGridBinding
import com.example.musicapp.shared.extension.loadImageUrl

class TopicAdapter(private val topics: ArrayList<Topic>) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoriesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return topics.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position])
    }

    class ViewHolder(private val binding: ItemCategoriesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.imgCategoriesGrid.loadImageUrl(topic.image)
            binding.tvCategoriesGrid.text = topic.name
        }
    }

}