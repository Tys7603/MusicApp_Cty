package com.example.musicapp.screen.user.adapter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.musicapp.R
import com.example.musicapp.databinding.LayoutBottomSheetAccountBinding
import com.example.musicapp.screen.account.AccountActivity
import com.example.musicapp.screen.account.phone.PhoneLoginActivity
import com.example.musicapp.shared.utils.constant.Constant.ACCESS_RULES
import com.example.musicapp.shared.widget.SnackBarManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetLogin(
    private val mListener: (Boolean) -> Unit
) : BottomSheetDialogFragment() {
    private var binding: LayoutBottomSheetAccountBinding? = null
    private val viewModel: BottomSheetLoginViewModel by viewModel()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 123
        private const val FACEBOOK = "facebook"
        private const val GOOGLE = "google"
        private const val PHONE = "phone"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_account,
            null,
            false
        )
        binding?.root?.let {
            mBottomSheetDialog.setContentView(it)
            initHandler()
            it.viewTreeObserver.addOnGlobalLayoutListener {
                val behavior = BottomSheetBehavior.from(it.parent as View)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return mBottomSheetDialog
    }

    private fun initHandler() {
        mAuth = FirebaseAuth.getInstance()
        initViewModel()
        handlerEventViewModel()
        handlerEvent()
        setUpGoogleSignIn()
    }

    private fun handlerEvent() {
        binding?.btnSdt?.setOnClickListener { checkPolicyAccept(PHONE) }
        binding?.btnFace?.setOnClickListener { checkPolicyAccept(FACEBOOK) }
        binding?.btnGg?.setOnClickListener { checkPolicyAccept(GOOGLE) }
    }

    private fun initViewModel() {
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = requireActivity()
    }

    private fun handlerEventViewModel() {
        viewModel.getLoginClickedLiveData().observe(this) { isLogin ->
            if (isLogin) {
                startActivity(Intent(requireContext(), AccountActivity::class.java))
            } else {
                SnackBarManager.showMessage(binding?.checkBox, ACCESS_RULES)
            }
        }
    }

    private fun checkPolicyAccept(tag: String) {
        if (binding?.checkBox?.isChecked == true) {
            when (tag) {
                FACEBOOK -> {
                    SnackBarManager.showMessage(binding?.imageView, "Tính năng phát triển sau")
                }

                GOOGLE -> {
                    logoutGoogle()
                    signInGoogle()
                }

                PHONE -> {
                    startActivity(Intent(requireContext(), PhoneLoginActivity::class.java))
                }
            }
        } else {
            SnackBarManager.showMessage(binding?.checkBox, ACCESS_RULES)
        }
    }

    private fun logoutGoogle() {
        googleSignInClient.signOut()
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun setUpGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
                dismiss()
                mListener.invoke(true)
            } catch (e: ApiException) {
                Toast.makeText(
                    requireContext(),
                    "Google sign in failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    mListener.invoke(false)
                } else {
                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}