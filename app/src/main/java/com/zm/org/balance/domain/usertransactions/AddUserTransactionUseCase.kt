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
    @Throws(AssertionError::class)
    suspend operator fun invoke(transaction: Transaction): Boolean {
        assert(transaction.title.isNullOrBlank().not()) {
            "Invalid transaction title >> ${transaction.title}"
        }
        assert((transaction.amount <= 0f).not()) {
            "Invalid transaction Amount >> ${transaction.amount}"
        }
        assert((Date(transaction.creationDateMillis.timeMillis).after(Date())).not()) {
            "Invalid transaction creationDate >> ${transaction.creationDateMillis.timeMillis}"
        }
        return userTransactionsRepository.createUserTransaction(transaction)
    }
}
