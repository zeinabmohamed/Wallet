package com.zm.org.balance.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zm.org.balance.data.model.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
}
