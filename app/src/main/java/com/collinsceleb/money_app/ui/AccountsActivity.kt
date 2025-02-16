package com.collinsceleb.money_app.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collinsceleb.money_app.R
import com.collinsceleb.money_app.data.AccountDao
import com.collinsceleb.money_app.data.AppDatabase
import com.collinsceleb.money_app.databinding.ActivityAccountsBinding
import com.collinsceleb.money_app.databinding.ActivityLoginBinding
import com.collinsceleb.money_app.factory.AccountViewModelFactory
import com.collinsceleb.money_app.repository.AccountRepository
import com.collinsceleb.money_app.repository.AccountRepositoryInterface
import com.collinsceleb.money_app.repository.MockAccountRepository
import com.collinsceleb.money_app.ui.adapters.AccountAdapter
import com.collinsceleb.money_app.viewmodel.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AccountsActivity : AppCompatActivity() {
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var accountAdapter: AccountAdapter
    private var useMockData = false // Set to true to use mock data, false for real data
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts) // Replace with your layout file
        recyclerView = findViewById(R.id.recylerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val accountDao: AccountDao = AppDatabase.getDatabase(application, applicationScope).accountDao()
        val accountRepository: AccountRepositoryInterface = if (useMockData) {
            MockAccountRepository()
        } else {
            AccountRepository(accountDao)
        }
        val factory = AccountViewModelFactory(application, accountRepository)
        accountViewModel = ViewModelProvider(this, factory)[AccountViewModel::class.java]
        accountAdapter = AccountAdapter()
        recyclerView.adapter = accountAdapter
        accountViewModel.accounts.observe(this) { accounts ->
            accountAdapter.submitList(accounts)
        }
    }
}