package com.example.musicapp.screen.account.forgotPassword

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityForgotPasswordBinding
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.widget.SnackBarManager

class ForgotPasswordActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

    val viewModel by lazy {
        ForgotPasswordViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_forgot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViewModel()
        initHandlerViewModel()
        initHandlerEvent()
    }

    private fun initHandlerEvent() {
        binding.btnBackForgot.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initHandlerViewModel() {
        viewModel.getIsValidate().observe(this) {
            SnackBarManager.showMessage(binding.buttonSen, Constant.LOGIN_EMPTY)
        }

        viewModel.getIsLogin().observe(this) {
            if (it) {
                SnackBarManager.showMessage(binding.buttonSen, Constant.SEN_EMAIL_SUCCESS)
                binding.etEmailSen.text?.clear()
            } else {
                SnackBarManager.showMessage(binding.buttonSen, Constant.SEN_EMAIL_FAILURE)
            }
        }
    }
}