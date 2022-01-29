package com.zm.org.balance.data.usertransactions.local

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType

internal class UserTransactionsLocalDataSource(private val transactionsDao: TransactionsDao) {
    suspend fun createUserTransaction(transactionToAdd: Transaction) {
        return transactionsDao.insert(transactionToAdd)
    }

    suspend fun getUserAllTransactions(): List<Transaction> {
        return transactionsDao.getAll()
    }

    suspend fun getUserTransactionsForTransactionType(transactionType: TransactionType): List<Transaction> {
        return transactionsDao.getByTransactionType(transactionType)
    }
}
