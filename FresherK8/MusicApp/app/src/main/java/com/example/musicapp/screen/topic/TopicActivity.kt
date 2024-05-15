package com.example.musicapp.screen.topic

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicapp.R
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ActivityTopicBinding
import com.example.musicapp.screen.topic.adapter.TopicAdapterGrid
import com.example.musicapp.shared.extension.setAdapterGrid
import com.example.musicapp.shared.extension.setAdapterGridVertical
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicActivity : AppCompatActivity() {
    private val viewModel : TopicViewModel by viewModel()
    private val adapter = TopicAdapterGrid()
    val binding by lazy {
        ActivityTopicBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initValue()
        initViewModel()
        handleEventViewModel()
        handleEvent()
        initRecyclerView()
    }

    private fun handleEventViewModel() {
        viewModel.topics.observe(this){
            adapter.submitList(it)
        }
    }

    private fun initViewModel() {
        binding.topicViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun handleEvent() {
        binding.imgBackTopic.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initValue() {
        val category = intent.getParcelableExtra<Category>(Constant.KEY_INTENT_ITEM)
        Log.d("TAG", "initValue: " + category.toString())
        binding.tvNameCategoryTopic.text = category?.name
        category?.let { viewModel.fetchTopic(it.id) }
    }

    private fun initRecyclerView() {
        binding.rcvCategoriesTopic.setAdapterGridVertical(adapter)
    }
}