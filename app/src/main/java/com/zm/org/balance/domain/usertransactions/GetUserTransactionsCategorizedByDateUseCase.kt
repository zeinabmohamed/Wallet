package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.util.TimeMillis
import com.zm.org.balance.util.toDayTimeStampMillis

/**
 * Responsible collect user balance Summery Expenses , income , balance
 */
class GetUserTransactionsCategorizedByDateUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    suspend operator fun invoke(): Map<TimeMillis, List<Transaction>> {
        return userTransactionsRepository.getUserAllTransactions().groupBy {
            it.creationDateMillis.toDayTimeStampMillis()
        }
    }
}
