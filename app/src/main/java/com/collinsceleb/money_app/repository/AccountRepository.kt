package com.collinsceleb.money_app.repository

import androidx.lifecycle.LiveData
import com.collinsceleb.money_app.model.Account
import com.collinsceleb.money_app.data.AccountDao

class AccountRepository(private val accountDao: AccountDao): AccountRepositoryInterface {

    override val accounts: LiveData<List<Account>> = accountDao.getAllAccounts()

    override suspend fun insert(account: Account) {
        accountDao.insertAccount(account)
    }

    override suspend fun update(account: Account) {
        accountDao.updateAccount(account)
    }
}