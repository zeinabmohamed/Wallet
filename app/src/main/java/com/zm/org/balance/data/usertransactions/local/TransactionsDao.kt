package com.zm.org.balance.data.usertransactions.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType

@Dao
interface TransactionsDao {
    @Query("SELECT * FROM `user-transactions` WHERE transaction_type == :transactionType")
    suspend fun getByTransactionType(transactionType: TransactionType): List<Transaction>

    @Query("SELECT * FROM `user-transactions`")
    suspend fun getAll(): List<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Query("DELETE FROM `user-transactions`")
    suspend fun deleteAll()
}
