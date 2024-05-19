package com.example.musicapp.screen.exploreDetail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityExploreDetailBinding
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.screen.explore.ExploreViewModel
import com.example.musicapp.shared.utils.constant.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreDetailActivity : AppCompatActivity() {
    private val viewModel: ExploreViewModel by viewModel()
    private val binding by lazy {
        ActivityExploreDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_explore_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initValue()
        initViewModel()
        handelEvent()
    }

    private fun initValue() {
        val name = intent.getStringExtra(Constant.KEY_NAME)
        val nameData = intent.getStringExtra(Constant.KEY_INTENT_ITEM)
        binding.tvTitle.text = name
        nameData?.let { handelViewModel(nameData) }
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun handelViewModel(nameData: String) {
        when(nameData){
            Constant.PLAYLIST -> {

            }
            Constant.CATEGORIES -> {

            }
            Constant.RANK -> {

            }
            Constant.MOOD_TODAY -> {

            }
            Constant.ALBUM_NEW -> {

            }
            Constant.ALBUM_LOVE -> {

            }
        }
    }

    private fun handelEvent() {
        TODO("Not yet implemented")
    }
}