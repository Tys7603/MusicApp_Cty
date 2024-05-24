package com.example.musicapp.screen.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.SearchAll
import com.example.musicapp.databinding.ItemSearchBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class SearchAdapter(
    private val mListener: (String) -> Unit
) : ListAdapter<SearchAll, SearchAdapter.SearchViewHolder>(GenericDiffCallback<SearchAll>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchAll: SearchAll) {
            binding.searchAll = searchAll
            binding.root.setOnClickListener { mListener.invoke(searchAll.name) }
        }
    }
}
