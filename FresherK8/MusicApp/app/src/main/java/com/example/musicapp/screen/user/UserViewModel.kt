package com.example.musicapp.screen.user

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class UserViewModel : ViewModel() {

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin : LiveData<Boolean> = _isLogin

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _image = MutableLiveData<Uri>()
    val image : LiveData<Uri> = _image

    init {
        initValueUser()
    }

    private fun initValueUser(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            _userName.value = user.email
            _image.value = user.photoUrl
            _isLogin.value = true
        }
    }
}