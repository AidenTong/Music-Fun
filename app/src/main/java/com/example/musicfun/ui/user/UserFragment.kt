package com.example.musicfun.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.musicfun.databinding.DialogEdittextBinding
import com.example.musicfun.databinding.DialogPhoneBinding
import com.example.musicfun.databinding.FragmentUserBinding
import com.example.musicfun.ui.authentication.SignIn
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val TAG = "jcy-UserFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth

        return root
    }

    override fun onResume() {
        super.onResume()
        binding.tvPhone.setText(auth.currentUser?.phoneNumber?:"")
        binding.tvEmail.setText(auth.currentUser?.email?:"")
        binding.tvName.setText(auth.currentUser?.displayName?:"")
      /*  binding.tvName.setOnClickListener {
            var editBinding  = DialogEdittextBinding.inflate(layoutInflater)
            editBinding.itemEd.setHint("enter user name")
            editBinding.itemEd.setText(binding.tvName.text.toString())
            AlertDialog.Builder(requireContext())
                .setTitle("Do you want to update your username?")
                .setView(editBinding.itemLinEd)
                .setPositiveButton("Update") { dialog, i ->
                    dialog.dismiss()
                   var newName =  editBinding.itemEd.text.toString()
                    if (newName.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "Please Input Name", Toast.LENGTH_LONG).show()
                    }
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(newName) // 设置新的用户名
                        .build()
                    auth.currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener(OnCompleteListener<Void?> { task ->
                            if (task.isSuccessful) {
                                // 用户信息更新成功
                                Log.d(TAG, "updateProfile 用户信息更新成功 ")
                                Toast.makeText(
                                    requireContext(),
                                    "User name updated successfully",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                binding.tvName.setText(newName)
                            } else {
                                // 用户信息更新失败
                                Log.w(TAG, "updateProfile 用户信息更新失败 ", task.exception)
                                Toast.makeText(
                                    requireContext(),
                                    "User name updated  Failed",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        })
                }
                .setNegativeButton("Cancel") { dialog, i ->
                    dialog.dismiss()
                }.create().show()
        }
        binding.tvEmail.setOnClickListener {
            var editBinding  = DialogEdittextBinding.inflate(layoutInflater)
            editBinding.itemEd.setHint("enter email")
            editBinding.itemEd.setText(binding.tvEmail.text.toString())
            AlertDialog.Builder(requireContext())
                .setTitle("Do you want to update your email?")
                .setView(editBinding.itemLinEd)
                .setPositiveButton("Update") { dialog, i ->
                    dialog.dismiss()
                   var email =  editBinding.itemEd.text.toString()
                    if (email.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "Please Input Email", Toast.LENGTH_LONG).show()
                    }
                    auth.currentUser?.updateEmail(email)
                        ?.addOnCompleteListener(OnCompleteListener<Void?> { task ->
                            if (task.isSuccessful) {
                                // 用户信息更新成功
                                Log.d(TAG, "updateEmail successfully ")
                                Toast.makeText(
                                    requireContext(),
                                    "Update Email  successfully",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                binding.tvEmail.setText(email)
                            } else {
                                // 用户信息更新失败
                                Log.w(TAG, "updateEmail Failed ", task.exception)
                                Toast.makeText(
                                    requireContext(),
                                    "Update Email  Failed",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        })
                }
                .setNegativeButton("Cancel") { dialog, i ->
                    dialog.dismiss()
                }.create().show()
        }
        binding.tvPhone.setOnClickListener {
            var editBinding  = DialogPhoneBinding.inflate(layoutInflater)
            editBinding.itemPhone.setText(binding.tvPhone.text.toString())
            editBinding.btnSend.setOnClickListener {
                var phoneNumber =  editBinding.itemPhone.text.toString()
                if (phoneNumber.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Please Input phone number", Toast.LENGTH_LONG).show()
                }

            }
            AlertDialog.Builder(requireContext())
                .setTitle("Do you want to update your phone number?")
                .setView(editBinding.itemLinEd)
                .setPositiveButton("Update") { dialog, i ->
                    dialog.dismiss()

                }
                .setNegativeButton("Cancel") { dialog, i ->
                    dialog.dismiss()
                }.create().show()
       } */
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Do you want to log out?")
                .setPositiveButton("OK") { dialog, i ->
                    dialog.dismiss()
                    auth.signOut()
                    startActivity(Intent(requireContext(),SignIn::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Cancel") { dialog, i ->
                    dialog.dismiss()
                }.create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}