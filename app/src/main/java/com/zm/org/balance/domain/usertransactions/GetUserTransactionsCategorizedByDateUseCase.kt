package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.util.TimeMillis
import com.zm.org.balance.util.toDayTimeStampMillis

/**
 * Responsible collect user balance Summery Expenses , income , balance
 */
class GetUserTransactionsCategorizedByDateUseCase(
    private val userTransactionsRepository: UserTransactionsRepository,
    private val transactionMapper: TransactionMapper,
) {

    suspend operator fun invoke(): Map<TimeMillis, List<Transaction>> {
        return userTransactionsRepository.getUserAllTransactions().map {
            transactionMapper.fromTransactionEntity(it)
        }.sortedByDescending {
            it.creationDateMillis.timeMillis
        }.groupBy {
            it.creationDateMillis.toDayTimeStampMillis()
        }
    }
}
