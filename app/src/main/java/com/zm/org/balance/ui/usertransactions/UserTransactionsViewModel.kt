package com.zm.org.balance.ui.usertransactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.domain.usertransactions.AddUserTransactionUseCase
import com.zm.org.balance.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.balance.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.balance.ui.usertransactions.TransactionsHistoryViewState.LoadingState
import com.zm.org.balance.ui.usertransactions.TransactionsHistoryViewState.TransactionsHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserTransactionsViewModel @Inject constructor(
    private val balanceSummaryUseCase: GetUserBalanceSummaryUseCase,
    private val transactionsCategorizedByDateUseCase: GetUserTransactionsCategorizedByDateUseCase,
    private val addUserTransactionUseCase: AddUserTransactionUseCase,
) : ViewModel() {
    private val initialViewState: TransactionsHistoryViewState = LoadingState
    val viewState: MutableState<TransactionsHistoryViewState> =
        mutableStateOf(initialViewState)

    init {
        viewModelScope.launch {
            loadTransactionsHistory()
        }
    }

    private suspend fun loadTransactionsHistory() {
        val userBalance = balanceSummaryUseCase.invoke()

        val transactionHistory = transactionsCategorizedByDateUseCase.invoke()

        viewState.value = TransactionsHistoryState(data = Pair(userBalance, transactionHistory))
    }

    fun onAddTransaction(transaction: Transaction) {
        viewModelScope.launch {
            viewState.value = LoadingState
            addUserTransactionUseCase.invoke(transaction)
            loadTransactionsHistory()
        }
    }
}
