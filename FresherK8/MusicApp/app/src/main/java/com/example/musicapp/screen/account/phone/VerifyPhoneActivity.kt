package com.example.musicapp.screen.account.phone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityVerifyPhoneBinding

class VerifyPhoneActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityVerifyPhoneBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpInputs()
        handlerEvent()
    }

    private fun setUpInputs() {
        val editTexts = arrayOf(
            binding.editTextText1,
            binding.editTextText2,
            binding.editTextText3,
            binding.editTextText4,
            binding.editTextText5,
            binding.editTextText6
        )
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty() && i < editTexts.size - 1) {
                        editTexts[i + 1].requestFocus()
                    } else if (s.toString().trim().isEmpty() && i > 0) {
                        editTexts[i - 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) = Unit
            })
        }
    }

    private fun handlerEvent() {

    }
}