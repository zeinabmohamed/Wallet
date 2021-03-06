package com.zm.org.wallet.data.usertransactions

import com.zm.org.wallet.data.model.TransactionEntity
import com.zm.org.wallet.data.model.TransactionType

/**
 * Handle all CRUD operations on user transactions (Create,Read,Update,Delete)
 */
interface UserTransactionsRepository {

    /**
     * Fetch all user transactions filtered by [TransactionType]
     */
    suspend fun getUserTransactionsForTransactionType(transactionType: TransactionType): List<TransactionEntity>

    /**
     * Fetch all user transactions
     */
    suspend fun getUserAllTransactions(): List<TransactionEntity>

    /**
     * Create new user transaction
     */
    suspend fun createUserTransaction(transaction: TransactionEntity): Boolean
}
