package com.example.musicapp.screen.follow

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.data.model.Follow
import com.example.musicapp.databinding.ActivityFollowBinding
import com.example.musicapp.screen.follow.adapter.ArtistAdapter
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowActivity : AppCompatActivity() {
    private val viewModel : FollowViewModel by viewModel()

    private val followAdapter = ArtistAdapter(::onItemClick)
    private val binding by lazy {
        ActivityFollowBinding.inflate(layoutInflater)
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
        setUpViewModel()
        setUpRecyclerView()
        handlerViewModel()
    }

    private fun setUpViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpRecyclerView() {
        binding.rcvFollow.setAdapterLinearVertical(followAdapter)
    }

    private fun handlerViewModel() {
       viewModel.follows.observe(this){
           followAdapter.submitList(it)
       }
    }

    private fun onItemClick(follow: Follow) {

    }
}