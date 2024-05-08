package com.example.musicfun.ui.authentication

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicfun.R
import com.example.musicfun.HomeActivity
import com.example.musicfun.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {
   lateinit var binding:ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    val TAG = "jcy-SignIn"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.btnSignIn.setOnClickListener {
           /* */
            var email = binding.editTextTextEmailAddress.text.toString()
            var password = binding.editTextTextPassword.text.toString()
            if (email.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "signInWithEmail:success",
                            Toast.LENGTH_SHORT,
                        ).show()
                        val intent = Intent(this@SignIn, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this@SignIn, SignUp::class.java)
            startActivity(intent)
        }
        binding.btnForgetPassword.setOnClickListener {
            val intent = Intent(this@SignIn, ForgetPassword::class.java)
            startActivity(intent)
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@SignIn, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}