package com.collinsceleb.money_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.collinsceleb.money_app.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.login) { v, insets ->
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
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Please, kindly fill all the required fields",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    if (auth.currentUser?.isEmailVerified == true) {
                        startActivity(Intent(this, AccountsActivity::class.java))
                        finish()
                    } else {
                        Snackbar.make(binding.root, "Ensure your email is verified", Snackbar.LENGTH_LONG).show()
                        auth.signOut()
                    }
                } else {
                    Snackbar.make(binding.root, "Failed login: ${task.exception?.message}", Snackbar.LENGTH_LONG).show()
                }
            }
            binding.btnRegister.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            startActivity(Intent(this, AccountsActivity::class.java))
            finish()
        }
    }
}