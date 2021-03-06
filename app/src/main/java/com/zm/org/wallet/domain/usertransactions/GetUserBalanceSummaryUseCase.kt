package com.zm.org.wallet.domain.usertransactions

import com.zm.org.wallet.data.model.TransactionType
import com.zm.org.wallet.data.usertransactions.UserTransactionsRepository
import com.zm.org.wallet.domain.entity.UserBalanceSummary

/**
 * Responsible collect user balance Summery Expenses , income , balance
 */
class GetUserBalanceSummaryUseCase(
    private val userTransactionsRepository: UserTransactionsRepository
) {

    suspend operator fun invoke(): UserBalanceSummary {
        val expenses = userTransactionsRepository.getUserTransactionsForTransactionType(
            transactionType = TransactionType.EXPENSE
        )
        val totalExpenses = expenses.sumOf {
            it.amount.toDouble()
        }.toFloat()

        val income = userTransactionsRepository.getUserTransactionsForTransactionType(
            transactionType = TransactionType.INCOME
        )
        val totalIncomes = income.sumOf {
            it.amount.toDouble()
        }.toFloat()

        val balance = totalIncomes - totalExpenses

        return UserBalanceSummary(
            totalExpenses, totalIncomes, balance
        )
    }
}
