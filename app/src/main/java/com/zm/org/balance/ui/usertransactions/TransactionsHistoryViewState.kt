package com.zm.org.balance.ui.usertransactions

import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.domain.entity.UserBalanceSummary
import com.zm.org.balance.util.TimeMillis

sealed class TransactionsHistoryViewState(
    open val isLoading: Boolean,
    open val data: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>?,
) {
    object LoadingState : TransactionsHistoryViewState(isLoading = true, data = null)
    class TransactionsHistoryState(override val data: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>?) :
        TransactionsHistoryViewState(isLoading = false, data)
}
