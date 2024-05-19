package com.example.musicapp.screen.explore.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemCategoryBinding
import com.example.musicapp.data.model.Category
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class CategoriesAdapter(
    private var mListener: (Category) -> Unit
) : ListAdapter<Category, CategoriesAdapter.CategoriesViewHolder>(GenericDiffCallback<Category>()) {

    private var itemsEnabled: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size.coerceAtMost(8)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setEnableItem(enabled: Boolean) {
        itemsEnabled = enabled
    }

    inner class CategoriesViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.root.isEnabled = itemsEnabled
            binding.root.setOnClickListener {
                if (itemsEnabled) mListener.invoke(category)
            }
        }
    }
}