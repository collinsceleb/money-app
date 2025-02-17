package com.collinsceleb.money_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.collinsceleb.money_app.model.Account
import com.collinsceleb.money_app.repository.AccountRepositoryInterface
import kotlinx.coroutines.launch

class AccountViewModel(application: Application, private val accountRepository: AccountRepositoryInterface) : AndroidViewModel(application) {
    var accounts: LiveData<List<Account>> = accountRepository.accounts

    fun insert(account: Account) = viewModelScope.launch {
        accountRepository.insert(account)
    }

    fun update(account: Account) = viewModelScope.launch {
        accountRepository.update(account)
    }
}