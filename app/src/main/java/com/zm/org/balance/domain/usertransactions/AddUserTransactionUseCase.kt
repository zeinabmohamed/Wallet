package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository

/**
 * Responsible to Save user transaction
 */
class AddUserTransactionUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    suspend operator fun invoke(transaction: Transaction): Boolean {
        return userTransactionsRepository.createUserTransaction(transaction)
    }
}
