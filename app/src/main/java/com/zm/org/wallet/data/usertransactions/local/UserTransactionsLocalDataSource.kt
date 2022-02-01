package com.zm.org.wallet.data.usertransactions.local

import com.zm.org.wallet.data.database.TransactionsDao
import com.zm.org.wallet.data.model.TransactionEntity
import com.zm.org.wallet.data.model.TransactionType
import javax.inject.Inject

internal class UserTransactionsLocalDataSource @Inject constructor(private val transactionsDao: TransactionsDao) {
    suspend fun createUserTransaction(transactionToAdd: TransactionEntity) {
        return transactionsDao.insert(transactionToAdd)
    }

    suspend fun getUserAllTransactions(): List<TransactionEntity> {
        return transactionsDao.getAll()
    }

    suspend fun getUserTransactionsForTransactionType(transactionType: TransactionType): List<TransactionEntity> {
        return transactionsDao.getByTransactionType(transactionType)
    }
}
