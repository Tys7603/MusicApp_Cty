package com.example.musicapp.screen.musicVideo.adapter

import android.annotation.SuppressLint
import android.graphics.Color
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
    private val mListener: (Topic) -> Unit
) : ListAdapter<Topic, TopicMVAdapter.ViewHolder>(GenericDiffCallback<Topic>()) {

    private var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTopicMvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])

        holder.itemView.setOnClickListener {
            mListener.invoke(currentList[position])
            val previouslySelectedItem = selectedItem
            selectedItem = holder.adapterPosition;
            notifyItemChanged(previouslySelectedItem)
            notifyItemChanged(selectedItem)
        }
        selectItemPosition(holder, position)

    }

    private fun selectItemPosition(holder: ViewHolder, position: Int) {
        // background color
        holder.binding.layoutTopicMv.setBackgroundResource(
            if (position == selectedItem) {
                if (position == 1) {
                    R.drawable.bg_select_mix
                } else R.drawable.bg_select_topic_mv

            } else if (position == 1){
                R.drawable.bg_stroke_mix
            }  else R.drawable.bg_no_select_topic_mv
        )
        // text color
        holder.binding.tvTopicMv.setTextColor(
            ContextCompat.getColor(
                holder.binding.root.context,
                if (position == selectedItem) R.color.white else R.color.black
            )
        )
    }

    class ViewHolder(val binding: ItemTopicMvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topic = topic
        }
    }
}