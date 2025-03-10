package com.collinsceleb.money_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collinsceleb.money_app.R
import com.collinsceleb.money_app.data.AppDatabase
import com.collinsceleb.money_app.databinding.ActivityTransactionHistoryBinding
import com.collinsceleb.money_app.factory.TransactionViewModelFactory
import com.collinsceleb.money_app.repository.TransactionRepository
import com.collinsceleb.money_app.ui.adapters.TransactionAdapter
import com.collinsceleb.money_app.utils.AuthUtils.logout
import com.collinsceleb.money_app.viewmodel.TransactionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class TransactionHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(
            TransactionRepository(
                AppDatabase.getDatabase(this).transactionDao()
            )
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.transactionHistory) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }
        recyclerView = binding.rvRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_accounts -> startActivity(Intent(this, AccountsActivity::class.java))
                R.id.nav_transfer -> startActivity(Intent(this, TransferActivity::class.java))
                R.id.nav_logout -> logout(this)
            }
            true
        }
        transactionViewModel.fetchAllTransactions.observe(this) { transaction ->
            binding.rvRecyclerview.adapter = TransactionAdapter(transaction)
        }
    }
}