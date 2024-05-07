package com.example.musicfun.ui.authentication

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicfun.R
import com.example.musicfun.databinding.ActivityForgetPasswordBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ForgetPassword : AppCompatActivity() {
    lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var auth: FirebaseAuth
    val TAG = "jcy-ForgetPassword"
    private var isWaiting = false
    private var waitTime = 60
    private var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            waitTime--;
            if (waitTime == 0) {
                waitTime = 60
                isWaiting = false;
                binding.tvAfter.setText(" After ${waitTime}s ")
            } else {

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnResend.setOnClickListener {
            handler.sendEmptyMessageDelayed(0, 1000)
            send()
        }
        binding.btnSend.setOnClickListener {
            send()
        }
    }

    fun send() {

        if (isWaiting) {
            return
        }
        var email = binding.editTextTextEmailAddress.text.toString()
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "Please Input Email", Toast.LENGTH_LONG).show()
        }
        isWaiting = true;
        handler.sendEmptyMessageDelayed(0, 1000)
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 发送密码重置电子邮件成功
                    // 这里可以提示用户检查他们的电子邮件
                    Log.d(TAG, "发送密码重置电子邮件成功，这里可以提示用户检查他们的电子邮件")
                    Toast.makeText(
                        this,
                        "Successfully sent password reset email, please check the email",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    // 发送密码重置电子邮件失败
                    // 这里可以处理错误，例如提示用户
                    Log.d(TAG, "发送密码重置电子邮件失败，${task.exception}")
                    Toast.makeText(this, "Sending password reset email failed", Toast.LENGTH_LONG)
                        .show()

                }
            }
    }
}