package com.zm.org.balance.data.usertransactions.local

import com.zm.org.balance.data.database.TransactionsDao
import com.zm.org.balance.data.model.TransactionEntity
import com.zm.org.balance.data.model.TransactionType

internal class UserTransactionsLocalDataSource(private val transactionsDao: TransactionsDao) {
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
