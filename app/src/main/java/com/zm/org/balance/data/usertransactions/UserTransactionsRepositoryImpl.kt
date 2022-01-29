package com.zm.org.balance.data.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.data.usertransactions.local.UserTransactionsLocalDataSource

/**
 * [UserTransactionsRepository] responsible to fetch the required data from local or Remote DataSource
 * for now we only support local datasource and most of logic here bridging for the LocalDataSource
 */
internal class UserTransactionsRepositoryImpl(
    private val userTransactionsLocalDataSource: UserTransactionsLocalDataSource
) : UserTransactionsRepository {

    override suspend fun getUserTransactionsForTransactionType(transactionType: TransactionType): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserAllTransactions(): List<Transaction> {
        return userTransactionsLocalDataSource.getUserAllTransactions()
    }

    override suspend fun createUserTransaction(transaction: Transaction): Boolean {
        return userTransactionsLocalDataSource.createUserTransaction()
    }
}
