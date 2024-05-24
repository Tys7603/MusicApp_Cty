package com.example.musicapp.screen.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.request.transition.Transition.ViewAdapter
import com.example.musicapp.R
import com.example.musicapp.data.model.Search
import com.example.musicapp.data.model.SearchAll
import com.example.musicapp.databinding.ActivitySearchBinding
import com.example.musicapp.screen.search.adapter.SearchAdapter
import com.example.musicapp.screen.search.adapter.ViewPagerAdapter
import com.example.musicapp.screen.search.base.BaseFragment
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter = SearchAdapter(::onItemClick)
    private var mListDefault = mutableListOf<SearchAll>()
    private val binding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
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
        initViewModel()
        initRecyclerView()
        handlerEventViewModel()
        handlerEvent()
        visibilityView(true)
        binding.searchView.requestFocus()
    }

    private fun visibilityView(boolean: Boolean){
        if (boolean){
            binding.tabLayoutSearch.visibility = View.GONE
            binding.viewPager.visibility = View.INVISIBLE
            binding.rcvSearch.visibility = View.VISIBLE
        }else{
            binding.tabLayoutSearch.visibility = View.VISIBLE
            binding.viewPager.visibility = View.VISIBLE
            binding.rcvSearch.visibility = View.INVISIBLE
        }
    }

    private fun setUpTabLayout(search: Search) {
        val adapter = ViewPagerAdapter(
            this,
            search.playlists,
            search.albums,
            search.songs,
            search.musicVideos
        )
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayoutSearch, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.songs)
                1 -> getString(R.string.playlists)
                2 -> getString(R.string.albums)
                3 -> getString(R.string.music_videos)
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }.attach()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        binding.rcvSearch.setAdapterLinearVertical(searchAdapter)
    }

    private fun handlerEventViewModel() {
        viewModel.searchAll.observe(this) {
            searchAdapter.submitList(it)
            mListDefault = it.toMutableList()
        }

        viewModel.search.observe(this){
            setUpTabLayout(it)
        }
    }

    private fun handlerEvent() {
        binding.btnCancelSearch.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                query?.let {
                    filterList(it)
                }
                visibilityView(true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterList(it)
                }
                visibilityView(true)
                return true
            }
        })

        binding.searchView.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Xử lý sự kiện khi người dùng nhấn Enter trên bàn phím ở đây
                val query = binding.searchView.query.toString()

                visibilityView(false)
                viewModel.fetchSearch(query)
                Log.d("TAG", "handlerEvent: " + query)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun filterList(query : String){
        val filterList = ArrayList<SearchAll>()

        for (str in mListDefault){
            if (str.name.lowercase().contains(query)){
                filterList.add(str)
            }
        }

        if (filterList.isNotEmpty()){
            searchAdapter.submitList(filterList)
        }
    }

    private fun onItemClick(name: String) {
        binding.searchView.setQuery(name, true)
        viewModel.fetchSearch(name)
        visibilityView(false)
    }
}