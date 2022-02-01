package com.zm.org.wallet.data.usertransactions

import com.zm.org.wallet.data.model.TransactionEntity
import com.zm.org.wallet.data.model.TransactionType
import com.zm.org.wallet.data.usertransactions.local.UserTransactionsLocalDataSource
import javax.inject.Inject

/**
 * [UserTransactionsRepository] responsible to fetch the required data from local or Remote DataSource
 * for now we only support local datasource and most of logic here bridging for the LocalDataSource
 */
internal class UserTransactionsRepositoryImpl @Inject constructor(
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
