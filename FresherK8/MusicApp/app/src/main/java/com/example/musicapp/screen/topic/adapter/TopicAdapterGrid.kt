package com.example.musicapp.screen.topic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ItemCategoriesGridBinding
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.GenericDiffCallback

class TopicAdapterGrid : ListAdapter<Topic, TopicAdapterGrid.ViewHolder>(GenericDiffCallback<Topic>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoriesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ViewHolder(private val binding: ItemCategoriesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topic = topic
        }
    }
}