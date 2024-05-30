package com.example.musicapp.screen.topic.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ItemCategoriesGridBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class TopicAdapterGrid(
    private val mListener: (Topic) -> Unit
) : ListAdapter<Topic, TopicAdapterGrid.TopicViewHolder>(GenericDiffCallback<Topic>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding =
            ItemCategoriesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class TopicViewHolder(private val binding: ItemCategoriesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topic = topic
            binding.root.setOnClickListener { mListener.invoke(topic) }
        }
    }
}