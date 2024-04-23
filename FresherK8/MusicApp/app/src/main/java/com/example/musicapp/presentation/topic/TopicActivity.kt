package com.example.musicapp.presentation.topic

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicapp.R
import com.example.musicapp.contants.Constant
import com.example.musicapp.data.model.Category
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.Topic
import com.example.musicapp.databinding.ActivityTopicBinding
import com.example.musicapp.presentation.topic.adapter.TopicAdapter
import com.example.musicapp.shared.extension.loadImageUrl

class TopicActivity : AppCompatActivity(), TopicContract.View {

    val binding by lazy {
        ActivityTopicBinding.inflate(layoutInflater)
    }

    private val mPresenter by lazy {
        TopicPresenter()
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

    override fun onStart() {
        super.onStart()
        mPresenter.setView(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initValue() {
        val bundle = intent.getBundleExtra(Constant.KEY_BUNDLE_ITEM)
        val category = bundle?.getParcelable(Constant.KEY_INTENT_ITEM, Category::class.java)
        binding.tvNameCategoryTopic.text = category?.name
        category?.id?.let { mPresenter.getListTopicByIdCategory(it) }
    }

    override fun onListTopicByIdCategory(topics: ArrayList<Topic>) {
        binding.rcvCategoriesTopic.adapter = TopicAdapter(topics)
        binding.rcvCategoriesTopic.layoutManager = GridLayoutManager(this, 2)
    }
}