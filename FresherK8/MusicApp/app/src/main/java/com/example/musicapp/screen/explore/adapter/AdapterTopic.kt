package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemTopicBinding
import com.example.musicapp.data.model.Topic
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.OnItemClickListener
import kotlin.math.min

class AdapterTopic(
    private var listTopic: ArrayList<Topic>,
    private var mListener: OnItemClickListener
) :
    RecyclerView.Adapter<AdapterTopic.ViewHolder>() {

    fun setTopics(topics: ArrayList<Topic>) {
        this.listTopic = topics
    }

    private lateinit var binding: ItemTopicBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(listTopic.size, 6)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTopic[position])
        holder.itemView.setOnClickListener { mListener.onItemClick(listTopic[position]) }
    }

    class ViewHolder(private val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topic = topic
        }
    }

}