package com.example.musicapp.screen.user.adapter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.databinding.LayoutBottomSheetPlaylistBinding
import com.example.musicapp.screen.account.AccountActivity
import com.example.musicapp.screen.user.playlistUser.PlaylistUserViewModel
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.ACCESS_RULES
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetPlaylist(
    private val mListener : () -> Unit
) : BottomSheetDialogFragment() {
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
        initViewModel()
        handlerEventViewModel()
        handlerEvent()
    }

    private fun handlerEvent() {
        binding?.btnCancel?.setOnClickListener { dismiss() }
    }

    private fun initViewModel() {
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
    }

    private fun handlerEventViewModel() {
        viewModel.isCreatePlaylist.observe(this){
            if (it){
                dismiss()
                mListener.invoke()
            }else{
                SnackBarManager.showMessage(binding?.button2, Constant.FAILURE)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}