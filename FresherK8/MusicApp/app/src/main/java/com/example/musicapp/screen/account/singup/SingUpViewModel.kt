package com.example.musicapp.screen.account.singup

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapp.BR
import com.example.musicapp.data.repositories.UserRepository
import com.example.musicapp.shared.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SingUpViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val _isSingUp = MutableLiveData<Boolean>()
    private val _isValidateEqual = MutableLiveData<Boolean>()
    private val _isValidate = MutableLiveData<Boolean>()

    var email: String = ""
    var password: String = ""
    var rePassword: String = ""

    fun getIsSingUp(): LiveData<Boolean> {
        return _isSingUp
    }

    fun getIsValidate(): LiveData<Boolean> {
        return _isValidate
    }

    fun getIsValidateEqual(): LiveData<Boolean> {
        return _isValidateEqual
    }

    private fun validate(): Boolean {
        if (email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            _isValidate.value = false
            return false
        }
        if (password != rePassword) {
            _isValidateEqual.value = false
            return false
        }
        return true
    }

    fun singUp() {
        if (validate()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        launchTaskSync(
                            onRequest = { userRepository.createUser(user!!.uid) },
                            onSuccess = { _isSingUp.value = it },
                            onFailure = { message -> Log.e("singUp", "Failed: $message") },
                            onError = { exception.value = it }
                        )
                    } else {
                        _isSingUp.value = false
                    }
                }
        }
    }

}