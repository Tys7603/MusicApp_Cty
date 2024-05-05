package com.example.musicapp.screen.user.adapter

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.shared.widgit.SnackBarManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetLogin : BottomSheetDialogFragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var binding: LayoutBottomSheetAccountBinding
    private val viewModel : BottomSheetLoginViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_bottom_sheet_account, null, false)
        mBottomSheetDialog.setContentView(binding.root)
        return mBottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post { setupBottomSheetBehavior() }
        initViewModel()
        handlerEventViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupBottomSheetBehavior() {
        val bottomSheet = binding.root.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet.viewTreeObserver.addOnGlobalLayoutListener {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = bottomSheet.height
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun handlerEventViewModel() {
      viewModel.isLogin.observe(this){isLogin ->
          if (isLogin){
              SnackBarManager.showMessage(binding.linearLayout3, "ok")
          }else{
              SnackBarManager.showMessage(binding.linearLayout3, "no")
          }
      }
    }
}