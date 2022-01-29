package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.domain.entity.UserBalanceSummary

/**
 * Responsible collect user balance Summery Expenses , income , balance
 */
class GetUserBalanceSummaryUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    suspend operator fun invoke(): UserBalanceSummary {

        return UserBalanceSummary(
            0f, 0f, 0f
        )
    }
}
