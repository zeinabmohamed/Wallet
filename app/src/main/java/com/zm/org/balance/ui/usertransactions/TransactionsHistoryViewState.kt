package com.zm.org.balance.ui.usertransactions

sealed class TransactionsHistoryViewState(val isLoading: Boolean) {
    object LoadingState : TransactionsHistoryViewState(isLoading = true)
}
