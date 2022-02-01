package com.zm.org.wallet.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zm.org.wallet.data.model.TransactionEntity
import com.zm.org.wallet.data.model.TransactionType

@Dao
interface TransactionsDao {
    @Query("SELECT * FROM `user-transactions` WHERE transaction_type == :transactionType")
    suspend fun getByTransactionType(transactionType: TransactionType): List<TransactionEntity>

    @Query("SELECT * FROM `user-transactions`")
    suspend fun getAll(): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("DELETE FROM `user-transactions`")
    suspend fun deleteAll()
}
