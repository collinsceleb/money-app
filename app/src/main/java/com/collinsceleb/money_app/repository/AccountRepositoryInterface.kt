package com.collinsceleb.money_app.repository

import androidx.lifecycle.LiveData
import com.collinsceleb.money_app.model.Account

interface AccountRepositoryInterface {
    val accounts: LiveData<List<Account>>
    suspend fun insert(account: Account)
    suspend fun update(account: Account)
}