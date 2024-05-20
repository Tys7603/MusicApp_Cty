package com.example.musicapp.screen.explore.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemCategoryBinding
import com.example.musicapp.data.model.Category
import com.example.musicapp.databinding.ItemCategoryGridBinding
import com.example.musicapp.databinding.ItemPlayListBinding
import com.example.musicapp.databinding.ItemPlayListGridBinding
import com.example.musicapp.shared.utils.GenericDiffCallback
import kotlin.math.min

class CategoriesAdapter(
    private var mListener: (Category) -> Unit,
    private var type: Int
) : ListAdapter<Category, RecyclerView.ViewHolder>(GenericDiffCallback<Category>()) {

    companion object {
        const val GRID = 0
        const val LINEAR = 1
    }

    private var itemsEnabled: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            GRID -> {
                val binding =
                    ItemCategoryGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CategoriesGridViewHolder(binding)
            }
            LINEAR -> {
                val binding =
                    ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CategoriesLinearViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return if (type == PlayListAdapter.GRID) {
            currentList.size
        } else {
            currentList.size.coerceAtMost(8)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoriesLinearViewHolder -> holder.bind(currentList[position])
            is CategoriesGridViewHolder -> holder.bind(currentList[position])
        }
    }

    fun setEnableItem(enabled: Boolean) {
        itemsEnabled = enabled
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == PlayListAdapter.GRID) {
            GRID
        } else {
            LINEAR
        }
    }

    inner class CategoriesLinearViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.root.isEnabled = itemsEnabled
            binding.root.setOnClickListener {
                if (itemsEnabled) mListener.invoke(category)
            }
        }
    }

    inner class CategoriesGridViewHolder(private val binding: ItemCategoryGridBinding) :
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