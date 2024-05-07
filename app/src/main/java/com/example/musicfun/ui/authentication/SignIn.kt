package com.example.musicfun.ui.authentication

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
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

class SignIn : AppCompatActivity() {
   lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignIn.setOnClickListener {
           /* val intent = Intent(this@SignIn, HomeActivity::class.java)
            startActivity(intent)*/
            var email = binding.editTextTextEmailAddress.text.toString()
            var password = binding.editTextTextPassword.text.toString()
            if (email.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Email", Toast.LENGTH_LONG).show()
            }
            if (password.isNullOrEmpty()) {
                Toast.makeText(this, "Please Input Password", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this@SignIn, SignUp::class.java)
            startActivity(intent)
        }


    }




}