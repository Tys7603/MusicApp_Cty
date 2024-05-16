    package com.example.musicapp.screen.account.singup

    import android.content.Context
    import android.net.Uri
    import android.util.Log
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import com.example.musicapp.data.repositories.userRepository.UserRepository
    import com.example.musicapp.shared.base.BaseViewModel
    import com.example.musicapp.shared.utils.constant.Constant
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.userProfileChangeRequest

    class SingUpViewModel(private val userRepository: UserRepository, context: Context) : BaseViewModel() {

        private val _isSingUp = MutableLiveData<Boolean>()
        private val _isValidateEqual = MutableLiveData<Boolean>()
        private val _isValidate = MutableLiveData<Boolean>()
        private val _loading = MutableLiveData<Boolean>()
        val loading : LiveData<Boolean> = _loading

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
            return if (email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                _isValidate.value = false
                false
            } else if (password != rePassword) {
                _isValidateEqual.value = false
                false
            } else {
                true
            }
        }

        fun singUp() {
            _loading.value = true
            if (validate()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser
                            user?.let {
                                launchTaskSync(
                                    onRequest = { userRepository.createUser(user.uid) },
                                    onSuccess = { updateProfile() },
                                    onFailure = { message -> Log.e("signUp", "Failed: $message") },
                                    onError = { exception.value = it }
                                )
                            }
                        } else {
                            _isSingUp.value = false
                        }
                    }
            }
        }

        private fun updateProfile(){
            val profileUpdates = userProfileChangeRequest {
                photoUri = Uri.parse(Constant.URL_IMAGE)
            }

            FirebaseAuth.getInstance().currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _isSingUp.value = true
                        _loading.value = false
                    }
                }
        }

    }