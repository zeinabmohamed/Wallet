package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import java.util.*

/**
 * Responsible collect user balance Summery Expenses , income , balance
 */
class GetUserTransactionsCategorizedByDateUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    suspend operator fun invoke(): Map<Long, List<Transaction>> {
        return userTransactionsRepository.getUserAllTransactions().groupBy {
            val cal = Calendar.getInstance()
            cal.timeInMillis = it.creationDateMillis
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            cal.timeInMillis
        }
    }
}
