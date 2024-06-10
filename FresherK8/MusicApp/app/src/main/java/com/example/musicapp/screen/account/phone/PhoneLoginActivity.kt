package com.example.musicapp.screen.account.phone

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityPhoneLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPhoneLoginBinding.inflate(layoutInflater)
    }
    private var storedVerificationId : String? = null
    private var resendToken : String? = null
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnSenCode.isEnabled = false
        handlerEvent()
        listenerOnTextChange()
    }

    private fun listenerOnTextChange() {
        binding.etPhone.addTextChangedListener ( object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeBackgroundButton(s?.length ?: 0)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        } )
    }

    private fun changeBackgroundButton(count : Int){
        if (count > 0){
            binding.btnSenCode.isEnabled = true
            binding.btnSenCode.setBackgroundColor(getColor(R.color.teal))
            binding.btnSenCode.setTextColor(getColor(R.color.white))
        }else{
            binding.btnSenCode.isEnabled = false
            binding.btnSenCode.setBackgroundColor(getColor(R.color.gray))
            binding.btnSenCode.setTextColor(getColor(R.color.black))
        }
        binding.tvShowError.visibility = View.GONE
    }

    private fun handlerEvent() {
        binding.imgBackPhone.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.btnSenCode.setOnClickListener { checkValidateShowMessage(binding.etPhone.length()) }
    }

    private fun checkValidateShowMessage(count : Int){
        if (count == 9){
            senCodeVerifyPhoneNumber(binding.etPhone.text.toString().trim())
            binding.tvShowError.visibility = View.GONE
        }else{
            binding.tvShowError.visibility = View.VISIBLE
        }
    }

    private fun senCodeVerifyPhoneNumber(phone : String) {
        val phoneNumber = "+84$phone"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            signInWithPhoneAuthCredential(credential)
           startActivity(Intent(this@PhoneLoginActivity, VerifyPhoneActivity::class.java))
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.d("PhoneLoginActivity", "onVerificationFailed: $e")
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token.toString()
        }
    }

}