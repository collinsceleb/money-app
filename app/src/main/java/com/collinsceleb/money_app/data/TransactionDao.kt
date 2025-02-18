package com.collinsceleb.money_app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.collinsceleb.money_app.model.Account
import com.collinsceleb.money_app.model.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM accounts WHERE accountNumber = :accountNumber LIMIT 1")
    fun getAccountByNumber(accountNumber: String): Account?

    @Update
    fun updateAccount(account: Account)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>
}