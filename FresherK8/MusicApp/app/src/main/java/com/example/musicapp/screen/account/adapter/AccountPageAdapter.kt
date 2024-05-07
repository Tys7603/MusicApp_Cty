package com.example.musicapp.screen.account.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.musicapp.screen.account.login.LoginFragment
import com.example.musicapp.screen.account.singup.SingUpFragment

class AccountPageAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> SingUpFragment()
            else -> throw RuntimeException()
        }
    }
}