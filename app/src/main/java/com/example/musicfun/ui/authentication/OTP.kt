package com.example.musicfun.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicfun.HomeActivity
import com.example.musicfun.R
import com.example.musicfun.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OTP : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    private lateinit var inputCode1: EditText
    private lateinit var inputCode2: EditText
    private lateinit var inputCode3: EditText
    private lateinit var inputCode4: EditText
    private lateinit var inputCode5: EditText
    private lateinit var inputCode6: EditText
    private lateinit var OTP:String
    private lateinit var resendToken: ForceResendingToken
    private lateinit var phoneNumber:String
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        auth = Firebase.auth
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val confirmBtn = binding.btnConfirm
        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("token")!!
        phoneNumber= intent.getStringExtra("phoneNumber")!!
        init()
        addTextChangeLister()
        confirmBtn.setOnClickListener{
            val typedOTP = inputCode1.text.toString()+inputCode2.text.toString()+inputCode3.text.toString()+inputCode4.text.toString()+inputCode5.text.toString()+inputCode6.text.toString()
            if(typedOTP.isNotEmpty()){
                if(typedOTP.length ==6){
                    val credential:PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP,typedOTP
                    )
                    signInWithPhoneAuthCredential(credential)
                }else{
                    Toast.makeText(this,"please enter correct OTP",Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this,"please enter OTP",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addTextChangeLister(){
        inputCode1.addTextChangedListener(EditTextWatcher(inputCode1))
        inputCode2.addTextChangedListener(EditTextWatcher(inputCode2))
        inputCode3.addTextChangedListener(EditTextWatcher(inputCode3))
        inputCode4.addTextChangedListener(EditTextWatcher(inputCode4))
        inputCode5.addTextChangedListener(EditTextWatcher(inputCode5))
        inputCode6.addTextChangedListener(EditTextWatcher(inputCode6))
    }
private fun init(){
    val auth = FirebaseAuth.getInstance()
    inputCode1 = binding.inputCode1
    inputCode2 = binding.inputCode2
    inputCode3 = binding.inputCode3
    inputCode4 = binding.inputCode4
    inputCode5 = binding.inputCode5
    inputCode6 = binding.inputCode6
    val confirmBtn = binding.btnConfirm
    val resend = binding.textResendOTP

}
    inner class EditTextWatcher(private val editText: EditText) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not implemented
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Not implemented
        }

        override fun afterTextChanged(s: Editable?) {
            val text = s.toString()
            when (editText.id) {
                R.id.inputCode1 -> if (text.length == 1) inputCode2.requestFocus()
                R.id.inputCode2 -> if (text.length == 1) inputCode3.requestFocus() else if (text.isEmpty()) inputCode1.requestFocus()
                R.id.inputCode3 -> if (text.length == 1) inputCode4.requestFocus() else if (text.isEmpty()) inputCode2.requestFocus()
                R.id.inputCode4 -> if (text.length == 1) inputCode5.requestFocus() else if (text.isEmpty()) inputCode3.requestFocus()
                R.id.inputCode5 -> if (text.length == 1) inputCode6.requestFocus() else if (text.isEmpty()) inputCode4.requestFocus()
                R.id.inputCode6 -> if (text.isEmpty()) inputCode5.requestFocus()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext,
                        "signInWithEmail:success",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val intent = Intent(this@OTP, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

}