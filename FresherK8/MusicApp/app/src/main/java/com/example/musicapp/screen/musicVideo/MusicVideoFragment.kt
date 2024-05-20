package com.example.musicapp.screen.musicVideo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.FragmentMusicVideoBinding
import com.example.musicapp.screen.musicVideo.adapter.TopicMVAdapter
import com.example.musicapp.screen.musicVideo.adapter.MusicVideoAdapter
import com.example.musicapp.screen.musicVideoDetail.MusicVideoDetailActivity
import com.example.musicapp.shared.extension.setAdapterLinearHorizontal
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicVideoFragment : Fragment() {
    private val viewModel: MusicVideoViewModel by viewModel()
    private val musicVideoAdapter = MusicVideoAdapter(::onClickItem)
    private val categoryMVAdapter = TopicMVAdapter(::onClickItem)
    private var mMusicVideos: ArrayList<MusicVideo>? = null

    private val binding by lazy {
        FragmentMusicVideoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        handlerEventViewModel()
        checkVisibilityLayout(false)
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        with(binding) {
            rcvMv.setAdapterLinearVertical(musicVideoAdapter)
            rcvCategoryMv.setAdapterLinearHorizontal(categoryMVAdapter)
        }
    }

    private fun handlerEventViewModel() {
        viewModel.musicVideos.observe(viewLifecycleOwner) {
            handlerPostDelay {
                mMusicVideos = it
                musicVideoAdapter.submitList(it.shuffled())
                checkVisibilityLayout(true)
            }
        }
        viewModel.topics.observe(viewLifecycleOwner) {
            it.add(0, Topic(0, ALL, "", 0))
            it.add(1, Topic(1, NEW_PROPOSAL, "", 0))
            categoryMVAdapter.submitList(it)
        }
    }

    private fun checkVisibilityLayout(boolean: Boolean) {
        if (boolean) {
            binding.layoutMvFragment.visibility = View.VISIBLE
            binding.includeLayoutMvFragment.visibility = View.GONE
        } else {
            binding.layoutMvFragment.visibility = View.INVISIBLE
            binding.includeLayoutMvFragment.visibility = View.VISIBLE
        }
    }

    private fun onClickItem(item: Any, position: Int) {
        when (item) {
            is Topic -> {
                when (item.id) {
                    0 -> {
                        handleTopicClicked(0)
                    }
                    1 -> {
                        handleTopicClicked(1)
                    }
                    else -> {
                        handleTopicClicked(item.id)
                    }
                }
                scrollToPositionCategories(position)
            }

            is MusicVideo -> {
                val intent = Intent(requireContext(), MusicVideoDetailActivity::class.java)
                intent.putExtra(Constant.KEY_INTENT_ITEM, item)
                startActivity(intent)
            }
        }
    }

    private fun handleTopicClicked(topicId: Int) {
        val filteredList = when (topicId) {
            0 -> mMusicVideos?.shuffled()
            1 -> mMusicVideos?.let { itemEqualListMusicVideoProposalNew(it).shuffled() }
            else -> mMusicVideos?.let { itemEqualListMusicVideo(topicId, it).shuffled() }
        }
        filteredList?.let {
            if (it.isEmpty()) {
                binding.tvShow.visibility = View.VISIBLE
            } else {
                binding.tvShow.visibility = View.GONE
            }
            musicVideoAdapter.submitList(it) {
                scrollToFirstItem()
            }
        }
    }

    private fun scrollToFirstItem() {
        val layoutManager = binding.rcvMv.layoutManager as LinearLayoutManager
        layoutManager.scrollToPosition(0)
    }

    private fun scrollToPositionCategories(position: Int) {
        val layoutManager = binding.rcvCategoryMv.layoutManager as LinearLayoutManager
        if (position > 0) {
            layoutManager.scrollToPositionWithOffset(position - 1, 0)
        }
    }

    private fun itemEqualListMusicVideoProposalNew(musicVideos: List<MusicVideo>): ArrayList<MusicVideo> {
        val matchedMusicVideos = ArrayList<MusicVideo>()
        for (musicVideo in musicVideos) {
            if (musicVideo.musicVideoProposalNew == 1) {
                matchedMusicVideos.add(musicVideo)
            }
        }
        return matchedMusicVideos
    }

    private fun itemEqualListMusicVideo(
        topicId: Int,
        musicVideos: List<MusicVideo>
    ): ArrayList<MusicVideo> {
        val matchedMusicVideos = ArrayList<MusicVideo>()
        for (musicVideo in musicVideos) {
            if (musicVideo.topicId == topicId) {
                matchedMusicVideos.add(musicVideo)
            }
        }
        return matchedMusicVideos
    }

    private fun handlerPostDelay(listener: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            listener.invoke()
        }, 500)
    }

    companion object {
        const val ALL = "Tất cả"
        const val NEW_PROPOSAL = "Đề xuất mới"
    }
}