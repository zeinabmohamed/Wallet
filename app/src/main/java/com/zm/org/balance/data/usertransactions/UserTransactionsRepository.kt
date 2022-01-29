package com.zm.org.balance.data.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType

/**
 * Handle all CRUD operations on user transactions (Create,Read,Update,Delete)
 */
interface UserTransactionsRepository {

    /**
     * Fetch all user transactions filtered by [TransactionType]
     */
    suspend fun getUserTransactionsForTransactionType(transactionType: TransactionType): List<Transaction>

    /**
     * Fetch all user transactions
     */
    suspend fun getUserAllTransactions(): List<Transaction>

    /**
     * Create new user transaction
     */
    suspend fun createUserTransaction(transaction: Transaction): Boolean
}
