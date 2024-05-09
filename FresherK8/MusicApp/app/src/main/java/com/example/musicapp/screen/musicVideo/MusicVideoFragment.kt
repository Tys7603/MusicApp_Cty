package com.example.musicapp.screen.musicVideo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.data.model.MusicVideo
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.FragmentMusicVideoBinding
import com.example.musicapp.screen.musicVideo.adapter.TopicMVAdapter
import com.example.musicapp.screen.musicVideo.adapter.MusicVideoAdapter
import com.example.musicapp.screen.musicVideoDetail.MusicVideoDetailActivity
import com.example.musicapp.shared.extension.setAdapterLinearHorizontal
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicVideoFragment : Fragment() {
    private val viewModel : MusicVideoViewModel by viewModel()
    private val musicVideoAdapter = MusicVideoAdapter(::onClickItem)
    private val categoryMVAdapter = TopicMVAdapter(::onClickItem)
    private var mMusicVideos : ArrayList<MusicVideo>? = null
    private val binding by lazy {
        FragmentMusicVideoBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        handlerEventViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        lifecycle
    }

    private fun initRecyclerView() {
        with(binding) {
            rcvMv.setAdapterLinearVertical(musicVideoAdapter)
            rcvCategoryMv.setAdapterLinearHorizontal(categoryMVAdapter)
        }
    }

    private fun handlerEventViewModel() {
        viewModel.musicVideos.observe(viewLifecycleOwner){
            mMusicVideos = it
            musicVideoAdapter.submitList(it.shuffled())

        }
        viewModel.topics.observe(viewLifecycleOwner){
            it.add(0,Topic(0, ALL, "", 0))
            it.add(1,Topic(1,NEW_PROPOSAL, "", 0))
            categoryMVAdapter.submitList(it)
        }
    }

    private fun onClickItem(item: Any){
        when(item){
            is Topic -> {
                when (item.id) {
                    0 -> {
                        musicVideoAdapter.submitList(mMusicVideos!!.shuffled())
                    }
                    1 -> {
                        musicVideoAdapter.submitList(itemEqualListMusicVideoProposalNew(mMusicVideos!!))
                    }
                    else -> {
                        musicVideoAdapter.submitList(itemEqualListMusicVideo(item, mMusicVideos!!))
                    }
                }
            }

            is MusicVideo -> {
                val intent = Intent(requireContext(), MusicVideoDetailActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable(Constant.KEY_INTENT_ITEM, item)
                }
                intent.putExtra(Constant.KEY_BUNDLE_ITEM, bundle)
                startActivity(intent)
            }
        }
    }

    private fun itemEqualListMusicVideoProposalNew(musicVideos: List<MusicVideo>) : ArrayList<MusicVideo> {
        val matchedMusicVideos  = ArrayList<MusicVideo>()
        for (musicVideo in musicVideos){
            if (musicVideo.musicVideoProposalNew == 1){
                matchedMusicVideos.add(musicVideo)
            }
        }
        return matchedMusicVideos
    }


    private fun itemEqualListMusicVideo(topic: Topic, musicVideos: List<MusicVideo>) : ArrayList<MusicVideo> {
        val matchedMusicVideos  = ArrayList<MusicVideo>()
        for (musicVideo in musicVideos){
            if (musicVideo.topicId == topic.id){
                matchedMusicVideos.add(musicVideo)
            }
        }
        return matchedMusicVideos
    }

    companion object{
        const val ALL = "Tất cả"
        const val NEW_PROPOSAL = "Đề xuất mới"
    }
}