package com.zm.org.wallet.domain.usertransactions

import com.zm.org.wallet.domain.entity.Transaction
import com.zm.org.wallet.data.usertransactions.UserTransactionsRepository
import com.zm.org.wallet.util.TimeMillis
import com.zm.org.wallet.util.toDayTimeStampMillis

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
