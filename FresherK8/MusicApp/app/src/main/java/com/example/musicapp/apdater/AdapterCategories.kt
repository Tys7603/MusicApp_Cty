package com.example.musicapp.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemCategoryBinding
import com.example.musicapp.model.Category
import kotlin.math.min

class AdapterCategories(private val categories: ArrayList<Category>) :
    RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    private lateinit var binding: ItemCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            Glide.with(binding.root).load(category.image).centerCrop()
                .placeholder(R.drawable.img_placeholder).into(binding.imgTopicCategory)
            binding.tvNameCategory.text = category.name
        }
    }

}