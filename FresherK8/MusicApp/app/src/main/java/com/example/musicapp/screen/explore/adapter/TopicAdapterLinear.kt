package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemTopicBinding
import com.example.musicapp.data.model.Topic
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class TopicAdapterLinear(
    private var mListener: (Topic) -> Unit
) : ListAdapter<Topic, TopicAdapterLinear.ViewHolder>(GenericDiffCallback<Topic>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(currentList.size, 6)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topic = topic
            binding.root.setOnClickListener { mListener.invoke(topic) }
        }
    }
}