package com.collinsceleb.money_app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.collinsceleb.money_app.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth

object AuthUtils {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun logout(context: Context) {
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)

        if (context is Activity) {
            context.finish()
        }
    }
}