package com.collinsceleb.money_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.collinsceleb.money_app.R
import com.collinsceleb.money_app.databinding.ActivityMainBinding
import com.collinsceleb.money_app.utils.AuthUtils
import com.collinsceleb.money_app.utils.AuthUtils.logout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

open class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val autoLogoutTime = 5 * 60 * 1000L
    private val handler = Handler(Looper.getMainLooper())
    private val autoLogoutRunnable = Runnable { logout(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
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
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_accounts -> startActivity(Intent(this, AccountsActivity::class.java))
                R.id.nav_transfer -> startActivity(Intent(this, TransferActivity::class.java))
                R.id.nav_history -> startActivity(Intent(this, TransactionHistoryActivity::class.java))
                R.id.nav_logout -> logout(this)
            }
            true
        }
        resetAutoLogoutTimer()
    }

    override fun onStart() {
        super.onStart()
        checkUserSession()
        resetAutoLogoutTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        resetAutoLogoutTimer()
    }

    private fun checkUserSession() {
        val user = auth.currentUser
        if (user == null || !user.isEmailVerified) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    private fun resetAutoLogoutTimer() {
        handler.removeCallbacks(autoLogoutRunnable)
        handler.postDelayed(autoLogoutRunnable, autoLogoutTime)
    }

}