package com.example.musicapp.screen.user.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.screen.account.AccountActivity
import com.example.musicapp.screen.account.phone.PhoneLoginActivity
import com.example.musicapp.shared.utils.constant.Constant.ACCESS_RULES
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetLogin : BottomSheetDialogFragment() {
    private var binding: LayoutBottomSheetAccountBinding? = null
    private val viewModel: BottomSheetLoginViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_account,
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
        binding?.btnSdt?.setOnClickListener { startActivity(Intent(requireContext(), PhoneLoginActivity::class.java)) }
        binding?.btnFace?.setOnClickListener { SnackBarManager.showMessage(binding?.imageView, "Tính năng phát triển sau") }
        binding?.btnGg?.setOnClickListener { SnackBarManager.showMessage(binding?.imageView, "Tính năng phát triển sau") }
    }

    private fun initViewModel() {
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = requireActivity()
    }

    private fun handlerEventViewModel() {
        viewModel.getLoginClickedLiveData().observe(this) { isLogin ->
            if (isLogin){
                startActivity(Intent(requireContext(), AccountActivity::class.java))
            }else{
                SnackBarManager.showMessage(binding?.checkBox, ACCESS_RULES)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}