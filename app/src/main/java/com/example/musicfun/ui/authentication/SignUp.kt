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
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        // 设置Navigation Button监听
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        auth = Firebase.auth
        binding.btnSignUp.setOnClickListener {
            var name = binding.editTextText.text.toString()
            var email = binding.editTextTextEmailAddress.text.toString()
            var password = binding.editTextTextPassword.text.toString()
            var passwordAgain = binding.editTextTextPasswordAgain.text.toString()
            if (name.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Name", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (email.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!password.equals(passwordAgain)) {
                Toast.makeText(this, "Please make sure the passwords input are the same", Toast.LENGTH_LONG).show()
                return@setOnClickListener
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