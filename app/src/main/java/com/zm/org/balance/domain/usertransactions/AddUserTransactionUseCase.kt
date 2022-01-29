package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import java.util.*

/**
 * Responsible to Save user transaction
 */
class AddUserTransactionUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    /**
     * Handle all validation rules in transaction before add
     * - Title shouldn't be empty
     * - amount should be > 0
     * - creation date should be <= Today [No future transactions]
     */
    suspend operator fun invoke(transaction: Transaction): Boolean {
        if (transaction.title.isNullOrBlank()) return false
        if (transaction.amount <= 0f) return false
        if (Date(transaction.creationDateMillis.timeMillis ).after(Date())) return false

        return userTransactionsRepository.createUserTransaction(transaction)
    }
}
