package com.collinsceleb.money_app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.collinsceleb.money_app.model.Account

class MockAccountRepository: AccountRepositoryInterface {
    private val mockAccounts = mutableListOf(
        Account("123456789", "Seal", "Savings", 5000.00),
        Account("987654321", "Seal", "Checking", 2500.75),
        Account("112233445", "Seal", "Business", 12000.00),
        Account("556677889", "Seal", "Personal", 800.50)
    )
    private val accountsLiveData = MutableLiveData<List<Account>>(mockAccounts)

    override val accounts: LiveData<List<Account>> = accountsLiveData

    override suspend fun insert(account: Account) {
        mockAccounts.add(account)
        accountsLiveData.value = mockAccounts.toList()
    }

    override suspend fun update(account: Account) {
        val index = mockAccounts.indexOfFirst { it.accountNumber == account.accountNumber }
        if (index != -1) {
            mockAccounts[index] = account
            accountsLiveData.value = mockAccounts.toList()
        }
    }

}