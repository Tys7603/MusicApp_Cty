package com.example.musicapp.screen.user.adapter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.databinding.LayoutBottomSheetPlaylistBinding
import com.example.musicapp.screen.account.AccountActivity
import com.example.musicapp.screen.user.playlistUser.PlaylistUserViewModel
import com.example.musicapp.shared.utils.constant.Constant.ACCESS_RULES
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetPlaylist : BottomSheetDialogFragment() {
    private var binding: LayoutBottomSheetPlaylistBinding? = null
    private val viewModel: PlaylistUserViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_playlist,
            null,
            false
        )
        binding?.root?.let { mBottomSheetDialog.setContentView(it) }
        initHandler()
        return mBottomSheetDialog
    }

    private fun initHandler() {
        initViewModel()
        handlerEventViewModel()
    }

    private fun initViewModel() {
        binding!!.viewModel = viewModel
        binding!!.lifecycleOwner = requireActivity()
    }

    private fun handlerEventViewModel() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}