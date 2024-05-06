package com.example.musicapp.screen.user.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BottomSheetLoginViewModel : ViewModel() {
    private val _isLogin = MutableLiveData<Boolean>()

    private val _isSelectCheckbox = MutableLiveData<Boolean>()

    fun loginEmail(){
        _isLogin.value = _isSelectCheckbox.value == true
    }

    fun getLoginClickedLiveData(): LiveData<Boolean> {
        return _isLogin
    }

    fun onCheckboxClicked(checked: Boolean) {
        _isSelectCheckbox.value = checked
    }

}