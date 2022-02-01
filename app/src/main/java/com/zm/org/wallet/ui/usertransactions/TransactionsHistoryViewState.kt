package com.zm.org.wallet.ui.usertransactions

import com.zm.org.wallet.domain.entity.Transaction
import com.zm.org.wallet.domain.entity.UserBalanceSummary
import com.zm.org.wallet.util.TimeMillis

sealed class TransactionsHistoryViewState(
    open val isLoading: Boolean = false,
    open val data: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>? = null,
    open val error: String? = null,
) {
    object LoadingState : TransactionsHistoryViewState(isLoading = true)
    class TransactionsHistoryState(
        override val data: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>?,
    ) : TransactionsHistoryViewState(data = data)

    class ErrorState(
        override val error: String,
        data: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>? = null,
    ) : TransactionsHistoryViewState(error = error, data = data)
}
