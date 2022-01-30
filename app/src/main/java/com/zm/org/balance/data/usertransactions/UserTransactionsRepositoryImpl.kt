package com.zm.org.balance.data.usertransactions

import com.zm.org.balance.data.model.TransactionEntity
import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.data.usertransactions.local.UserTransactionsLocalDataSource

/**
 * [UserTransactionsRepository] responsible to fetch the required data from local or Remote DataSource
 * for now we only support local datasource and most of logic here bridging for the LocalDataSource
 */
internal class UserTransactionsRepositoryImpl(
    private val userTransactionsLocalDataSource: UserTransactionsLocalDataSource,
) : UserTransactionsRepository {

    override suspend fun getUserTransactionsForTransactionType(transactionType: TransactionType): List<TransactionEntity> {
        return userTransactionsLocalDataSource.getUserTransactionsForTransactionType(transactionType)
    }

    override suspend fun getUserAllTransactions(): List<TransactionEntity> {
        return userTransactionsLocalDataSource.getUserAllTransactions()
    }

    override suspend fun createUserTransaction(transaction: TransactionEntity): Boolean {
        userTransactionsLocalDataSource.createUserTransaction(transaction)
        return true
    }
}
