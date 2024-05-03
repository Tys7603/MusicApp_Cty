package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemCategoryBinding
import com.example.musicapp.data.model.Category
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.OnItemClickListener
import kotlin.math.min

class AdapterCategories(
    private var categories: ArrayList<Category>,
    private var mListener: OnItemClickListener
) :
    RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    fun setCategories(categories: ArrayList<Category>) {
        this.categories = categories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(categories.size, 8)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener { mListener.onItemClick(categories[position]) }
    }

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.imgTopicCategory.loadImageUrl(category.image)
        }
    }

}