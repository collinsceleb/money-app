package com.collinsceleb.money_app.repository

import android.util.Log
import com.collinsceleb.money_app.data.TransactionDao
import com.collinsceleb.money_app.model.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    private val accounts = mutableMapOf(
        "1001" to 5000.0,
        "1002" to 3000.0,
        "1003" to 7000.0
    )
    suspend fun transferMoney(source: String, destination: String, amount: Double): Boolean {
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

        // Perform transfer
        sourceAccount.accountBalance -= amount
        destinationAccount.accountBalance += amount

        transactionDao.updateAccount(sourceAccount)
        transactionDao.updateAccount(destinationAccount)
        transactionDao.insertTransaction(
            Transaction(0, source, destination, amount, System.currentTimeMillis())
        )

        Log.d("Repository", "Transfer successful! New balance: ${sourceAccount.accountBalance}")
        return true
    }
}