package com.example.musicapp.screen.account

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityAccountBinding
import com.example.musicapp.screen.account.adapter.AccountPageAdapter
import com.example.musicapp.shared.utils.constant.Constant
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AccountActivity : AppCompatActivity() {
    val binding : ActivityAccountBinding by lazy {
        ActivityAccountBinding.inflate(layoutInflater)
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_account)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initTabLayout()
        initHandlerEvent()
    }

    private fun initHandlerEvent() {
        binding.btnBackAccount.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun initTabLayout() {
        val pagerAdapter = AccountPageAdapter(this)
        binding.viewPager.setAdapter(pagerAdapter)
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.setText(Constant.LOGIN)
                1 -> tab.setText(Constant.SING_UP)
            }
        }.attach()
    }
}