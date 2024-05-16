package com.example.musicapp.screen.music.adapter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.databinding.LayoutBottomSheetAddSongPlaylistBinding
import com.example.musicapp.databinding.LayoutBottomSheetPlaylistBinding
import com.example.musicapp.screen.account.AccountActivity
import com.example.musicapp.screen.user.adapter.BottomSheetPlaylist
import com.example.musicapp.screen.user.adapter.PlaylistUserAdapter
import com.example.musicapp.screen.user.playlistUser.PlaylistUserViewModel
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.utils.constant.Constant.ACCESS_RULES
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetAddSongPlaylist(private val song: Song, private val imageView: ImageView) : BottomSheetDialogFragment() {
    private var binding: LayoutBottomSheetAddSongPlaylistBinding? = null
    private val viewModel: PlaylistUserViewModel by viewModel()
    private val playlistUserAdapter = PlaylistUserAdapter(::onItemClick, 2)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_add_song_playlist,
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
        initRecyclerView()
        handlerEventViewModel()
        handlerEvent()
    }

    private fun handlerEvent() {
        binding?.btnCancel?.setOnClickListener { dismiss() }
        binding?.btnAddPlaylistUser?.setOnClickListener { openBottomSheetCreatePlaylist() }
    }

    private fun initViewModel() {
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
    }

    private fun handlerEventViewModel() {
        viewModel.playlistUser.observe(this) {
            playlistUserAdapter.submitList(it)
        }

        viewModel.isInsertSongPlaylist.observe(this){
            if (it){
                SnackBarManager.showMessage(imageView, "Đã thêm vào playlist của bạn")
            }else{
                SnackBarManager.showMessage(imageView, "Bài hát đã tồn tại trong playlist của bạn")
            }
            dismiss()
        }
    }

    private fun initRecyclerView() {
        binding?.rcvAddPlaylistUser?.setAdapterLinearVertical(playlistUserAdapter)
    }

    private fun openBottomSheetCreatePlaylist() {
        val bottomSheet = BottomSheetPlaylist(::onItemClickBottomSheet)
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        bottomSheet.isCancelable = false
    }

    private fun onItemClickBottomSheet() {
        viewModel.fetchPlaylistsUser()
    }

    private fun onItemClick(b: Boolean, any: Any) {
        val playlistUser = any as PlaylistUser
        viewModel.insertSongPlaylistUser(playlistUser.playlistUserId, song.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}