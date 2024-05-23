package com.example.musicapp.screen.account.singup

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.databinding.FragmentSingUpBinding
import com.example.musicapp.shared.utils.constant.Constant
import com.example.musicapp.shared.widget.SnackBarManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingUpFragment : Fragment() {
    val binding by lazy {
        FragmentSingUpBinding.inflate(layoutInflater)
    }

    val viewModel : SingUpViewModel by viewModel()

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
        viewModel.getIsSingUp().observe(viewLifecycleOwner) {
            if (it){
                SnackBarManager.showMessage(binding.button, Constant.SING_UP_SUCCESS)
                binding.etEmail.text?.clear()
                binding.etPass.text?.clear()
                binding.etRePass.text?.clear()
            }else{
                SnackBarManager.showMessage(binding.button, Constant.SING_UP_FAILURE)
                binding.layoutLoading.visibility = View.INVISIBLE
                binding.layoutContentSingUp.visibility = View.VISIBLE
            }
        }

        viewModel.getIsValidate().observe(viewLifecycleOwner){
            SnackBarManager.showMessage(binding.button, Constant.LOGIN_EMPTY)
        }

        viewModel.getIsValidateEqual().observe(viewLifecycleOwner){
            SnackBarManager.showMessage(binding.button, Constant.SING_UP_EQUAL)
        }

        viewModel.loading.observe(viewLifecycleOwner){
            if (it){
                binding.layoutLoading.visibility = View.VISIBLE
                binding.layoutContentSingUp.visibility = View.INVISIBLE
            }else{
                binding.layoutLoading.visibility = View.INVISIBLE
                binding.layoutContentSingUp.visibility = View.VISIBLE
            }
        }
    }
}