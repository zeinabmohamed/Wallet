package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository

/**
 * Responsible to Save user transaction
 */
class AddUserTransactionUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    /**
     * Handle all validation rules in transaction before add
     * - Title shouldn't be empty
     */
    suspend operator fun invoke(transaction: Transaction): Boolean {
        if (transaction.title.isNullOrBlank()) return false
        return userTransactionsRepository.createUserTransaction(transaction)
    }
}
