package com.example.musicapp.screen.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemPlayListBinding
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.shared.extension.loadImageUrl
import com.example.musicapp.shared.utils.OnItemClickListener
import kotlin.math.min

class AdapterPlayList(
    private var listPlayList: ArrayList<Playlist>,
    private var mListener: OnItemClickListener
) :
    RecyclerView.Adapter<AdapterPlayList.ViewHolder>() {

    fun setPlaylist(playlists: ArrayList<Playlist>) {
        this.listPlayList = playlists
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return min(listPlayList.size, 5)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = listPlayList[position]
        holder.bind(playlist)
        holder.itemView.setOnClickListener {
            mListener.onItemClick(playlist)
        }
    }

    class ViewHolder(private val binding: ItemPlayListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.playlist = playlist
            binding.imgPlaylist.loadImageUrl(playlist.image)
        }
    }


}