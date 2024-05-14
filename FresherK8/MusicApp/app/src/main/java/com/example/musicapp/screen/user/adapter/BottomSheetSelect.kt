package com.example.musicapp.screen.user.adapter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.databinding.LayoutBottomSheetPlaylistBinding
import com.example.musicapp.databinding.LayoutBottomSheetSelectBinding
import com.example.musicapp.screen.account.AccountActivity
import com.example.musicapp.screen.user.playlistUser.PlaylistUserViewModel
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.ACCESS_RULES
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetSelect(
    private val mListener: () -> Unit,
    private val keySelect: String
) : BottomSheetDialogFragment() {
    private var binding: LayoutBottomSheetSelectBinding? = null
    private val viewModel: PlaylistUserViewModel by viewModel()
    private val playlistUserAdapter = PlaylistUserAdapter(::onItemClick, 1)
    private var mPlaylistsUser: ArrayList<Int>? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_select,
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
        initRecyclerView()
    }

    private fun handlerEventViewModel() {
        viewModel.playlistUser.observe(this) {
            playlistUserAdapter.submitList(it)
        }
    }

    private fun handlerEvent() {
        binding!!.btnCancel.setOnClickListener {
            dismiss()
            mListener.invoke()
        }
        binding!!.cbSelectAll.setOnClickListener {
            val isChecked = binding!!.cbSelectAll.isChecked
            if (!isChecked) {
                playlistUserAdapter.noSelectAllItem()
                binding!!.cbSelectAll.text = SELECT_ALL
            } else {
                playlistUserAdapter.selectAllItem()
                binding!!.cbSelectAll.text = NO_SELECT
            }
        }
        binding!!.btnDeletePlaylistUser.setOnClickListener {
            val listString = Gson().toJson(mPlaylistsUser!!)
            viewModel.deletePlaylistUser(
                listString
            )
        }
    }

    private fun initViewModel() {
        binding!!.viewModel = viewModel
        binding!!.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        mPlaylistsUser = ArrayList()
        binding!!.rcvPlaylistSelect.setAdapterLinearVertical(playlistUserAdapter)
    }

    private fun onItemClick(boolean: Boolean, any: Any) {
        when (any) {
            is PlaylistUser -> {
                if (boolean) {
                    mPlaylistsUser!!.add(any.playlistUserId)
                } else {
                    mPlaylistsUser!!.remove(any.playlistUserId)
                }
            }

            is Boolean -> {
                if (boolean) {
                    binding!!.cbSelectAll.text = NO_SELECT
                    binding!!.cbSelectAll.isChecked = true
                } else {
                    binding!!.cbSelectAll.text = SELECT_ALL
                    binding!!.cbSelectAll.isChecked = false
                }
            }
        }
    }

    companion object {
        const val SELECT_ALL = "Chọn tất cả"
        const val NO_SELECT = "Bỏ chọn"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        mPlaylistsUser = null
    }
}