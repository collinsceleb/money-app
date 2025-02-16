package com.collinsceleb.money_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sourceAccount: String,
    val destinationAccount: String,
    val amount: Double,
    val timestamp: Long

)
