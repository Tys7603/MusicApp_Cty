package com.example.musicapp.screen.user.adapter

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.data.model.Playlist
import com.example.musicapp.data.model.PlaylistUser
import com.example.musicapp.databinding.LayoutBottomSheetSelectBinding
import com.example.musicapp.screen.user.PlaylistLoveViewModel
import com.example.musicapp.screen.user.PlaylistUserViewModel
import com.example.musicapp.shared.extension.setAdapterLinearVertical
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
    private val userViewModel: PlaylistUserViewModel by viewModel()
    private val loveViewModel: PlaylistLoveViewModel by viewModel()
    private val playlistUserAdapter = PlaylistUserAdapter(::onItemClick, 1)
    private val playlistLoveAdapter = PlaylistLoveAdapter(::onItemClick, 1)
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
        when(keySelect){
            KEY_SELECT_PLAYLIST_USER -> {
                userViewModel.playlistUser.observe(this) {
                    playlistUserAdapter.submitList(it)
                }
            }
            KEY_SELECT_PLAYLIST_LOVE ->{
                loveViewModel.playlists.observe(this){
                    playlistLoveAdapter.submitList(it)
                }
            }
        }
    }

    private fun handlerEvent() {
        binding?.btnCancel?.setOnClickListener {
            dismiss()
            mListener.invoke()
        }

        binding?.cbSelectAll?.setOnClickListener {
            val isChecked = binding?.cbSelectAll?.isChecked

            when(keySelect){
                KEY_SELECT_PLAYLIST_USER -> {
                    if (!isChecked!!) {
                        playlistUserAdapter.noSelectAllItem()
                        binding?.cbSelectAll?.text = SELECT_ALL
                    } else {
                        playlistUserAdapter.selectAllItem()
                        binding?.cbSelectAll?.text = NO_SELECT
                    }
                }
                KEY_SELECT_PLAYLIST_LOVE ->{
                    if (!isChecked!!) {
                        playlistLoveAdapter.noSelectAllItem()
                        binding?.cbSelectAll?.text = SELECT_ALL
                    } else {
                        playlistLoveAdapter.selectAllItem()
                        binding?.cbSelectAll?.text = NO_SELECT
                    }
                }
            }
        }
        binding?.btnDeletePlaylistUser?.setOnClickListener {
            val listString = Gson().toJson(mPlaylistsUser)

            when(keySelect){
                KEY_SELECT_PLAYLIST_USER -> {
                    userViewModel.deletePlaylistUser(
                        listString
                    )
                }
                KEY_SELECT_PLAYLIST_LOVE ->{
                    loveViewModel.deletePlaylistLove(
                        listString
                    )
                }
            }


        }
    }

    private fun initViewModel() {
        when(keySelect){
            KEY_SELECT_PLAYLIST_USER -> {
                binding?.viewModel = userViewModel
            }
            KEY_SELECT_PLAYLIST_LOVE ->{
                binding?.loveViewModel = loveViewModel
            }
        }
        binding?.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        mPlaylistsUser = ArrayList()
        when(keySelect){
            KEY_SELECT_PLAYLIST_USER -> {
                binding?.rcvPlaylistSelect?.setAdapterLinearVertical(playlistUserAdapter)
            }
            KEY_SELECT_PLAYLIST_LOVE ->{
                binding?.rcvPlaylistSelect?.setAdapterLinearVertical(playlistLoveAdapter)
            }
        }
    }

    private fun onItemClick(boolean: Boolean, any: Any) {

        when(keySelect){
            KEY_SELECT_PLAYLIST_USER -> {
                when (any) {
                    is PlaylistUser -> {
                        if (boolean) {
                            mPlaylistsUser?.add(any.playlistUserId)
                        } else {
                            mPlaylistsUser?.remove(any.playlistUserId)
                        }
                    }

                    is Int -> {
                        if (boolean) {
                            binding?.cbSelectAll?.text = NO_SELECT
                            binding?.cbSelectAll?.isChecked = true
                        } else {
                            binding?.cbSelectAll?.text = SELECT_ALL
                            binding?.cbSelectAll?.isChecked = false
                        }
                    }
                }
            }
            KEY_SELECT_PLAYLIST_LOVE ->{
                when (any) {
                    is Playlist -> {
                        if (boolean) {
                            mPlaylistsUser?.add(any.playlistUserLoveId)
                        } else {
                            mPlaylistsUser?.remove(any.playlistUserLoveId)
                        }
                    }

                    is Int -> {
                        if (boolean) {
                            binding?.cbSelectAll?.text = NO_SELECT
                            binding?.cbSelectAll?.isChecked = true
                        } else {
                            binding?.cbSelectAll?.text = SELECT_ALL
                            binding?.cbSelectAll?.isChecked = false
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val SELECT_ALL = "Chọn tất cả"
        const val NO_SELECT = "Bỏ chọn"
        const val KEY_SELECT_PLAYLIST_LOVE = "playlist_love"
        const val KEY_SELECT_PLAYLIST_USER = "playlist_user"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        mPlaylistsUser = null
    }
}