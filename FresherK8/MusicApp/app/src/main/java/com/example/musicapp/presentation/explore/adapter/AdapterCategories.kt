package com.example.musicapp.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemCategoryBinding
import com.example.musicapp.data.model.Category
import com.example.musicapp.shared.extension.loadImageUrl
import kotlin.math.min

class AdapterCategories(private val categories: ArrayList<Category>) :
    RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return min(categories.size, 5)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.imgTopicCategory.loadImageUrl(category.image)
            binding.tvNameCategory.text = category.name
        }
    }

}