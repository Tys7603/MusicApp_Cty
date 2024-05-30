package com.example.musicapp.screen.songDetail.adapter

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.LayoutBottomSheetSongDetailBinding
import com.example.musicapp.screen.music.adapter.BottomSheetAddSongPlaylist
import com.example.musicapp.screen.user.adapter.BottomSheetLogin
import com.example.musicapp.shared.utils.DownloadMusic
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth

class BottomSheetSongDetail(
    private var song: Song
) : BottomSheetDialogFragment() {
    private var binding: LayoutBottomSheetSongDetailBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_song_detail,
            null,
            false
        )
        binding?.root?.let {
            mBottomSheetDialog.setContentView(it)
            initHandler()
            it.viewTreeObserver.addOnGlobalLayoutListener {
                val behavior = BottomSheetBehavior.from(it.parent as View)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return mBottomSheetDialog
    }

    private fun initHandler() {
        initValue()
        handlerEvent()
    }

    private fun initValue() {
        binding?.song = song
    }

    private fun handlerEvent() {
        binding?.btnCancelSongDetail?.setOnClickListener { dismiss() }
        binding?.btnDownSongDetail?.setOnClickListener { downloadMusic() }
        binding?.btnAddPlaylistSongDetail?.setOnClickListener { addPlaylist() }
    }

    private fun downloadMusic() {
        DownloadMusic.downloadMusic(requireContext(), song)
    }

    private fun addPlaylist(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val bottomSheet = BottomSheetAddSongPlaylist(song, binding?.imageView10!!)
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        } else {
            openBottomSheetLogin()
        }
    }

    private fun openBottomSheetLogin(){
        val bottomSheetLogin = BottomSheetLogin{}
        bottomSheetLogin.show(parentFragmentManager, bottomSheetLogin.tag)
    }
}