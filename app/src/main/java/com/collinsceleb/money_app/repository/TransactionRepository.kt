package com.collinsceleb.money_app.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.collinsceleb.money_app.data.TransactionDao
import com.collinsceleb.money_app.model.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {

    fun getAllTransactions(): LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    private fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }
    @androidx.room.Transaction
    fun transferMoney(source: String, destination: String, amount: Double): Boolean {
        Log.d("Repository", "Attempting transfer: $source → $destination, $$amount")

        val sourceAccount = transactionDao.getAccountByNumber(source)
        val destinationAccount = transactionDao.getAccountByNumber(destination)

        if (sourceAccount == null) {
            Log.e("Repository", "Source account not found: $source")
            return false
        }
        if (destinationAccount == null) {
            Log.e("Repository", "Destination account not found: $destination")
            return false
        }
        if (sourceAccount.accountBalance < amount) {
            Log.e("Repository", "Insufficient funds: Available ${sourceAccount.accountBalance}, Required $amount")
            return false
        }

        sourceAccount.accountBalance -= amount
        destinationAccount.accountBalance += amount

        transactionDao.updateAccount(sourceAccount)
        transactionDao.updateAccount(destinationAccount)
        insertTransaction(
            Transaction(0, source, destination, amount, System.currentTimeMillis())
        )

        Log.d("Repository", "Transfer successful! New balance: ${sourceAccount.accountBalance}")
        return true
    }
}