package com.collinsceleb.money_app.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.collinsceleb.money_app.repository.AccountRepositoryInterface
import com.collinsceleb.money_app.viewmodel.AccountViewModel

class AccountViewModelFactory(private val application: Application, private val accountRepository: AccountRepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(application, accountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}