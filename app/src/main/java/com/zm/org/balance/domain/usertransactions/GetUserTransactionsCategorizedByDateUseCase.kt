package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository

/**
 * Responsible collect user balance Summery Expenses , income , balance
 */
class GetUserTransactionsCategorizedByDateUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    suspend operator fun invoke(): Map<Long, Transaction> {
        return emptyMap()
    }
}
