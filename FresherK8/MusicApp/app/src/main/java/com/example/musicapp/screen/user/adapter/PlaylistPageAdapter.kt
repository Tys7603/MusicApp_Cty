package com.example.musicapp.screen.user.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.musicapp.screen.account.login.LoginFragment
import com.example.musicapp.screen.account.singup.SingUpFragment
import com.example.musicapp.screen.user.playlistLove.PlaylistLoveFragment
import com.example.musicapp.screen.user.playlistUser.PlaylistUserFragment

class PlaylistPageAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaylistUserFragment()
            1 -> PlaylistLoveFragment()
            else -> throw RuntimeException()
        }
    }
}