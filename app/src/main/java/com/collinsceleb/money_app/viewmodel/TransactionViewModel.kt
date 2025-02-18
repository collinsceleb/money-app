package com.collinsceleb.money_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collinsceleb.money_app.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _transferStatus = MutableLiveData<Boolean>()
    val transferStatus: LiveData<Boolean> get() = _transferStatus

    fun transferMoney(source: String, destination: String, amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = transactionRepository.transferMoney(source, destination, amount)
            _transferStatus.postValue(success)

        }
    }
}