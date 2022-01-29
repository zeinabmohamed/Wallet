package com.zm.org.balance.data.usertransactions.local

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType

internal class UserTransactionsLocalDataSource {
    suspend fun createUserTransaction(transactionToAdd: Transaction): Boolean {
        TODO("Not yet implemented")
    }

    fun getUserAllTransactions(): List<Transaction> {
        TODO("Not yet implemented")
    }

    fun getUserTransactionsForTransactionType(transactionType: TransactionType): List<Transaction> {
        TODO("Not yet implemented")
    }
}
