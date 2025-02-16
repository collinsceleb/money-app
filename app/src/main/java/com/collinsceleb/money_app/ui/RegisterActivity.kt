package com.collinsceleb.money_app.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.collinsceleb.money_app.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.register) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }
        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Please, kindly fill all the required fields",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Snackbar.make(binding.root, "Ensure passwords are matched", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    emailVerification()
                } else {
                    Snackbar.make(
                        binding.root,
                        "Failed registration: ${task.exception?.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            binding.btnBackToLogin.setOnClickListener {
                finish()
            }
        }
    }

    private fun emailVerification() {
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Snackbar.make(binding.root, "Email verification sent.", Snackbar.LENGTH_LONG).show()
                auth.signOut()
                finish()
            } else {
                Snackbar.make(binding.root, "Verification email failed!", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}