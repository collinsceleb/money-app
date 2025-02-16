package com.collinsceleb.money_app.data

import android.content.Context
import androidx.activity.result.launch
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.collinsceleb.money_app.model.Account
import com.collinsceleb.money_app.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Account::class, Transaction::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private var shouldPopulateWithMockData = true // Flag to control mock data insertion

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                if (shouldPopulateWithMockData) {
                    scope.launch {
                        populateDatabase(database.accountDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(accountDao: AccountDao) {
            accountDao.deleteAll()
            var account = Account("123456789", "Seal", "Savings", 5000.00)
            accountDao.insertAccount(account)
            account = Account("987654321", "Seal", "Checking", 2500.75)
            accountDao.insertAccount(account)
            account = Account("112233445", "Seal", "Business", 12000.00)
            accountDao.insertAccount(account)
            account = Account("556677889", "Seal", "Personal", 800.50)
            accountDao.insertAccount(account)
            account = Account("556677889", "Seal", "Personal", 800.50)
            accountDao.insertAccount(account)
            account = Account("556677889", "Seal", "Personal", 800.50)
            accountDao.insertAccount(account)
            account = Account("556677889", "Seal", "Personal", 800.50)
            accountDao.insertAccount(account)
        }
    }
}