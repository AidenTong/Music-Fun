package com.example.musicfun.ui.authentication

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicfun.R
import com.example.musicfun.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUp : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    val TAG = "jcy-SignUp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        actionBar?.setCustomView(R.layout.actionbar_title)
        val customView = actionBar?.customView
        val backButton = customView?.findViewById<ImageButton>(R.id.backButton)
        backButton?.setOnClickListener {
            // Handle the click event for the backward button
            onBackPressedDispatcher.onBackPressed()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        binding.btnSignUp.setOnClickListener {
            var name = binding.editTextText.text.toString()
            var email = binding.editTextTextEmailAddress.text.toString()
            var password = binding.editTextTextPassword.text.toString()
            var passwordAgain = binding.editTextTextPasswordAgain.text.toString()
            if (name.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Name", Toast.LENGTH_LONG).show()
            }
            if (email.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Email", Toast.LENGTH_LONG).show()
            }
            if (password.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Password", Toast.LENGTH_LONG).show()
            }
            if (!password.equals(passwordAgain)) {
                Toast.makeText(this, "Two passwords do not match", Toast.LENGTH_LONG).show()
            }
            Log.d(TAG, "SignUp email $email ")
            Log.d(TAG, "SignUp password $password ")
            Log.d(TAG, "SignUp passwordAgain $passwordAgain ")
            Log.d(TAG, "SignUp name $name ")
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name) // 设置新的用户名
                            .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                if (task.isSuccessful) {
                                    // 用户信息更新成功
                                    Log.d(TAG, "updateProfile 用户信息更新成功 ")
                                    Toast.makeText(
                                        baseContext,
                                        "SignUp Successed",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                    finish()
                                } else {
                                    // 用户信息更新失败
                                    Log.w(TAG, "updateProfile 用户信息更新失败 ", task.exception)
                                    Toast.makeText(
                                        baseContext,
                                        "SignUp Successed",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                    finish()
                                }
                            })

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
        binding.btnSignIn.setOnClickListener { finish() }
    }
}