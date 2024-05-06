package com.example.musicapp.screen.account.login

import android.content.res.ColorStateList
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.BR
import com.example.musicapp.R
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel : BaseObservable() {
    private val _isLogin = MutableLiveData<Boolean>()
    private val _isValidate = MutableLiveData<Boolean>()
    private val _forgotPasswordEvent = MutableLiveData<Boolean>()
    val forgotPasswordEvent : LiveData<Boolean> = _forgotPasswordEvent

    private var email: String = ""
    private var password: String = ""

    @Bindable
    fun getEmail(): String {
        return email
    }

    @Bindable
    fun getPassword(): String {
        return password
    }

    fun setEmail(email: String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    fun setPassword(password: String) {
        this.password = password
        notifyPropertyChanged(BR.password)
    }

    fun forgotPassword(){
        _forgotPasswordEvent.value = true
    }

    fun getIsLogin(): LiveData<Boolean> {
        return _isLogin
    }

    fun getIsValidate(): LiveData<Boolean> {
        return _isValidate
    }

    private fun validateLogin(): Boolean {
        return !(getEmail().isEmpty() || getPassword().isEmpty())
    }

    fun login() {
        if (validateLogin()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(getEmail(), getPassword())
                .addOnCompleteListener { task ->
                    _isLogin.value = task.isSuccessful
                }
        } else {
            _isValidate.value = validateLogin()
        }
    }

}