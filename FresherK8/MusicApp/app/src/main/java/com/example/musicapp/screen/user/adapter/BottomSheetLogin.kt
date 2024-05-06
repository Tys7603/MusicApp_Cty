package com.example.musicapp.screen.user.adapter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.screen.account.AccountActivity
import com.example.musicapp.shared.utils.constant.Constant.ACCESS_RULES
import com.example.musicapp.shared.widgit.SnackBarManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetLogin : BottomSheetDialogFragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var binding: LayoutBottomSheetAccountBinding
    private val viewModel: BottomSheetLoginViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_account,
            null,
            false
        )
        mBottomSheetDialog.setContentView(binding.root)
        initHandler()
        return mBottomSheetDialog
    }

    private fun initHandler() {
        initViewModel()
        handlerEventViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
    }

    private fun handlerEventViewModel() {
        viewModel.getLoginClickedLiveData().observe(this) { isLogin ->
            if (isLogin){
                startActivity(Intent(requireContext(), AccountActivity::class.java))
            }else{
                SnackBarManager.showMessage(binding.checkBox, ACCESS_RULES)
            }
        }
    }
}