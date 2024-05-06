package com.example.musicapp.screen.account.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.databinding.FragmentLoginBinding
import com.example.musicapp.screen.account.forgotPassword.ForgotPasswordActivity
import com.example.musicapp.screen.main.MainActivity
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.widget.SnackBarManager

class LoginFragment : Fragment() {
    val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    val viewModel by lazy {
        LoginViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initHandlerViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initHandlerViewModel() {
        viewModel.getIsValidate().observe(viewLifecycleOwner) {
            SnackBarManager.showMessage(binding.button, Constant.LOGIN_EMPTY)
        }

        viewModel.getIsLogin().observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra(Constant.KEY_USER, true)
                startActivity(intent)
            } else {
                SnackBarManager.showMessage(binding.button, Constant.LOGIN_FAILURE)
            }
        }

        viewModel.forgotPasswordEvent.observe(viewLifecycleOwner) {
            startActivity(Intent(requireContext(), ForgotPasswordActivity::class.java))
        }

    }
}