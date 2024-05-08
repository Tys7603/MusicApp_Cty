package com.example.musicapp.screen.musicVideo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.Category
import com.example.musicapp.databinding.ItemCategoryMvBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class CategoryMVAdapter(
    private val mListener: (Category) -> Unit
) : ListAdapter<Category, CategoryMVAdapter.ViewHolder>(GenericDiffCallback<Category>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryMvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener { mListener.invoke(currentList[position]) }
    }

    class ViewHolder(private val binding: ItemCategoryMvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
        }
    }
}