package com.example.musicapp.screen.topic

import android.os.Build
import android.os.Bundle
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
import com.example.musicapp.screen.topic.adapter.TopicAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicActivity : AppCompatActivity() {
    private val viewModel : TopicViewModel by viewModel()
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            initValue()
        }

        handleEvent()
    }

    private fun handleEvent() {
        binding.imgBackTopic.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initValue() {
        val bundle = intent.getBundleExtra(Constant.KEY_BUNDLE_ITEM)
        val category = bundle?.getParcelable(Constant.KEY_INTENT_ITEM, Category::class.java)
        binding.tvNameCategoryTopic.text = category?.name
        category?.let { viewModel.fetchTopic(it.id) }
    }

    fun onListTopicByIdCategory(topics: ArrayList<Topic>) {
        binding.rcvCategoriesTopic.adapter = TopicAdapter(topics)
        binding.rcvCategoriesTopic.layoutManager = GridLayoutManager(this, 2)
    }
}