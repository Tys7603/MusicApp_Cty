package com.example.musicapp.screen.musicVideo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ItemTopicMvBinding
import com.example.musicapp.shared.utils.GenericDiffCallback

class TopicMVAdapter(
    private val mListener: (Topic, Int) -> Unit
) : ListAdapter<Topic, TopicMVAdapter.TopicMVViewHolder>(GenericDiffCallback<Topic>()) {

    private var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicMVViewHolder {
        val binding =
            ItemTopicMvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicMVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicMVViewHolder, position: Int) {
        holder.bind(currentList[position], selectedItem == position)
    }

    private fun selectItemPosition(
        binding: ItemTopicMvBinding,
        isSelected: Boolean,
        position: Int
    ) {
        // background color
        binding.layoutTopicMv.setBackgroundResource(
            if (isSelected) {
                if (position == 1) {
                    R.drawable.bg_select_mix
                } else R.drawable.bg_select_topic_mv
            } else if (position == 1) {
                R.drawable.bg_stroke_mix
            } else R.drawable.bg_no_select_topic_mv
        )
        // text color
        binding.tvTopicMv.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                if (isSelected) R.color.white else R.color.black
            )
        )
    }

    inner class TopicMVViewHolder(val binding: ItemTopicMvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Topic, isSelected: Boolean) {
            binding.topic = topic
            binding.root.isSelected = isSelected

            binding.root.setOnClickListener {
                val position = layoutPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener.invoke(topic, layoutPosition)

                    val previouslySelectedItem = selectedItem
                    selectedItem = layoutPosition

                    notifyItemChanged(previouslySelectedItem)
                    notifyItemChanged(selectedItem)
                }
            }
            selectItemPosition(binding, isSelected, layoutPosition)
        }
    }
}