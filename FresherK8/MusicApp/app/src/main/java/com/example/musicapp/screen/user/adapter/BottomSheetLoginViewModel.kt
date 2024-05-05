package com.example.musicapp.screen.user.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.shared.widgit.SnackBarManager


class BottomSheetLoginViewModel : ViewModel() {
    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin : LiveData<Boolean> = _isLogin

    private val _isSelectCheckbox = MutableLiveData<Boolean>()

    fun loginEmail(){
        _isLogin.value = _isSelectCheckbox.value == true
    }

    fun onCheckboxClicked(checked: Boolean) {
        _isSelectCheckbox.value = checked
    }

}