package com.collinsceleb.money_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.collinsceleb.money_app.R
import com.collinsceleb.money_app.data.AppDatabase
import com.collinsceleb.money_app.databinding.ActivityTransferBinding
import com.collinsceleb.money_app.factory.TransactionViewModelFactory
import com.collinsceleb.money_app.repository.TransactionRepository
import com.collinsceleb.money_app.utils.AuthUtils.logout
import com.collinsceleb.money_app.viewmodel.TransactionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
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
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.transfer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_accounts -> startActivity(Intent(this, AccountsActivity::class.java))
                R.id.nav_history -> startActivity(Intent(this, TransactionHistoryActivity::class.java))
                R.id.nav_logout -> logout(this)
            }
            true
        }
        binding.btnTransfer.setOnClickListener {
            val sourceAccount = binding.etSourceAccount.text.toString()
            val destinationAccount = binding.etDestinationAccount.text.toString()
            val transferAmount = binding.etAmount.text.toString()
            if (sourceAccount.isEmpty() || destinationAccount.isEmpty() || transferAmount.isEmpty()) {
                Snackbar.make(binding.root, "All fields are required", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val amount = transferAmount.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Snackbar.make(binding.root, "Enter a valid amount", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            showTransactionSummaryDialog(sourceAccount, destinationAccount, amount)
        }
    }

    private fun performTransferTransaction(
        source: String,
        destination: String,
        amount: Double
    ) {
        transactionViewModel.transferMoney(source, destination, amount)
        transactionViewModel.transferStatus.observe(this) { success ->
            if (success) {
                Snackbar.make(binding.root, "Successful Transfer", Snackbar.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Snackbar.make(
                    binding.root,
                    "Failed Transfer. Kindly Check your balance",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showTransactionSummaryDialog(source: String, destination: String, amount: Double) {
        val message = """
            Source Account: $source
            Destination Account: $destination
            Transfer Amount: $$amount
        """.trimIndent()
        AlertDialog.Builder(this).setTitle("Transfer Summary").setMessage(message)
            .setPositiveButton("Confirm") { _, _ ->
                performTransferTransaction(source, destination, amount)
            }.setNegativeButton("Cancel", null).show()
    }
}