package com.collinsceleb.money_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey val accountNumber: String,
    val accountHolder: String,
    val accountType: String,
    val accountBalance: Double
)
