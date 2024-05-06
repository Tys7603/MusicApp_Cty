package com.example.musicapp.screen.account.forgotPassword

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.BR
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : BaseObservable() {

    private var email: String = ""
    private val _isValidate = MutableLiveData<Boolean>()
    private val _isSenEmail = MutableLiveData<Boolean>()

    @Bindable
    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    fun getIsValidate(): LiveData<Boolean> {
        return _isValidate
    }

    private fun validateLogin(): Boolean {
        return getEmail().isNotEmpty()
    }

    fun getIsLogin(): LiveData<Boolean> {
        return _isSenEmail
    }


    fun senEnEmailResetPassword(){
        if (validateLogin()){
            FirebaseAuth.getInstance().sendPasswordResetEmail(getEmail())
                .addOnCompleteListener { task ->
                    _isSenEmail.value = task.isSuccessful
                }
        }else{
            _isValidate.value = false
        }

    }
}