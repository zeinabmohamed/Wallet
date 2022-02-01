package com.zm.org.balance.ui.usertransactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.domain.entity.UserBalanceSummary
import com.zm.org.balance.domain.usertransactions.AddUserTransactionUseCase
import com.zm.org.balance.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.balance.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.balance.ui.error.ErrorToUserMessage
import com.zm.org.balance.ui.usertransactions.TransactionsHistoryViewState.*
import com.zm.org.balance.util.TimeMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserTransactionsViewModel @Inject constructor(
    private val balanceSummaryUseCase: GetUserBalanceSummaryUseCase,
    private val transactionsCategorizedByDateUseCase: GetUserTransactionsCategorizedByDateUseCase,
    private val addUserTransactionUseCase: AddUserTransactionUseCase,
    private val errorToUserMessage: ErrorToUserMessage,
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
        runCatching {
            val userBalance = balanceSummaryUseCase.invoke()
            val transactionHistory = transactionsCategorizedByDateUseCase.invoke()
            Pair(userBalance, transactionHistory)
        }.onSuccess {
            viewState.value = TransactionsHistoryState(data = Pair(it.first, it.second))
        }.onFailure {
            viewState.value = ErrorState(error = errorToUserMessage.toUserMessage(it))
        }
    }

    fun onAddTransaction(
        currentStateData: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>?,
        transaction: Transaction,
    ) {
        viewModelScope.launch {
            viewState.value = LoadingState
            runCatching {
                addUserTransactionUseCase.invoke(transaction)
            }.onSuccess {
                loadTransactionsHistory()
            }.onFailure {
                viewState.value = ErrorState(error = errorToUserMessage.toUserMessage(it),
                    data = currentStateData)
            }
        }
    }
}
