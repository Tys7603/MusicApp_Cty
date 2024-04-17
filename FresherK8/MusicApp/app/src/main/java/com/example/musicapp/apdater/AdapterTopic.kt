package com.example.musicapp.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemTopicBinding
import com.example.musicapp.model.Topic
import kotlin.math.min

class AdapterTopic(private val listTopic: ArrayList<Topic>) :
    RecyclerView.Adapter<AdapterTopic.ViewHolder>() {

    private lateinit var binding: ItemTopicBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(listTopic.size, 8)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTopic[position])
    }

    class ViewHolder(private val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            Glide.with(binding.root).load(topic.image).centerCrop()
                .placeholder(R.drawable.img_placeholder).into(binding.imgTopicCategory)
        }
    }

}